package com.example.akaash.assignment.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by akaash on 12/1/18.
 */

public class ApiClient {
    public static final String BASE_URL = "https://api.github.com/";
    private static Retrofit retrofit = null;

    public static Retrofit getCLient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
