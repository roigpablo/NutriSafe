package com.nutrisafe.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class EvaluadorDiabetesTest {

    @Test
    public void azucaresBajosVerde() {
        assertEquals(NivelSemaforo.VERDE, EvaluadorDiabetes.evaluar(3.0).nivel);
    }

    @Test
    public void limiteInferiorSigueVerde() {
        assertEquals(NivelSemaforo.VERDE, EvaluadorDiabetes.evaluar(5.0).nivel);
    }

    @Test
    public void azucaresMediosAmbar() {
        assertEquals(NivelSemaforo.AMBAR, EvaluadorDiabetes.evaluar(10.0).nivel);
    }

    @Test
    public void limiteSuperiorSigueAmbar() {
        assertEquals(NivelSemaforo.AMBAR, EvaluadorDiabetes.evaluar(22.5).nivel);
    }

    @Test
    public void azucaresAltosRojo() {
        assertEquals(NivelSemaforo.ROJO, EvaluadorDiabetes.evaluar(56.3).nivel);
    }

    @Test
    public void sinDatosGris() {
        assertEquals(NivelSemaforo.GRIS, EvaluadorDiabetes.evaluar(null).nivel);
    }
}