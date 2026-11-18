package com.nutrisafe.domain;

public final class Evaluacion {
    public final NivelSemaforo nivel;
    public final String motivo;

    public Evaluacion(NivelSemaforo nivel, String motivo) {
        this.nivel = nivel;
        this.motivo = motivo;
    }
}