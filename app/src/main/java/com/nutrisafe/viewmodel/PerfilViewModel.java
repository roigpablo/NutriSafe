package com.nutrisafe.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.nutrisafe.data.local.AppDatabase;
import com.nutrisafe.data.local.Usuario;
import com.nutrisafe.data.local.UsuarioDao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PerfilViewModel extends AndroidViewModel {

    private final UsuarioDao dao;
    private final LiveData<Usuario> perfil;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public PerfilViewModel(@NonNull Application application) {
        super(application);
        dao = AppDatabase.getInstance(application).usuarioDao();
        perfil = dao.observarPerfil();
    }

    public LiveData<Usuario> getPerfil() {
        return perfil;
    }

    public void guardar(Usuario usuario) {
        executor.execute(() -> dao.guardar(usuario));
    }
}