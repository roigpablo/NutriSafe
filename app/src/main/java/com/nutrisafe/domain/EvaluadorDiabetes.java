package com.nutrisafe.domain;

public final class EvaluadorDiabetes {

    public static final double UMBRAL_VERDE = 5.0;
    public static final double UMBRAL_ROJO = 22.5;

    private EvaluadorDiabetes() {
    }

    public static Evaluacion evaluar(Double azucares100g) {
        if (azucares100g == null) {
            return new Evaluacion(NivelSemaforo.GRIS, "Sin datos de azúcares");
        }
        String valor = azucares100g + " g de azúcares por 100 g";
        if (azucares100g <= UMBRAL_VERDE) {
            return new Evaluacion(NivelSemaforo.VERDE, "Azúcares bajos: " + valor);
        }
        if (azucares100g <= UMBRAL_ROJO) {
            return new Evaluacion(NivelSemaforo.AMBAR, "Azúcares medios: " + valor);
        }
        return new Evaluacion(NivelSemaforo.ROJO, "Azúcares altos: " + valor);
    }
}