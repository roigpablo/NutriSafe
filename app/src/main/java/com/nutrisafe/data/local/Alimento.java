package com.nutrisafe.data.local;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "alimento")
public class Alimento {
    @PrimaryKey
    @NonNull
    public String codigoBarras = "";

    public String nombre;
    public String marca;
    public String imagenUrl;
    public String ingredientes;
    public String alergenos = "";   // allergens_tags separados por coma
    public String trazas = "";      // traces_tags separados por coma
    public Double kcal100g;         // Double (no double): null si OFF no tiene el dato
    public Double azucares100g;
    public long ultimaActualizacion;
}