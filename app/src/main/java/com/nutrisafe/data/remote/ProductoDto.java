package com.nutrisafe.data.remote;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductoDto {
    public String code;

    @SerializedName("product_name")
    public String nombre;

    public String brands;

    @SerializedName("image_url")
    public String imagenUrl;

    @SerializedName("ingredients_text")
    public String ingredientes;

    @SerializedName("allergens_tags")
    public List<String> alergenos;

    @SerializedName("traces_tags")
    public List<String> trazas;

    public NutrimentosDto nutriments;
}
