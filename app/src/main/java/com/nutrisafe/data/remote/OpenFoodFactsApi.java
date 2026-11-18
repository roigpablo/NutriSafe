package com.nutrisafe.data.remote;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OpenFoodFactsApi {

    String CAMPOS = "code,product_name,brands,image_url,ingredients_text,"
            + "allergens_tags,traces_tags,nutriments";

    @GET("api/v2/product/{codigo}.json")
    Call<ProductoResponse> obtenerProducto(
            @Path("codigo") String codigo,
            @Query("fields") String campos);
}