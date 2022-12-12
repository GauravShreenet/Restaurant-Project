package com.example.resturantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.resturantapp.adapters.MenuAdapters;
import com.example.resturantapp.databinding.ActivityDessertBinding;
import com.example.resturantapp.databinding.ActivityOrderBinding;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Dessert extends DrawerBaseActivity {
    private RecyclerView mFirestoreDessert;
    private FirebaseFirestore firebaseFirestore;
    MenuAdapters menuAdapters;
    List<EntreeModel> menuModelList;
    ActivityDessertBinding activityDessertBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityDessertBinding = ActivityDessertBinding.inflate(getLayoutInflater());
        setContentView(activityDessertBinding.getRoot());
        allocateActivityTitle("Dessert");

        firebaseFirestore = FirebaseFirestore.getInstance();
        mFirestoreDessert = findViewById(R.id.fireStore_Dessert);
        mFirestoreDessert.setLayoutManager(new LinearLayoutManager(this));

        menuModelList = new ArrayList<>();
        menuAdapters = new MenuAdapters(this, menuModelList);

        mFirestoreDessert.setAdapter(menuAdapters);

        EventChangeListner();



    }

    private void EventChangeListner() {

        firebaseFirestore.collection("Dessert").orderBy("name", Query.Direction.ASCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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