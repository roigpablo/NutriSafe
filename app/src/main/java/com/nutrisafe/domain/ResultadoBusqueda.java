package com.nutrisafe.domain;

public final class ResultadoBusqueda {
    public final String codigo;
    public final String nombre;
    public final String marca;
    public final String imagenUrl;

    public ResultadoBusqueda(String codigo, String nombre, String marca, String imagenUrl) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.marca = marca;
        this.imagenUrl = imagenUrl;
    }
}