package com.example.resturantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class ContactUs extends AppCompatActivity {

    EditText textFullName, textMessage;
    Button btnCall, btnMessage;
    ImageView imageClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        textFullName = findViewById(R.id.textFullName);

        textMessage = findViewById(R.id.textMessage);

        btnCall = findViewById(R.id.btnCall);


        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:0451352524"));
                startActivity(intent);
            }
        });

        btnMessage = findViewById(R.id.btnMessage);
        btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();
            }
        });

        imageClose = findViewById(R.id.imageClose);
        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void sendMail() {
        String fullName = textFullName.getText().toString();
        String message = textMessage.getText().toString();
        String [] TO = {"gthakuri656@gmail.com"};

        Intent i = new Intent(Intent.ACTION_SEND);
        i.putExtra(Intent.EXTRA_EMAIL, TO);
        i.putExtra(Intent.EXTRA_SUBJECT, fullName);
        i.putExtra(Intent.EXTRA_TEXT, message);

        i.setType("message/rfc822");
        startActivity(Intent.createChooser(i, "Choose an email client"));




    }
}