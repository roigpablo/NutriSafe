package com.nutrisafe.data.remote;

import com.google.gson.annotations.SerializedName;

public class NutrimentosDto {
    @SerializedName("energy-kcal_100g")
    public Double kcal100g;

    @SerializedName("sugars_100g")
    public Double azucares100g;
}