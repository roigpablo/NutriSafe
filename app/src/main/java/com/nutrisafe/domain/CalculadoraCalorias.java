package com.nutrisafe.domain;

public final class CalculadoraCalorias {

    private CalculadoraCalorias() {
    }

    public static double calcularTmb(String sexo, int edad, double pesoKg, double alturaCm) {
        double base = 10 * pesoKg + 6.25 * alturaCm - 5 * edad;
        return "H".equals(sexo) ? base + 5 : base - 161;
    }

    public static int calcularMantenimiento(double tmb, double factorActividad) {
        return (int) Math.round(tmb * factorActividad);
    }
}