package com.example.retailor.Network;

import android.util.Base64;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkUtils {
    public static final String API_BASE_URL = "http://176.126.167.172:81";
    private static final String AUTH = "Basic " + Base64.encodeToString(("test:12345").getBytes(), Base64.NO_WRAP);

    static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addInterceptor(
                    new Interceptor() {
                        @Override
                        public okhttp3.Response intercept(Chain chain) throws IOException {
                            Request original = chain.request();

                            Request.Builder builder = original.newBuilder()
                                    .addHeader("Authorization", AUTH)
                                    .method(original.method(), original.body());

                            Request request = builder.build();
                            return chain.proceed(request);
                        }
                    }
            ).build();

    public static Retrofit getRetrofit(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit;
    }
}
