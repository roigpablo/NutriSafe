package com.nutrisafe.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

public class SemaforoServiceTest {

    @Test
    public void celiacoDiabeticoConGlutenYPocosAzucaresEsRojoPorGluten() {
        Evaluacion e = SemaforoService.evaluar(
                true, true, Collections.emptyList(), "en:gluten", "", "trigo", 2.0);
        assertEquals(NivelSemaforo.ROJO, e.nivel);
        assertTrue(e.motivo.contains("Gluten"));
    }

    @Test
    public void diabeticoConMuchosAzucaresEsRojoPorAzucares() {
        Evaluacion e = SemaforoService.evaluar(
                false, true, Collections.emptyList(), "", "", "azúcar", 56.3);
        assertEquals(NivelSemaforo.ROJO, e.nivel);
        assertTrue(e.motivo.contains("Azúcares altos"));
    }

    @Test
    public void sinDiabetesNoSeEvaluanLosAzucares() {
        Evaluacion e = SemaforoService.evaluar(
                false, false, Collections.emptyList(), "", "", "azúcar", 56.3);
        assertEquals(NivelSemaforo.VERDE, e.nivel);
    }

    @Test
    public void unDatoAusenteDejaElResultadoEnGrisSiElRestoEsVerde() {
        Evaluacion e = SemaforoService.evaluar(
                false, true, Arrays.asList("en:peanuts"), "", "", "", 3.0);
        assertEquals(NivelSemaforo.GRIS, e.nivel);
    }

    @Test
    public void conDosRojosSeJuntanLosMotivos() {
        Evaluacion e = SemaforoService.evaluar(
                true, true, Collections.emptyList(), "en:gluten", "", "trigo", 30.0);
        assertEquals(NivelSemaforo.ROJO, e.nivel);
        assertTrue(e.motivo.contains("Gluten") && e.motivo.contains("Azúcares altos"));
    }
}