package com.nutrisafe.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.nutrisafe.data.ProductoRepository;
import com.nutrisafe.data.local.AppDatabase;
import com.nutrisafe.data.local.Alimento;
import com.nutrisafe.data.local.Usuario;
import com.nutrisafe.domain.Evaluacion;
import com.nutrisafe.domain.SemaforoService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ConsultaViewModel extends AndroidViewModel {

    private final ProductoRepository repositorio;
    private final LiveData<Usuario> perfil;
    private final MutableLiveData<Alimento> alimento = new MutableLiveData<>();
    private final MutableLiveData<String> mensaje = new MutableLiveData<>();
    private final MutableLiveData<Boolean> cargando = new MutableLiveData<>(false);
    private final MediatorLiveData<Evaluacion> evaluacion = new MediatorLiveData<>();

    public ConsultaViewModel(@NonNull Application application) {
        super(application);
        repositorio = new ProductoRepository(application);
        perfil = AppDatabase.getInstance(application).usuarioDao().observarPerfil();

        evaluacion.addSource(alimento, a -> recalcular());
        evaluacion.addSource(perfil, u -> recalcular());
    }

    public LiveData<Alimento> getAlimento() {
        return alimento;
    }

    public LiveData<String> getMensaje() {
        return mensaje;
    }

    public LiveData<Boolean> getCargando() {
        return cargando;
    }

    public LiveData<Evaluacion> getEvaluacion() {
        return evaluacion;
    }

    public void buscar(String codigo) {
        cargando.setValue(true);
        mensaje.setValue(null);
        repositorio.obtenerProducto(codigo, new ProductoRepository.Callback() {
            @Override
            public void onExito(Alimento resultado) {
                cargando.setValue(false);
                alimento.setValue(resultado);
            }

            @Override
            public void onNoEncontrado() {
                cargando.setValue(false);
                alimento.setValue(null);
                mensaje.setValue("Producto no encontrado en Open Food Facts");
            }

            @Override
            public void onError(String texto) {
                cargando.setValue(false);
                alimento.setValue(null);
                mensaje.setValue(texto);
            }
        });
    }

    private void recalcular() {
        Alimento a = alimento.getValue();
        Usuario u = perfil.getValue();
        if (a == null || u == null) {
            evaluacion.setValue(null);
            return;
        }
        evaluacion.setValue(SemaforoService.evaluar(
                u.celiaco, u.diabetico, separar(u.alergenos),
                a.alergenos, a.trazas, a.ingredientes, a.azucares100g));
    }

    private List<String> separar(String csv) {
        if (csv == null || csv.isEmpty()) {
            return Collections.emptyList();
        }
        return Arrays.asList(csv.split(","));
    }
}