package com.equipo.superttapp.util;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
<<<<<<< HEAD
    private static final String BASE_URL = "http://10.100.65.210:8000/";
=======
    private static final String BASE_URL = "http://10.100.75.9:8000/";
>>>>>>> 5d468c62af1d2321752cfeaa74941de0da59bd8b
    private static Retrofit.Builder builder = new Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private static HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();

    public static <S> S createService(Class<S> serviceClass) {
        if (!httpClient.interceptors().contains(loggingInterceptor)) {
            loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(loggingInterceptor);
            builder.client(httpClient.build());
            retrofit = builder.build();
        }
        return retrofit.create(serviceClass);
    }
}
