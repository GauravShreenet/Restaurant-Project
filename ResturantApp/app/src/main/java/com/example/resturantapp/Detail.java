package com.example.resturantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class Detail extends AppCompatActivity {

    TextView quantity;
    int totalQuantity = 1;
    double totalPrice = 0;
    ImageView detailedImg;
    TextView price, description, detail;
    Button addToCart;
    ImageView addItem, removeItem;

    FirebaseFirestore firestore;
    FirebaseAuth auth;

    EntreeModel entreeModel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        final Object object = getIntent().getSerializableExtra("detail");
        if(object instanceof EntreeModel){
            entreeModel = (EntreeModel) object;
        }

        quantity = findViewById(R.id.quantity);
        detailedImg = findViewById(R.id.detail_img);
        addItem = findViewById(R.id.add_item);
        removeItem = findViewById(R.id.remove_item);

        price = findViewById(R.id.detail_price);
        description = findViewById(R.id.detail_des);
        detail = findViewById(R.id.textView6);

        if (entreeModel != null){
            Glide.with(getApplicationContext()).load(entreeModel.getUrl()).into(detailedImg);
            price.setText("$" + entreeModel.getPrice() + "");
            detail.setText(entreeModel.getName());

            totalPrice = (double) (entreeModel.getPrice() * totalQuantity);
        }

        addToCart = findViewById(R.id.add_to_cart);
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addedToCart();
            }
        });
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (totalQuantity < 10){
                    totalQuantity ++;
                    quantity.setText(String.valueOf(totalQuantity));
                    totalPrice = (double) (entreeModel.getPrice() * totalQuantity);
                }

            }
        });

        removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (totalQuantity > 1){
                    totalQuantity --;
                    quantity.setText(String.valueOf(totalQuantity));
                    totalPrice = (double) (entreeModel.getPrice() * totalQuantity);
                }

            }
        });

    }

    private void addedToCart() {
        String saveCurrentDate, saveCurrentTime;
        Calendar calForDate = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());

        final HashMap<String,Object> cartMap = new HashMap<>();

            cartMap.put("productName", entreeModel.getName());
            cartMap.put("productPrice", price.getText().toString());
            cartMap.put("currentDate", saveCurrentDate);
            cartMap.put("currentTime", saveCurrentTime);
            cartMap.put("totalQuantity", Integer.parseInt((String) quantity.getText()));
            cartMap.put("totalPrice", totalPrice);

        firestore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("AddToCart").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(Detail.this,  "Added to the Cart", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }




}