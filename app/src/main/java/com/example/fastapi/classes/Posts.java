package com.example.fastapi.classes;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastapi.activity.MainActivity;
import com.example.fastapi.activity.SignIn;
import com.example.fastapi.adapters.PostCardAdapter;
import com.example.fastapi.classes.calls.GetPostsCalls;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Posts {

    private TextView textView;
    private RecyclerView recyclerView;
    public Activity context;
    private ArrayList<PostsConstants> arrayList;
    private Button button_seen;
    private CardView card_new_item;
    private int count = 0;
    private static final int MILLISECONDS_PER_INCH = 50;

    public Posts(TextView textView, RecyclerView recyclerView, Activity context, ArrayList<PostsConstants> arrayList, Button button_seen, CardView card_new_item) {
        this.textView = textView;
        this.recyclerView = recyclerView;
        this.context = context;
        this.arrayList = arrayList;
        this.button_seen = button_seen;
        this.card_new_item = card_new_item;
    }

    public void fetchOnePost() {
        new Thread(() -> new GetPostsCalls(context).getOnePostGetAPICall("/posts/1").enqueue(new Callback<PostsConstants>() {
            @Override
            public void onResponse(@NonNull Call<PostsConstants> call, @NonNull Response<PostsConstants> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(context, "no post found!", Toast.LENGTH_SHORT).show();
                    textView.setVisibility(View.VISIBLE);
                    textView.setText(MessageFormat.format("Code: {0}", response.code()));
                    return;
                }
                assert response.body() != null;
                PostsConstants postsConstants1 = response.body();

                arrayList.add(new PostsConstants(postsConstants1.getPublished(),
                        postsConstants1.getTitle(), postsConstants1.getContent(),
                        postsConstants1.getCreated_at()));

                new MainActivity().runOnUiThread(() -> {
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setAdapter(new PostCardAdapter(arrayList));
                });
            }

            @Override
            public void onFailure(@NonNull Call<PostsConstants> call, @NonNull Throwable t) {
                textView.setText(t.getMessage());
                textView.setVisibility(View.VISIBLE);
            }
        })).start();

    }

    public void fetchPosts() {
        new Thread(() -> {
            new GetPostsCalls(context).getPostsGetAPICall("posts/users/Post").enqueue(new Callback<List<PostsConstants>>() {
                @Override
                public void onResponse(@NonNull Call<List<PostsConstants>> call, @NonNull Response<List<PostsConstants>> response) {
                    if (!response.isSuccessful()) {
                        (context).runOnUiThread(() -> {
                            textView.setVisibility(View.VISIBLE);
                            textView.setText(MessageFormat.format("Code: {0} ({1})", response.code(), Objects.requireNonNull(response.message())));
                            context.startActivity(new Intent(context, SignIn.class));
                            context.finish();
                        });

                        return;
                    }
                    List<PostsConstants> constants = response.body();
                    assert constants != null;
                    count = constants.size();
                    arrayList.clear();
                    for (PostsConstants postsConstants1 : constants) {

                        arrayList.add(new PostsConstants(postsConstants1.getPublished(),
                                postsConstants1.getTitle(), postsConstants1.getContent(),
                                postsConstants1.getCreated_at()));

                    }
                    new MainActivity().runOnUiThread(() -> {
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                        linearLayoutManager.setSmoothScrollbarEnabled(true);
                        recyclerView.setLayoutManager(linearLayoutManager);
                        recyclerView.setHasFixedSize(true);
                        new Handler().postDelayed(() -> {
                            LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(recyclerView.getContext()) {

                                @Override
                                protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                                    return (float) MILLISECONDS_PER_INCH / (displayMetrics.densityDpi);
                                }
                            };

                            if (arrayList.size() != 0) {
                                linearSmoothScroller.setTargetPosition(arrayList.size() - 1);
                                linearLayoutManager.startSmoothScroll(linearSmoothScroller);
                            }

                        }, 0);
                        recyclerView.setAdapter(new PostCardAdapter(arrayList));
                        //                        recyclerView.scrollToPosition(arrayList.size() - 1);
                        changeVisibilityOfNewPostCard();
                    });
                }

                @Override
                public void onFailure(@NonNull Call<List<PostsConstants>> call, @NonNull Throwable t) {
                    button_seen.setEnabled(false);
                    textView.setText(t.getMessage());
                    textView.setVisibility(View.VISIBLE);
                    context.startActivity(new Intent(context, SignIn.class));
                    context.finish();

                }
            });
        }).start();

    }

    public void changeVisibilityOfNewPostCard() {
        new Thread(() -> new GetPostsCalls(context).getPostCountGETAPIcall("posts/post/count").enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
//                assert response.body() != null;
                if (response.body() == null) {
                    assert response.errorBody() != null;
                    context.startActivity(new Intent(context, SignIn.class));
                    context.finish();
                    textView.setError(response.errorBody().toString());
                    return;
                }
                new MainActivity().runOnUiThread(() -> {
                    if (!response.body().equals(String.valueOf(count))) {
                        button_seen.setEnabled(true);
                        Toast.makeText(context, response.body(), Toast.LENGTH_SHORT).show();
                        button_seen.setVisibility(View.VISIBLE);
                        card_new_item.setVisibility(View.VISIBLE);
                        return;
                    }
                    button_seen.setEnabled(false);
                    card_new_item.setVisibility(View.GONE);
                    button_seen.setVisibility(View.GONE);
                    new Handler().postDelayed(Posts.this::changeVisibilityOfNewPostCard, 2000);
                });
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                button_seen.setEnabled(false);
                textView.setText(t.getMessage());
                textView.setVisibility(View.VISIBLE);
            }
        })).start();
    }


    public void sendFeedbackRequest() {
        new Thread(() -> {
            new GetPostsCalls(context).getPostCreationAPICall().enqueue(new Callback<modal>() {
                @Override
                public void onResponse(@NonNull Call<modal> call, @NonNull Response<modal> response) {

                    new MainActivity().runOnUiThread(() -> {

                        if (!response.isSuccessful()) {
                            Toast.makeText(context, "failed!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Toast.makeText(context, "Data added to API", Toast.LENGTH_SHORT).show();

                        textView.setText("");

                        modal responseFromAPI = response.body();
                        textView.setVisibility(View.VISIBLE);
                        if (responseFromAPI == null) {

                            textView.setText(String.valueOf(response.code()));
                            Toast.makeText(context, "empty", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        String responseString = "Response Code : " + response.code() + "\nTitle : " + responseFromAPI.getTitle()
                                + "\n" + "Content : " + responseFromAPI.getContent()
                                + "\n" + "Published: " + responseFromAPI.isPublished()
                                + "\n" + "Created at: " + responseFromAPI.getCreated_at();


                        textView.setText(responseString);
                    });

                }

                @Override
                public void onFailure(Call<modal> call, Throwable t) {
                    textView.setText(MessageFormat.format("Error found is : {0}", t.getMessage()));
                }
            });
        }).start();


    }


}
