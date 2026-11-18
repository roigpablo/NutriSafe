package com.nutrisafe.data;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.nutrisafe.data.local.Alimento;
import com.nutrisafe.data.local.AlimentoDao;
import com.nutrisafe.data.local.AppDatabase;
import com.nutrisafe.data.remote.OpenFoodFactsApi;
import com.nutrisafe.data.remote.ProductoDto;
import com.nutrisafe.data.remote.ProductoResponse;
import com.nutrisafe.data.remote.RetrofitClient;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Response;

public class ProductoRepository {

    public interface Callback {
        void onExito(Alimento alimento);

        void onNoEncontrado();

        void onError(String mensaje);
    }

    private final AlimentoDao dao;
    private final OpenFoodFactsApi api;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler principal = new Handler(Looper.getMainLooper());

    public ProductoRepository(Context context) {
        dao = AppDatabase.getInstance(context).alimentoDao();
        api = RetrofitClient.getApi();
    }

    public void obtenerProducto(String codigo, Callback callback) {
        executor.execute(() -> {
            Alimento guardado = dao.buscarPorCodigo(codigo);
            if (guardado != null) {
                principal.post(() -> callback.onExito(guardado));
                return;
            }

            try {
                Response<ProductoResponse> respuesta =
                        api.obtenerProducto(codigo, OpenFoodFactsApi.CAMPOS).execute();
                ProductoResponse cuerpo = respuesta.body();

                if (respuesta.code() == 404 || (cuerpo != null && cuerpo.status != 1)) {
                    principal.post(callback::onNoEncontrado);
                } else if (!respuesta.isSuccessful() || cuerpo == null || cuerpo.product == null) {
                    principal.post(() -> callback.onError("Error del servidor: " + respuesta.code()));
                } else {
                    Alimento alimento = convertir(codigo, cuerpo.product);
                    dao.guardar(alimento);
                    principal.post(() -> callback.onExito(alimento));
                }
            } catch (IOException e) {
                principal.post(() -> callback.onError("Sin conexión con Open Food Facts"));
            }
        });
    }

    private Alimento convertir(String codigo, ProductoDto dto) {
        Alimento a = new Alimento();
        a.codigoBarras = codigo;
        a.nombre = dto.nombre;
        a.marca = dto.brands;
        a.imagenUrl = dto.imagenUrl;
        a.ingredientes = dto.ingredientes;
        a.alergenos = unir(dto.alergenos);
        a.trazas = unir(dto.trazas);
        if (dto.nutriments != null) {
            a.kcal100g = dto.nutriments.kcal100g;
            a.azucares100g = dto.nutriments.azucares100g;
        }
        a.ultimaActualizacion = System.currentTimeMillis();
        return a;
    }

    private String unir(List<String> lista) {
        return lista == null ? "" : String.join(",", lista);
    }
}