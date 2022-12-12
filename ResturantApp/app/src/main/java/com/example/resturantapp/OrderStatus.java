package com.example.resturantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.resturantapp.adapters.OrderAdapter;
import com.example.resturantapp.databinding.ActivityBeerBinding;
import com.example.resturantapp.databinding.ActivityOrderStatusBinding;
import com.example.resturantapp.models.Request;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class OrderStatus extends DrawerBaseActivity {

    FirebaseFirestore db;
    FirebaseAuth firebaseAuth;

    RecyclerView recyclerView;
    OrderAdapter orderAdapter;
    List<Request> requestModelList;

    ActivityOrderStatusBinding activityOrderStatusBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityOrderStatusBinding = ActivityOrderStatusBinding.inflate(getLayoutInflater());
        setContentView(activityOrderStatusBinding.getRoot());
        allocateActivityTitle("Order Status");

        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        recyclerView = findViewById(R.id.recycleOrder);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        requestModelList = new ArrayList<>();
        orderAdapter = new OrderAdapter(this, requestModelList);
        recyclerView.setAdapter(orderAdapter);


        db.collection("Request").document(firebaseAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();

                        Request request = documentSnapshot.toObject(Request.class);

                        requestModelList.add(request);
                        orderAdapter.notifyDataSetChanged();

                        if (request != null){
                            if((request.getStatus()).equals("Order Picked Up")){
                                db.collection("OderHistory").document(firebaseAuth.getCurrentUser().getUid()).collection("History").add(request);
                                db.collection("Request").document(firebaseAuth.getCurrentUser().getUid()).delete();
                            }else if((request.getStatus()).equals("Delivered")) {
                                db.collection("OderHistory").document(firebaseAuth.getCurrentUser().getUid()).collection("History").add(request);
                                db.collection("Request").document(firebaseAuth.getCurrentUser().getUid()).delete();
                            }
                        }else {
                            Toast.makeText(OrderStatus.this, "Please order the food", Toast.LENGTH_SHORT).show();
                        }




                    }
                }

        });

    }


}