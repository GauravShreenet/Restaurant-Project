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
import android.widget.FrameLayout;

import com.google.android.material.navigation.NavigationView;

public class DrawerBaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;

    @Override
    public void setContentView(View view) {
        drawerLayout = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_drawer_base, null);
        FrameLayout container = drawerLayout.findViewById(R.id.activityContainer);
        container.addView(view);
        super.setContentView(drawerLayout);

        Toolbar toolbar = drawerLayout.findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        NavigationView navigationView = drawerLayout.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar, R.string.menu_drawer_open, R.string.menu_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


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

    protected void allocateActivityTitle(String titleString){
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(titleString);
        }
    }
}