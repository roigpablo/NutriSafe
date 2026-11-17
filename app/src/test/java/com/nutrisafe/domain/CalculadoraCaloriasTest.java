package com.nutrisafe.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CalculadoraCaloriasTest {

    @Test
    public void tmbHombreAdulto() {
        double tmb = CalculadoraCalorias.calcularTmb("H", 30, 80, 180);
        assertEquals(1780.0, tmb, 0.01);
    }

    @Test
    public void tmbMujerAdulta() {
        double tmb = CalculadoraCalorias.calcularTmb("M", 30, 60, 165);
        assertEquals(1320.25, tmb, 0.01);
    }

    @Test
    public void mantenimientoConActividadLigera() {
        int kcal = CalculadoraCalorias.calcularMantenimiento(1780, 1.375);
        assertEquals(2448, kcal);
    }
}