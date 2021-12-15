package com.example.fastapi.classes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.fastapi.activity.MainActivity;
import com.example.fastapi.classes.calls.GetUsersCalls;

import java.text.MessageFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Users {
    private TextView text_id;
    private Activity activity;

    public Users(TextView text_id, Activity activity) {
        this.text_id = text_id;
        this.activity = activity;
    }

    public void loginUser(String email, String password) {


//        Toast.makeText(text_id.getContext(), "auth?username=" + email + "&password="
//                + password + "/", Toast.LENGTH_SHORT).show();
        new Thread(() -> {
            new GetUsersCalls().getUserLoginGETAPICall("auth"
                    , new UsersConstants(email, password))
                    .enqueue(new Callback<AuthToken>() {
                        @Override
                        public void onResponse(@NonNull Call<AuthToken> call, @NonNull Response<AuthToken> response) {
                            if (!response.isSuccessful()) {
                                text_id.setText(MessageFormat.format("Error: {0}{1}", String.valueOf(response.code()), response.errorBody()));
                                return;
                            }
//                            assert response.body() != null;
                            assert response.body() != null;
                            new MainActivity().runOnUiThread(() -> {

                                SharedPreferences prefs = text_id.getContext().getSharedPreferences(
                                        "fastapi.access_token", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putString("token", response.body().getAccess_token());
                                editor.putString("type", response.body().getToken_type());
                                editor.apply();
                                activity.startActivity(new Intent(activity, MainActivity.class));
                                activity.finish();
                                text_id.setText(MessageFormat.format("{0}\n{1}", response.body().getAccess_token(), response.body().getToken_type()));

                            });
                        }

                        @Override
                        public void onFailure(@NonNull Call<AuthToken> call, @NonNull Throwable t) {
                            text_id.setText(t.getLocalizedMessage());
                        }
                    });
        }).start();
    }

    public void registerUser(String email, String password) {
        new Thread(() -> {
            new GetUsersCalls().getUserRegisterGETAPICall("users"
                    , new UsersConstants(email, password))
                    .enqueue(new Callback<AuthToken>() {
                        @Override
                        public void onResponse(@NonNull Call<AuthToken> call, @NonNull Response<AuthToken> response) {
                            if (!response.isSuccessful()) {
                                text_id.setText(MessageFormat.format("Error: {0}{1}", String.valueOf(response.code()), response.errorBody()));
                                return;
                            }
//                            assert response.body() != null;
                            assert response.body() != null;
                            new MainActivity().runOnUiThread(() -> {

                                SharedPreferences prefs = text_id.getContext().getSharedPreferences(
                                        "fastapi.access_token", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putString("token", response.body().getAccess_token());
                                editor.putString("type", response.body().getToken_type());
                                editor.apply();
                                activity.startActivity(new Intent(activity, MainActivity.class));
                                activity.finish();
                                text_id.setText(MessageFormat.format("{0}\n{1}", response.body().getAccess_token(), response.body().getToken_type()));

                            });
                        }

                        @Override
                        public void onFailure(@NonNull Call<AuthToken> call, @NonNull Throwable t) {
                            text_id.setText(t.getLocalizedMessage());
                        }
                    });
        }).start();
    }
}
