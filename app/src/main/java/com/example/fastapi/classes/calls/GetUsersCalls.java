package com.example.fastapi.classes.calls;

import android.widget.Toast;

import com.example.fastapi.classes.AuthToken;
import com.example.fastapi.classes.PostsConstants;
import com.example.fastapi.classes.UsersConstants;
import com.example.fastapi.classes.modal;
import com.example.fastapi.interfaces.JsonPlaceHolderApi;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class GetUsersCalls {
//auth?username=123@gmail.com&password=111
    public Call<AuthToken> getUserLoginGETAPICall(String endpoint, UsersConstants usersConstants) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UsersConstants.getRootUrl())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        JsonPlaceHolderApi retrofitAPI = retrofit.create(JsonPlaceHolderApi.class);

        return retrofitAPI.login(endpoint, usersConstants.getemail(), usersConstants.getPassword());

    }
    public Call<AuthToken> getUserRegisterGETAPICall(String endpoint, UsersConstants usersConstants) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UsersConstants.getRootUrl())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        JsonPlaceHolderApi retrofitAPI = retrofit.create(JsonPlaceHolderApi.class);

        return retrofitAPI.register(endpoint, usersConstants);

    }

}
