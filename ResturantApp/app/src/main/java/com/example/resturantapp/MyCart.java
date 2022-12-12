package com.example.resturantapp;



import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.DialogInterface;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;

import android.widget.TextView;
import android.widget.Toast;

import com.example.resturantapp.adapters.MyCartAdapter;
import com.example.resturantapp.models.MyCartModel;
import com.example.resturantapp.models.Request;
import com.example.resturantapp.models.RequestAdd;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.List;

public class MyCart extends AppCompatActivity {

    FirebaseFirestore db;
    FirebaseAuth auth;
    TextView overTotalAmount;
    RecyclerView recyclerMyCart;
    MyCartAdapter cartAdapter;
    List<MyCartModel> cartModelList;
    Button checkOut;
    ProgressBar progressBar;
    ImageView emptyCart;
    ConstraintLayout constraintLayout, textLayout;
    String totalOrderPrice;
    RadioButton radioPickup, radioDelivery;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    CollectionReference requests;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        requests = db.collection("Request");

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance("https://resturant-app-93f08-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("CustomerUser");
        userID = user.getUid();


        constraintLayout = findViewById(R.id.progressBarLayout);
        textLayout = findViewById(R.id.textLayout);
        textLayout.setVisibility(View.GONE);
        progressBar = findViewById(R.id.progressBar4);
        progressBar.setVisibility(View.VISIBLE);

        emptyCart = findViewById(R.id.imageView6);
        emptyCart.setVisibility(View.VISIBLE);

        radioPickup = findViewById(R.id.radioPickup);
        radioDelivery = findViewById(R.id.radioDelivery);

        checkOut = findViewById(R.id.checkOut);
        checkOut.setVisibility(View.GONE);



        recyclerMyCart = findViewById(R.id.fireStore_MyCart);
        recyclerMyCart.setVisibility(View.GONE);
        recyclerMyCart.setLayoutManager(new LinearLayoutManager(this));

        overTotalAmount = findViewById(R.id.textView24);

        cartModelList = new ArrayList<>();
        cartAdapter = new MyCartAdapter(this, cartModelList);
        recyclerMyCart.setAdapter(cartAdapter);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                CustomerUser userProfile = snapshot.getValue(CustomerUser.class);

                if (userProfile != null) {
                    String userID = user.getUid();
                    String firstName = userProfile.firstName;
                    String lastName = userProfile.lastName;
                    String email = userProfile.email;
                    String phone = userProfile.phone;

                }

                db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                        .collection("AddToCart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {

                                        String documentID = documentSnapshot.getId();

                                        MyCartModel cartModel = documentSnapshot.toObject(MyCartModel.class);

                                        cartModel.setDocumentId(documentID);


                                        cartModelList.add(cartModel);
                                        calculateTotalAmount(cartModelList);
                                        cartAdapter.notifyDataSetChanged();

                                        emptyCart.setVisibility(View.GONE);

                                        recyclerMyCart.setVisibility(View.VISIBLE);
                                        checkOut.setVisibility(View.VISIBLE);
                                        textLayout.setVisibility(View.VISIBLE);
                                    }

                                    constraintLayout.setVisibility(View.GONE);


                                }
                            }
                        });
                checkOut.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (radioPickup.isChecked()) {

                            Request request = new Request(
                                    userProfile.firstName,
                                    userProfile.phone,
                                    totalOrderPrice,
                                    cartModelList

                            );

                            requests.document(auth.getCurrentUser().getUid()).set(request);
                            removeCart();

                            Toast.makeText(MyCart.this, "Thank you, Order Place", Toast.LENGTH_SHORT).show();
                            finish();

                        } else if (radioDelivery.isChecked()) {
                            showAlertDialog();
                        }



                    }



                    private void showAlertDialog() {
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MyCart.this);
                        alertDialog.setTitle("For Delivery");
                        alertDialog.setMessage("Enter your address: ");

                        final EditText editAddress = new EditText(MyCart.this);
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.MATCH_PARENT
                        );
                        editAddress.setLayoutParams(lp);
                        alertDialog.setView(editAddress);
                        alertDialog.setIcon(R.drawable.ic_baseline_add_shopping_cart_24);

                        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                RequestAdd requestAdd = new RequestAdd(
                                        userProfile.firstName,
                                        userProfile.phone,
                                        editAddress.getText().toString(),
                                        totalOrderPrice,
                                        cartModelList

                                );

                                String order_number = auth.getCurrentUser().getUid();
                                requests.document(order_number).set(requestAdd);
                                removeCart();




                                Toast.makeText(MyCart.this, "Thank you, Order Place", Toast.LENGTH_SHORT).show();
                                finish();
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


                });

            }




            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MyCart.this, "Something wrong happened!", Toast.LENGTH_SHORT).show();
            }

        });
    }




    private void calculateTotalAmount(List<MyCartModel> cartModelList) {
        double totalAmount = 0.0;
        for (MyCartModel myCartModel : cartModelList) {
            totalAmount += myCartModel.getTotalPrice();
        }

        overTotalAmount.setText("$" + totalAmount);
        totalOrderPrice = "$" + String.valueOf(totalAmount);
    }

    private void removeCart() {
        db.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                .collection("AddToCart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot snapshot1 : task.getResult()){
                            db.collection("CurrentUser").document(auth.getCurrentUser().getUid()).collection("AddToCart").document(snapshot1.getId()).delete();
                        }
                    }
                });
    }


}