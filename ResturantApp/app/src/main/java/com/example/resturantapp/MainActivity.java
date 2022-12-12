package com.example.resturantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView textNoAccount, textForgot;
    private EditText textEmail, textPassword;
    private Button bttnLogin;

    FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textNoAccount = (TextView) findViewById(R.id.textNoAccount);
        textNoAccount.setOnClickListener(this);

        textForgot = (TextView) findViewById(R.id.textForgot);
        textForgot.setOnClickListener(this);

        bttnLogin = (Button) findViewById(R.id.bttnLogin);
        bttnLogin.setOnClickListener(this);

        textEmail = (EditText) findViewById(R.id.textEmail);
        textPassword = (EditText) findViewById(R.id.textPassword);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textNoAccount:
                startActivity(new Intent(this, RegisterUser.class));
                break;

            case R.id.textForgot:
                startActivity(new Intent(this, ForgotPassword.class));
                break;

            case R.id.bttnLogin:
                userLogin();
                break;
        }
    }

    private void userLogin() {
        String email = textEmail.getText().toString().trim();
        String password = textPassword.getText().toString().trim();

        if (email.isEmpty()) {
            textEmail.setError("Email is required");
            textEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            textPassword.setError("Password is required!");
            textPassword.requestFocus();
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //redirect to user profile
                    startActivity(new Intent(MainActivity.this, HomePage.class));

                }else{
                    Toast.makeText(MainActivity.this, "Failed to login! Please check your credentials", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
                finish();
            }
        });
    }
}