package com.example.fastapi.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fastapi.R;
import com.example.fastapi.classes.Users;

public class SignIn extends AppCompatActivity implements View.OnClickListener {

    private TextView text_id, text_register, text_signin;
    private EditText edit_password, edit_email;
    private Button button_signin, button_register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        text_id = findViewById(R.id.text_id);
        text_register = findViewById(R.id.text_register);
        text_signin = findViewById(R.id.text_sign_in);
        edit_email = findViewById(R.id.edit_email);
        edit_password = findViewById(R.id.edit_password);
        button_signin = findViewById(R.id.button_sign_in);
        button_register = findViewById(R.id.button_register);

        button_signin.setOnClickListener(this);
        button_register.setOnClickListener(this);
        text_signin.setOnClickListener(this);
        text_register.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view == button_signin) {
            new Users(text_id, this).loginUser(edit_email.getText().toString().trim(),
                    edit_password.getText().toString().trim());
        }
        if (view == button_register) {
            //registration api gets called here...
            new Users(text_id, this).registerUser(edit_email.getText().toString().trim(),
                    edit_password.getText().toString().trim());
        }
        if (view == text_register) {
            button_register.setVisibility(View.VISIBLE);
            button_signin.setVisibility(View.GONE);
            text_signin.setVisibility(View.VISIBLE);
            text_register.setVisibility(View.GONE);
        }
        if (view == text_signin) {
            button_register.setVisibility(View.GONE);
            button_signin.setVisibility(View.VISIBLE);
            text_signin.setVisibility(View.GONE);
            text_register.setVisibility(View.VISIBLE);
        }
    }
}