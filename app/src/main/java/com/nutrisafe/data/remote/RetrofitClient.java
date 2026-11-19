package com.nutrisafe.data.remote;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class RetrofitClient {

    private static final String BASE_URL_PRODUCTOS = "https://world.openfoodfacts.org/";
    private static final String BASE_URL_BUSQUEDA = "https://search.openfoodfacts.org/";
    private static final String USER_AGENT =
            "NutriSafe/1.0 (https://github.com/roigpablo/NutriSafe)";

    private static OkHttpClient cliente;
    private static OpenFoodFactsApi apiProductos;
    private static SearchApi apiBusqueda;

    private RetrofitClient() {
    }

    public static synchronized OpenFoodFactsApi getApi() {
        if (apiProductos == null) {
            apiProductos = crearRetrofit(BASE_URL_PRODUCTOS).create(OpenFoodFactsApi.class);
        }
        return apiProductos;
    }

    public static synchronized SearchApi getSearchApi() {
        if (apiBusqueda == null) {
            apiBusqueda = crearRetrofit(BASE_URL_BUSQUEDA).create(SearchApi.class);
        }
        return apiBusqueda;
    }

    private static Retrofit crearRetrofit(String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(getCliente())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private static OkHttpClient getCliente() {
        if (cliente == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

            cliente = new OkHttpClient.Builder()
                    .addInterceptor(chain -> chain.proceed(
                            chain.request().newBuilder()
                                    .header("User-Agent", USER_AGENT)
                                    .build()))
                    .addInterceptor(logging)
                    .build();
        }
        return cliente;
    }
}