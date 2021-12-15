package com.example.fastapi.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastapi.R;
import com.example.fastapi.classes.Posts;
import com.example.fastapi.classes.PostsConstants;
import com.example.fastapi.classes.calls.GetPostsCalls;
import com.example.fastapi.classes.modal;
import com.example.fastapi.interfaces.JsonPlaceHolderApi;

import java.text.MessageFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edit_email, edit_password;
    private Button button_sign_in, button_seen;
    private TextView textView;
    private RecyclerView recyclerView;
    private CardView card_new_item;
    private ArrayList<PostsConstants> arrayList;
    private Posts posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arrayList = new ArrayList<>();

        recyclerView = findViewById(R.id.recycler);
        textView = findViewById(R.id.textView);
        card_new_item = findViewById(R.id.card_new_item);
        button_seen = findViewById(R.id.button_seen);

        textView.setVisibility(View.GONE);
        button_seen.setEnabled(false);

        posts = new Posts(textView, recyclerView, this
                , arrayList, button_seen, card_new_item);

        posts.fetchPosts();
//        posts.fetchOnePost();
//        posts.sendFeedbackRequest();


        button_seen.setOnClickListener(this);

    }

    public void goToSignIn(Context context){
        startActivity(new Intent(context, SignIn.class));
        finish();
    }

    @Override
    public void onClick(View view) {
        if (view == button_seen) {
            posts.fetchPosts();
            button_seen.setVisibility(View.GONE);
            card_new_item.setVisibility(View.GONE);
            button_seen.setEnabled(false);
        }
    }
}