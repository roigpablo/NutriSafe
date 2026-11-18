package com.nutrisafe.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class EvaluadorAlergenos {

    private static final String GLUTEN = "en:gluten";

    private EvaluadorAlergenos() {
    }

    public static Evaluacion evaluar(boolean celiaco, List<String> alergiasUsuario,
                                     String alergenos, String trazas, String ingredientes) {
        List<String> vigilados = new ArrayList<>(alergiasUsuario);
        if (celiaco && !vigilados.contains(GLUTEN)) {
            vigilados.add(GLUTEN);
        }
        if (vigilados.isEmpty()) {
            return new Evaluacion(NivelSemaforo.VERDE, "Sin alergias ni celiaquía configuradas");
        }

        List<String> enProducto = separar(alergenos);
        List<String> enTrazas = separar(trazas);

        List<String> contiene = interseccion(vigilados, enProducto);
        if (!contiene.isEmpty()) {
            return new Evaluacion(NivelSemaforo.ROJO, "Contiene: " + nombres(contiene));
        }

        List<String> posibles = interseccion(vigilados, enTrazas);
        if (!posibles.isEmpty()) {
            return new Evaluacion(NivelSemaforo.AMBAR, "Puede contener trazas de: " + nombres(posibles));
        }

        boolean hayDatos = !enProducto.isEmpty() || !enTrazas.isEmpty()
                || (ingredientes != null && !ingredientes.trim().isEmpty());
        if (!hayDatos) {
            return new Evaluacion(NivelSemaforo.GRIS, "Sin información de alérgenos en el producto");
        }
        return new Evaluacion(NivelSemaforo.VERDE, "No contiene tus alérgenos");
    }

    private static List<String> separar(String csv) {
        if (csv == null || csv.trim().isEmpty()) {
            return Collections.emptyList();
        }
        return Arrays.asList(csv.split(","));
    }

    private static List<String> interseccion(List<String> vigilados, List<String> producto) {
        List<String> resultado = new ArrayList<>();
        for (String tag : vigilados) {
            if (producto.contains(tag)) {
                resultado.add(tag);
            }
        }
        return resultado;
    }

    private static String nombres(List<String> tags) {
        return tags.stream().map(Alergenos::nombre).collect(Collectors.joining(", "));
    }
}