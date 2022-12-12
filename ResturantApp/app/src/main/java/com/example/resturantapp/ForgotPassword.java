package com.example.resturantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity implements View.OnClickListener{
    private TextView textHaveAcc2;
    private EditText textEmailReg2;
    private Button bttnRegister2;
    private ProgressBar progressBar3;

    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        textHaveAcc2 = (TextView) findViewById(R.id.textHaveAcc2);
        textHaveAcc2.setOnClickListener(this);

        textEmailReg2 = (EditText) findViewById(R.id.textEmailReg2);

        bttnRegister2 = (Button) findViewById(R.id.bttnRegister2);
        bttnRegister2.setOnClickListener(this);

        progressBar3 = (ProgressBar) findViewById(R.id.progressBar3);

        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.textHaveAcc2:
                startActivity(new Intent(this, RegisterUser.class));
                break;

            case R.id.bttnRegister2:
                resetPassword();


        }
    }

    private void resetPassword() {
        String email = textEmailReg2.getText().toString().trim();

        if(email.isEmpty()){
            textEmailReg2.setError("Email is required!");
            textEmailReg2.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            textEmailReg2.setError("Please provide valid email!");
            textEmailReg2.requestFocus();
            return;
        }

        progressBar3.setVisibility(View.VISIBLE);
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForgotPassword.this, "Check Your email to reset your password!", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ForgotPassword.this, "Try again! Something went wrong", Toast.LENGTH_SHORT).show();
                }
                progressBar3.setVisibility(View.GONE);
                finish();
            }
        });
    }
}