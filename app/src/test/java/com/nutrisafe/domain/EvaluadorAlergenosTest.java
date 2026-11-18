package com.nutrisafe.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

public class EvaluadorAlergenosTest {

    @Test
    public void celiacoConGlutenEsRojo() {
        Evaluacion e = EvaluadorAlergenos.evaluar(
                true, Collections.emptyList(), "en:gluten,en:milk", "", "trigo, leche");
        assertEquals(NivelSemaforo.ROJO, e.nivel);
    }

    @Test
    public void alergiaEnTrazasEsAmbar() {
        Evaluacion e = EvaluadorAlergenos.evaluar(
                false, Arrays.asList("en:milk"), "", "en:milk", "cacao");
        assertEquals(NivelSemaforo.AMBAR, e.nivel);
    }

    @Test
    public void conDatosYSinCoincidenciaEsVerde() {
        Evaluacion e = EvaluadorAlergenos.evaluar(
                false, Arrays.asList("en:peanuts"), "en:milk", "", "leche");
        assertEquals(NivelSemaforo.VERDE, e.nivel);
    }

    @Test
    public void sinDatosEsGris() {
        Evaluacion e = EvaluadorAlergenos.evaluar(
                false, Arrays.asList("en:peanuts"), "", "", "");
        assertEquals(NivelSemaforo.GRIS, e.nivel);
    }

    @Test
    public void sinAlergiasConfiguradasEsVerde() {
        Evaluacion e = EvaluadorAlergenos.evaluar(
                false, Collections.emptyList(), "en:gluten", "", "trigo");
        assertEquals(NivelSemaforo.VERDE, e.nivel);
    }
}