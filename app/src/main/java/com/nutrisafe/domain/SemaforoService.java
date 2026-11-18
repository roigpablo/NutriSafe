package com.nutrisafe.domain;

import java.util.List;

public final class SemaforoService {

    private SemaforoService() {
    }

    public static Evaluacion evaluar(boolean celiaco, boolean diabetico, List<String> alergias,
                                     String alergenos, String trazas, String ingredientes,
                                     Double azucares100g) {
        Evaluacion porAlergenos =
                EvaluadorAlergenos.evaluar(celiaco, alergias, alergenos, trazas, ingredientes);
        if (!diabetico) {
            return porAlergenos;
        }

        Evaluacion porAzucares = EvaluadorDiabetes.evaluar(azucares100g);
        if (porAlergenos.nivel == porAzucares.nivel) {
            return new Evaluacion(porAlergenos.nivel,
                    porAlergenos.motivo + ". " + porAzucares.motivo);
        }
        NivelSemaforo peor = NivelSemaforo.peor(porAlergenos.nivel, porAzucares.nivel);
        return peor == porAlergenos.nivel ? porAlergenos : porAzucares;
    }
}