package com.example.resturantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.resturantapp.databinding.ActivityOrderBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Order extends DrawerBaseActivity implements View.OnClickListener {
    ImageButton entreeImage, mainsImage, pizzaImage, saladImage, pastaImage, sidesImage;
    ImageButton dessertImage, drinksImage, beerImage, specialImage;
    FloatingActionButton viewCart;

    ActivityOrderBinding activityOrderBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityOrderBinding = ActivityOrderBinding.inflate(getLayoutInflater());
        setContentView(activityOrderBinding.getRoot());
        allocateActivityTitle("Menu");

        entreeImage = (ImageButton) findViewById(R.id.entreeImage);
        entreeImage.setOnClickListener(this);

        mainsImage = (ImageButton) findViewById(R.id.mainsImage);
        mainsImage.setOnClickListener(this);

        pizzaImage = (ImageButton) findViewById(R.id.pizzaImage);
        pizzaImage.setOnClickListener(this);

        saladImage = (ImageButton) findViewById(R.id.imageSalad);
        saladImage.setOnClickListener(this);

        pastaImage = (ImageButton) findViewById(R.id.imagePasta);
        pastaImage.setOnClickListener(this);

        sidesImage = (ImageButton) findViewById(R.id.sidesImage);
        sidesImage.setOnClickListener(this);

        dessertImage = (ImageButton) findViewById(R.id.dessertImage);
        dessertImage.setOnClickListener(this);

        drinksImage = (ImageButton) findViewById(R.id.drinksImage);
        drinksImage.setOnClickListener(this);

        beerImage = (ImageButton) findViewById(R.id.beerImage);
        beerImage.setOnClickListener(this);

        specialImage = (ImageButton) findViewById(R.id.specialImage);
        specialImage.setOnClickListener(this);

        viewCart = (FloatingActionButton) findViewById(R.id.vewCart);
        viewCart.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.entreeImage:
                startActivity(new Intent(this, Entree.class));
                break;

            case R.id.mainsImage:
                startActivity(new Intent(this, Mains.class));
                break;

            case R.id.pizzaImage:
                startActivity(new Intent(this, Pizza.class));
                break;

            case R.id.imageSalad:
                startActivity(new Intent(this, Salad.class));
                break;

            case R.id.imagePasta:
                startActivity(new Intent(this, Pasta.class));
                break;

            case R.id.sidesImage:
                startActivity(new Intent(this, Sides.class));
                break;

            case R.id.dessertImage:
                startActivity(new Intent(this, Dessert.class));
                break;

            case R.id.drinksImage:
                startActivity(new Intent(this, Drinks.class));
                break;

            case R.id.beerImage:
                startActivity(new Intent(this, Beer.class));
                break;

            case R.id.specialImage:
                startActivity(new Intent(this, Special.class));
                break;

            case R.id.vewCart:
                startActivity(new Intent(this, MyCart.class));
                break;
        }
    }
}