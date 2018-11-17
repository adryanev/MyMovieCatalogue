package com.adryanev.dicoding.mymoviecatalogue.rest;

import com.adryanev.dicoding.mymoviecatalogue.config.Config;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient(){
        if(retrofit == null){

            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    HttpUrl url = request.url().newBuilder().addQueryParameter("api_key",Config.API_KEY)
                            .build();
                    request = request.newBuilder().url(url).build();
                    return chain.proceed(request);
                }
            }).build();
            retrofit = new Retrofit.Builder().client(client).baseUrl(Config.API_ENDPOINT).addConverterFactory(GsonConverterFactory.create()).build();

        }
        return retrofit;
    }
}
