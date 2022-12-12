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
import com.example.resturantapp.databinding.ActivityBeerBinding;
import com.example.resturantapp.databinding.ActivityOrderBinding;
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

public class Beer extends DrawerBaseActivity {
    private RecyclerView mFirestoreBeer;
    private FirebaseFirestore firebaseFirestore;
    MenuAdapters menuAdapters;
    List<EntreeModel> menuModelList;
    FloatingActionButton viewCart;

    ActivityBeerBinding activityBeerBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBeerBinding = ActivityBeerBinding.inflate(getLayoutInflater());
        setContentView(activityBeerBinding.getRoot());
        allocateActivityTitle("Beer");

        firebaseFirestore = FirebaseFirestore.getInstance();
        mFirestoreBeer = findViewById(R.id.fireStore_Beer);
        mFirestoreBeer.setLayoutManager(new LinearLayoutManager(this));

        menuModelList = new ArrayList<>();
        menuAdapters = new MenuAdapters(this, menuModelList);
        viewCart = (FloatingActionButton) findViewById(R.id.vewCart);

        mFirestoreBeer.setAdapter(menuAdapters);

        EventChangeListner();

        viewCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Beer.this, MyCart.class));
            }
        });

    }

    private void EventChangeListner() {

        firebaseFirestore.collection("Beers").orderBy("name", Query.Direction.ASCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
