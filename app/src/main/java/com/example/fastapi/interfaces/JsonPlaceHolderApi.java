package com.example.fastapi.interfaces;

import com.example.fastapi.classes.AuthToken;
import com.example.fastapi.classes.PostsConstants;
import com.example.fastapi.classes.UsersConstants;
import com.example.fastapi.classes.modal;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface JsonPlaceHolderApi {
    //    @Header("Authorization") String header,
    @GET
    Call<List<PostsConstants>> getAllPosts(@Url String url);

    @GET
    Call<PostsConstants> getOnePost(@Url String url);

    @GET
    Call<String> getPostCount(@Url String url);

    @FormUrlEncoded
    @POST
    Call<AuthToken> login(@Url String url,
                          @Field("username") String email,
                          @Field("password") String password);

    @POST
    Call<AuthToken> register(@Url String url,
                             @Body UsersConstants usersConstants);


    @POST("posts")
    Call<modal> createPost(
            @Body modal m
    );
}

