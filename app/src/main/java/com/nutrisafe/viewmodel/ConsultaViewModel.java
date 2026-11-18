package com.nutrisafe.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.nutrisafe.data.ProductoRepository;
import com.nutrisafe.data.local.Alimento;

public class ConsultaViewModel extends AndroidViewModel {

    private final ProductoRepository repositorio;
    private final MutableLiveData<Alimento> alimento = new MutableLiveData<>();
    private final MutableLiveData<String> mensaje = new MutableLiveData<>();
    private final MutableLiveData<Boolean> cargando = new MutableLiveData<>(false);

    public ConsultaViewModel(@NonNull Application application) {
        super(application);
        repositorio = new ProductoRepository(application);
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
}