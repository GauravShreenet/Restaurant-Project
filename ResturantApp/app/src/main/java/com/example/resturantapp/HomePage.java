package com.example.resturantapp;

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


import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Map;

public class HomePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener{

    FirebaseAuth mFirebaseAuth;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle mDrawerToggle;
    Button btnOrder;



    @Override
    protected void onStart() {
        super.onStart();

        mFirebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if(mFirebaseUser != null){
            //there is user logged
        }else{
            //no one logged in
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        btnOrder = (Button) findViewById(R.id.button4);
        btnOrder.setOnClickListener(this);



        drawerLayout  = findViewById(R.id.drawerLayout);
        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        mDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));

        drawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        NavigationView navigationView = findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(this);



    }




    @Override
    public void onClick(View v) {

        startActivity(new Intent(this, Order.class));


            }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
         if (id == R.id.home) {
             startActivity(new Intent(this, HomePage.class));
         }

         if (id == R.id.profileUser) {
             startActivity(new Intent(this, ProfilePage.class));
         }

         if (id == R.id.order) {
             startActivity(new Intent(this, Order.class));

         }

        if (id == R.id.orderStatus) {
            startActivity(new Intent(this, OrderStatus.class));

        }

        if (id == R.id.contactUs) {
            startActivity(new Intent(this, ContactUs.class));

        }

        if (id == R.id.booking) {
            startActivity(new Intent(this, BookingTable.class));

        }

        if (id == R.id.bookingState) {
            startActivity(new Intent(this, BookedTable.class));

        }

        if (id == R.id.orderHis) {
            startActivity(new Intent(this, OrderHistory.class));

        }


         drawerLayout.closeDrawer(GravityCompat.START);
         return true;

    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }

    }



}