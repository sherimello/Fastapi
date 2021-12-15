package com.example.fastapi.classes.calls;

import android.app.Activity;

import com.example.fastapi.classes.Headers;
import com.example.fastapi.classes.PostsConstants;
import com.example.fastapi.classes.modal;
import com.example.fastapi.interfaces.JsonPlaceHolderApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class GetPostsCalls {

    private Activity activity;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private Call<List<PostsConstants>> call;


    public GetPostsCalls(Activity activity) {
        this.activity = activity;
    }

    public Call<List<PostsConstants>> getPostsGetAPICall(String endpoint) {

            Retrofit retrofit = new Retrofit.Builder()
                    .client(new Headers().getHeaderClient(activity))
                    .baseUrl(PostsConstants.getRootUrl())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

            return jsonPlaceHolderApi.getAllPosts(endpoint);

    }

    public Call<PostsConstants> getOnePostGetAPICall(String endpoint) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PostsConstants.getRootUrl())
                .client(new Headers().getHeaderClient(activity))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        return jsonPlaceHolderApi.getOnePost(endpoint);

    }

    public Call<String> getPostCountGETAPIcall(String endpoint) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PostsConstants.getRootUrl())
                .client(new Headers().getHeaderClient(activity))
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        return jsonPlaceHolderApi.getPostCount(endpoint);

    }

    public Call<modal> getPostCreationAPICall() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PostsConstants.getRootUrl())
                .client(new Headers().getHeaderClient(activity))
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi retrofitAPI = retrofit.create(JsonPlaceHolderApi.class);


        return retrofitAPI.createPost(new modal(
                true, "inan", "new", ""));

    }
}
