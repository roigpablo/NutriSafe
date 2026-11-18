package com.nutrisafe.domain;

public final class Alergenos {

    public static final String[] TAGS = {
            "en:gluten", "en:crustaceans", "en:eggs", "en:fish", "en:peanuts",
            "en:soybeans", "en:milk", "en:nuts", "en:celery", "en:mustard",
            "en:sesame-seeds", "en:sulphur-dioxide-and-sulphites", "en:lupin", "en:molluscs"
    };

    public static final String[] NOMBRES = {
            "Gluten", "Crustáceos", "Huevos", "Pescado", "Cacahuetes",
            "Soja", "Lácteos", "Frutos de cáscara", "Apio", "Mostaza",
            "Sésamo", "Sulfitos", "Altramuces", "Moluscos"
    };

    private Alergenos() {
    }

    public static String nombre(String tag) {
        for (int i = 0; i < TAGS.length; i++) {
            if (TAGS[i].equals(tag)) {
                return NOMBRES[i];
            }
        }
        return tag.replace("en:", "");
    }
}