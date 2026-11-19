package com.nutrisafe.data;

import android.os.Handler;
import android.os.Looper;

import com.nutrisafe.data.remote.BusquedaResponse;
import com.nutrisafe.data.remote.ResultadoDto;
import com.nutrisafe.data.remote.RetrofitClient;
import com.nutrisafe.data.remote.SearchApi;
import com.nutrisafe.domain.ResultadoBusqueda;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import retrofit2.Response;

public class BusquedaRepository {

    public interface Callback {
        void onExito(List<ResultadoBusqueda> resultados);

        void onError(String mensaje);
    }

    private static final int LIMITE_POR_MINUTO = 10;
    private static final long VENTANA_MS = 60_000;
    private static final int TAMANO_PAGINA = 20;

    private final SearchApi api = RetrofitClient.getSearchApi();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler principal = new Handler(Looper.getMainLooper());
    private final Deque<Long> envios = new ArrayDeque<>();

    public void buscar(String texto, Callback callback) {
        executor.execute(() -> {
            if (!reservarEnvio()) {
                principal.post(() -> callback.onError(
                        "Demasiadas búsquedas seguidas. Espera unos segundos."));
                return;
            }

            try {
                Response<BusquedaResponse> respuesta =
                        api.buscar(texto, TAMANO_PAGINA, "es", SearchApi.CAMPOS).execute();
                BusquedaResponse cuerpo = respuesta.body();

                if (!respuesta.isSuccessful() || cuerpo == null || cuerpo.hits == null) {
                    principal.post(() -> callback.onError(
                            "Error del servidor: " + respuesta.code()));
                    return;
                }

                List<ResultadoBusqueda> resultados = new ArrayList<>();
                for (ResultadoDto dto : cuerpo.hits) {
                    if (dto.code != null && !dto.code.isEmpty()) {
                        resultados.add(convertir(dto));
                    }
                }
                principal.post(() -> callback.onExito(resultados));
            } catch (IOException e) {
                principal.post(() -> callback.onError("Sin conexión con Open Food Facts"));
            }
        });
    }

    private synchronized boolean reservarEnvio() {
        long ahora = System.currentTimeMillis();
        while (!envios.isEmpty() && ahora - envios.peekFirst() > VENTANA_MS) {
            envios.pollFirst();
        }
        if (envios.size() >= LIMITE_POR_MINUTO) {
            return false;
        }
        envios.addLast(ahora);
        return true;
    }

    private ResultadoBusqueda convertir(ResultadoDto dto) {
        String nombre = dto.nombre != null && !dto.nombre.trim().isEmpty()
                ? dto.nombre.trim() : "Sin nombre";
        return new ResultadoBusqueda(dto.code, nombre, unirMarcas(dto.brands), dto.imagenUrl);
    }

    private String unirMarcas(List<String> marcas) {
        if (marcas == null) {
            return "";
        }
        return marcas.stream()
                .map(String::trim)
                .filter(m -> !m.isEmpty())
                .collect(Collectors.joining(", "));
    }
}