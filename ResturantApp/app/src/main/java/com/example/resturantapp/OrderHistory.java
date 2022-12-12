package com.example.resturantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.resturantapp.adapters.HistoryAdapter;
import com.example.resturantapp.databinding.ActivityBeerBinding;
import com.example.resturantapp.databinding.ActivityOrderHistoryBinding;
import com.example.resturantapp.models.HistoryModel;
import com.example.resturantapp.models.Request;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class OrderHistory extends DrawerBaseActivity {

    FirebaseFirestore db;
    FirebaseAuth firebaseAuth;

    RecyclerView recyclerView;
    HistoryAdapter historyAdapter;
    List<Request> requestList;

    ActivityOrderHistoryBinding activityOrderHistoryBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityOrderHistoryBinding = ActivityOrderHistoryBinding.inflate(getLayoutInflater());
        setContentView(activityOrderHistoryBinding.getRoot());
        allocateActivityTitle("Order History");

        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        recyclerView = findViewById(R.id.recycleHistory);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        requestList = new ArrayList<>();
        historyAdapter = new HistoryAdapter(this, requestList);
        recyclerView.setAdapter(historyAdapter);

        db.collection("OderHistory").document(firebaseAuth.getCurrentUser().getUid()).collection("History").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()){
                    Request request = documentSnapshot.toObject(Request.class);
                    requestList.add(request);
                    historyAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}