package com.nutrisafe.data.remote;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultadoDto {
    public String code;

    @SerializedName("product_name")
    public String nombre;

    public List<String> brands;

    @SerializedName("image_small_url")
    public String imagenUrl;
}