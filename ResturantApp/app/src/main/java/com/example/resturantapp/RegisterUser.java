package com.example.resturantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;


public class RegisterUser extends AppCompatActivity implements View.OnClickListener {

    //declaring the variables
    private TextView textHaveAcc;
    private EditText textFirst, textLastN, textEmailReg, textPhone, textPassGet, textPassConf;
    private Button bttnRegister;
    private ProgressBar progressBar2;
    FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        mAuth = FirebaseAuth.getInstance();

        textHaveAcc = (TextView) findViewById(R.id.textHaveAcc);
        textHaveAcc.setOnClickListener(this);

        textFirst = (EditText) findViewById(R.id.textFirst);
        textLastN = (EditText) findViewById(R.id.textLastN);
        textEmailReg = (EditText) findViewById(R.id.textEmailReg);
        textPhone = (EditText) findViewById(R.id.textPhone);
        textPassGet = (EditText) findViewById(R.id.textPassGet);
        textPassConf = (EditText) findViewById(R.id.textPassConf);

        bttnRegister = (Button) findViewById(R.id.bttnRegister);
        bttnRegister.setOnClickListener(this);

        progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);


    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textHaveAcc:
                startActivity(new Intent(this, MainActivity.class));
                break;

            case R.id.bttnRegister:
                bttnRegister();
                break;


        }
    }



    private void bttnRegister() {
        String firstName, lastName, email, phone, passGet, passConf;

        firstName = textFirst.getText().toString().trim();
        lastName = textLastN.getText().toString().trim();
        email = textEmailReg.getText().toString().trim();
        phone = textPhone.getText().toString().trim();
        passGet = textPassGet.getText().toString().trim();
        passConf = textPassConf.getText().toString().trim();

        if(firstName.isEmpty()){
            textFirst.setError("First name is required!");
            textFirst.requestFocus();
            return;
        }

        if(lastName.isEmpty()){
            textLastN.setError("Last name is required!");
            textLastN.requestFocus();
            return;
        }
        if(email.isEmpty()){
            textEmailReg.setError("Email is required!");
            textEmailReg.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            textEmailReg.setError("Please provide valid email!");
            textEmailReg.requestFocus();
        }

        if(phone.isEmpty()){
            textPhone.setError("Phone number is required!");
            textPhone.requestFocus();
            return;
        }

        if (passGet.isEmpty()) {
            textPassGet.setError("Password is required!");
            textPassGet.requestFocus();

        }else if(passGet.length() < 6){
            textPassGet.setError("Password most be more than 6 letter");
        }

        if (!passConf.equals(passGet)){
            textPassConf.setError("Password not matched");
            textPassConf.requestFocus();
            return;
        }

        progressBar2.setVisibility(View.VISIBLE);
        //authencate the email and password
        mAuth.createUserWithEmailAndPassword(email, passGet)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //if the register have been created
                        if(task.isSuccessful()){
                            CustomerUser customerUser = new CustomerUser(firstName, lastName, email, phone);
                            //registered user as stored in the database
                            FirebaseDatabase.getInstance("https://resturant-app-93f08-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("CustomerUser")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(customerUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(RegisterUser.this, "User has been registered successfully!", Toast.LENGTH_LONG).show();
                                                progressBar2.setVisibility(View.GONE);
                                                startActivity(new Intent(RegisterUser.this, MainActivity.class));
                                            }else{
                                                Toast.makeText(RegisterUser.this, "Failed to register!Try again!", Toast.LENGTH_LONG).show();
                                                progressBar2.setVisibility(View.GONE);
                                            }
                                        }
                                    });
                        }else{
                            Toast.makeText(RegisterUser.this, "Failed to register!Try again!", Toast.LENGTH_LONG).show();

                        }
                        progressBar2.setVisibility(View.GONE);
                        finish();
                    }
                });


    }
}