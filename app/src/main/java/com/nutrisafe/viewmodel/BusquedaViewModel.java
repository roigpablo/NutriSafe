package com.nutrisafe.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nutrisafe.data.BusquedaRepository;
import com.nutrisafe.domain.ResultadoBusqueda;

import java.util.Collections;
import java.util.List;

public class BusquedaViewModel extends ViewModel {

    private final BusquedaRepository repositorio = new BusquedaRepository();
    private final MutableLiveData<List<ResultadoBusqueda>> resultados =
            new MutableLiveData<>(Collections.emptyList());
    private final MutableLiveData<String> mensaje = new MutableLiveData<>();
    private final MutableLiveData<Boolean> cargando = new MutableLiveData<>(false);
    private int ultimaPeticion = 0;

    public LiveData<List<ResultadoBusqueda>> getResultados() {
        return resultados;
    }

    public LiveData<String> getMensaje() {
        return mensaje;
    }

    public LiveData<Boolean> getCargando() {
        return cargando;
    }

    public void buscar(String texto) {
        int numero = ++ultimaPeticion;
        cargando.setValue(true);
        mensaje.setValue(null);
        repositorio.buscar(texto, new BusquedaRepository.Callback() {
            @Override
            public void onExito(List<ResultadoBusqueda> lista) {
                if (numero != ultimaPeticion) {
                    return;
                }
                cargando.setValue(false);
                resultados.setValue(lista);
                mensaje.setValue(lista.isEmpty() ? "Sin resultados" : null);
            }

            @Override
            public void onError(String texto) {
                if (numero != ultimaPeticion) {
                    return;
                }
                cargando.setValue(false);
                resultados.setValue(Collections.emptyList());
                mensaje.setValue(texto);
            }
        });
    }

    public void limpiar() {
        ultimaPeticion++;
        cargando.setValue(false);
        resultados.setValue(Collections.emptyList());
        mensaje.setValue(null);
    }
}