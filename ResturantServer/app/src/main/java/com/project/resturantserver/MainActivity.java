package com.project.resturantserver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    FirebaseAuth mFirebaseAuth;
    Button manageMenu, btnLogOut, viewOrder, bookingConfirm;

    @Override
    protected void onStart() {
        super.onStart();

        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if(mFirebaseUser != null){
            //there is user logged
        }else{
            //no one logged in
            startActivity(new Intent(this, SignInActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manageMenu = (Button) findViewById(R.id.manageMenu);
        manageMenu.setOnClickListener(this);

        viewOrder = (Button) findViewById(R.id.viewOrder);
        viewOrder.setOnClickListener(this);

        btnLogOut = (Button) findViewById(R.id.btnLogOut);
        btnLogOut.setOnClickListener(this);

        bookingConfirm = (Button) findViewById(R.id.btnBooking);
        bookingConfirm.setOnClickListener(this);

        FirebaseMessaging firebaseMessaging = FirebaseMessaging.getInstance();
        firebaseMessaging.subscribeToTopic("new_user_forums");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.manageMenu:
                startActivity(new Intent(this, MenuActivity.class));
                break;

            case R.id.btnLogOut:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this, SignInActivity.class));
                break;

            case R.id.viewOrder:
                startActivity(new Intent(this, ViewOrder.class));
                break;

            case R.id.btnBooking:
                startActivity(new Intent(this, BookingConfirm.class));
                break;
        }


    }
}