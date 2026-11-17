package com.nutrisafe.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface AlimentoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void guardar(Alimento alimento);

    @Query("SELECT * FROM alimento WHERE codigoBarras = :codigo")
    Alimento buscarPorCodigo(String codigo);

    @Query("SELECT * FROM alimento WHERE nombre LIKE '%' || :texto || '%' LIMIT 20")
    java.util.List<Alimento> buscarPorNombre(String texto);
}