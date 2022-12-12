package com.project.resturantserver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.project.resturantserver.Common.Common;
import com.project.resturantserver.adapters.OrderAdapter;
import com.project.resturantserver.models.Request;


import java.util.ArrayList;
import java.util.List;

public class ViewOrder extends AppCompatActivity {
    FirebaseFirestore db;
    FirebaseAuth firebaseAuth;
    CollectionReference requests;

    RecyclerView recyclerView;
    OrderAdapter orderAdapter;
    List<Request> requestModelList;

    Spinner spinner;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);

        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        requests = db.collection("Request");


        recyclerView = findViewById(R.id.recycleOrder);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        requestModelList = new ArrayList<>();
        orderAdapter = new OrderAdapter(this, requestModelList);

        recyclerView.setAdapter(orderAdapter);

        db.collection("Request").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                                String documentID = documentSnapshot.getId();

                                Request request = documentSnapshot.toObject(Request.class);
                                request.setRequestID(documentID);
                                requestModelList.add(request);
                                orderAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });



    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        if (item.getTitle().equals(Common.UPDATE)){
            showUpdateDialog(requestModelList.get(item.getOrder()));
        }
        else if (item.getTitle().equals(Common.DELETE)){
            deleteOrder(requestModelList.get(item.getOrder()));
        }

        return super.onContextItemSelected(item);
    }

    private void showUpdateDialog(Request request) {
        final AlertDialog.Builder alertDialog =  new AlertDialog.Builder(ViewOrder.this);
        alertDialog.setTitle("Update Order");
        alertDialog.setMessage("Please change status");

        LayoutInflater inflater = this.getLayoutInflater();
        final View view = inflater.inflate(R.layout.udateorder,null);

        spinner = (Spinner) view.findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> stateObject = ArrayAdapter.createFromResource(this,R.array.orderState, android.R.layout.simple_spinner_item);
        stateObject.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(stateObject);

        alertDialog.setView(view);



        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                request.setStatus(spinner.getSelectedItem().toString());

                requests.document(request.getRequestID()).set(request);
                recreate();
            }
        });

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.show();
    }

    private void deleteOrder(Request request){
        db.collection("Request").document(request.getRequestID()).delete();
        recreate();
    }
}


