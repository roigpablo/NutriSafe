package com.nutrisafe.data.remote;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class RetrofitClient {

    private static final String BASE_URL = "https://world.openfoodfacts.org/";
    private static final String USER_AGENT =
            "NutriSafe/1.0 (https://github.com/roigpablo/NutriSafe)";

    private static OpenFoodFactsApi api;

    private RetrofitClient() {
    }

    public static synchronized OpenFoodFactsApi getApi() {
        if (api == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC);

            OkHttpClient cliente = new OkHttpClient.Builder()
                    .addInterceptor(chain -> chain.proceed(
                            chain.request().newBuilder()
                                    .header("User-Agent", USER_AGENT)
                                    .build()))
                    .addInterceptor(logging)
                    .build();

            api = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(cliente)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(OpenFoodFactsApi.class);
        }
        return api;
    }
}