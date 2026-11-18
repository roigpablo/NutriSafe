package com.nutrisafe.domain;

public enum NivelSemaforo {
    VERDE, GRIS, AMBAR, ROJO;

    public static NivelSemaforo peor(NivelSemaforo a, NivelSemaforo b) {
        return a.ordinal() >= b.ordinal() ? a : b;
    }
}