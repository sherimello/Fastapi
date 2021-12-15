package com.example.fastapi.classes;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.fastapi.activity.MainActivity;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class Headers {

    public OkHttpClient getHeaderClient(Activity activity) {

        String token;

        SharedPreferences prefs = activity.getSharedPreferences(
                "fastapi.access_token", Context.MODE_PRIVATE);
        token = prefs.getString("token", "");

        return new OkHttpClient.Builder().addInterceptor(chain -> {
            Request newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer " + token)
                    .build();
            return chain.proceed(newRequest);
        }).build();
    }
}
