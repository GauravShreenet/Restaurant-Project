package com.example.resturantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.resturantapp.adapters.MenuAdapters;
import com.example.resturantapp.databinding.ActivityOrderBinding;
import com.example.resturantapp.databinding.ActivitySaladBinding;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Salad extends DrawerBaseActivity {
    private RecyclerView mFirestoreSalad;
    private FirebaseFirestore firebaseFirestore;
    MenuAdapters menuAdapters;
    List<EntreeModel> menuModelList;
    FloatingActionButton viewCart;

    ActivitySaladBinding activitySaladBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activitySaladBinding = ActivitySaladBinding.inflate(getLayoutInflater());
        setContentView(activitySaladBinding.getRoot());
        allocateActivityTitle("Salad");

        firebaseFirestore = FirebaseFirestore.getInstance();
        mFirestoreSalad = findViewById(R.id.fireStore_Salad);
        mFirestoreSalad.setLayoutManager(new LinearLayoutManager(this));
        viewCart = (FloatingActionButton) findViewById(R.id.vewCart);

        menuModelList = new ArrayList<>();
        menuAdapters = new MenuAdapters(this, menuModelList);


        mFirestoreSalad.setAdapter(menuAdapters);

        EventChangeListner();

        viewCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Salad.this, MyCart.class));
            }
        });

    }

    private void EventChangeListner() {

        firebaseFirestore.collection("Salads").orderBy("name", Query.Direction.ASCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for(DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){
                    EntreeModel entreeModel = documentSnapshot.toObject(EntreeModel.class);
                    menuModelList.add(entreeModel);
                    menuAdapters.notifyDataSetChanged();
                }
            }
        });
    }


}