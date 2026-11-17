package com.nutrisafe.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface UsuarioDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void guardar(Usuario usuario);

    @Query("SELECT * FROM usuario LIMIT 1")
    LiveData<Usuario> observarPerfil();

    @Query("SELECT * FROM usuario LIMIT 1")
    Usuario obtenerPerfil();
}