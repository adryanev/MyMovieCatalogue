package com.adryanev.dicoding.mymoviecatalogue.utils;

import com.adryanev.dicoding.mymoviecatalogue.config.Config;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class RetrofitClient {

    private static Retrofit retrofit;

    public static Retrofit getClient(){
        if(retrofit == null){
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Timber.i(message);
                }
            });
            interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

            Interceptor urlInterceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    HttpUrl url = request.url().newBuilder().addQueryParameter("api_key", Config.API_KEY)
                            .build();
                    request = request.newBuilder().url(url).build();
                    return chain.proceed(request);
                }
            };

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .addInterceptor(urlInterceptor)
                    .addNetworkInterceptor(new StethoInterceptor())
                    .build();

            retrofit = new Retrofit.Builder().baseUrl(Config.API_ENDPOINT).client(client).addConverterFactory(GsonConverterFactory.create()).build();
           return retrofit;
        }

        return retrofit;
    }
}
