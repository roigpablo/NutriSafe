package com.nutrisafe.data.local;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "usuario")
public class Usuario {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String nombre;
    public String sexo;             // "H" o "M"
    public int edad;
    public double pesoKg;
    public double alturaCm;
    public double factorActividad;  // 1.2, 1.375, 1.55, 1.725
    public boolean celiaco;
    public boolean diabetico;
    public String alergenos = "";   // etiquetas OFF separadas por coma, ej: "en:milk,en:nuts"
}