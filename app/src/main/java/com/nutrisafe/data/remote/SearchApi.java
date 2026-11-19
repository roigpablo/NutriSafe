package com.nutrisafe.data.remote;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SearchApi {

    String CAMPOS = "code,product_name,brands,image_small_url";

    @GET("search")
    Call<BusquedaResponse> buscar(
            @Query("q") String texto,
            @Query("page_size") int tamano,
            @Query("langs") String idioma,
            @Query("fields") String campos);
}