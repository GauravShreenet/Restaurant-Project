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
import android.widget.Spinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.project.resturantserver.Common.Common;
import com.project.resturantserver.adapters.BookingAdapter;
import com.project.resturantserver.adapters.OrderAdapter;
import com.project.resturantserver.models.BookingModel;
import com.project.resturantserver.models.Request;

import java.util.ArrayList;
import java.util.List;

public class BookingConfirm extends AppCompatActivity {

    FirebaseFirestore db;
    FirebaseAuth firebaseAuth;
    CollectionReference bookings;

    RecyclerView recyclerView;
    BookingAdapter bookingAdapter;
    List<BookingModel> bookingModelList;

    Spinner spiner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_confirm);

        db = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        bookings = db.collection("Booking");

        recyclerView = findViewById(R.id.recycleBooking);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bookingModelList = new ArrayList<>();
        bookingAdapter = new BookingAdapter(this, bookingModelList);

        recyclerView.setAdapter(bookingAdapter);

        db.collection("Booking").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        String documentID = documentSnapshot.getId();

                        BookingModel bookingModel = documentSnapshot.toObject(BookingModel.class);
                        bookingModel.setDocumentID(documentID);
                        bookingModelList.add(bookingModel);
                        bookingAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
    }


    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        if (item.getTitle().equals(Common.UPDATE)){
            showUpdateDialog(bookingModelList.get(item.getOrder()));
        }
        else if (item.getTitle().equals(Common.DELETE)){
            deleteOrder(bookingModelList.get(item.getOrder()));
        }

        return super.onContextItemSelected(item);
    }

    private void deleteOrder(BookingModel bookingModel) {
        db.collection("Booking").document(bookingModel.getDocumentID()).delete();
        recreate();
    }

    private void showUpdateDialog(BookingModel bookingModel) {
        final AlertDialog.Builder alertDialog =  new AlertDialog.Builder(BookingConfirm.this);
        alertDialog.setTitle("Update Order");
        alertDialog.setMessage("Please change status");

        LayoutInflater inflater = this.getLayoutInflater();
        final View view = inflater.inflate(R.layout.updatebooking,null);

        spiner = (Spinner) view.findViewById(R.id.spiner);

        ArrayAdapter<CharSequence> stateObj = ArrayAdapter.createFromResource(this,R.array.bookingState, android.R.layout.simple_spinner_item);
        stateObj.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiner.setAdapter(stateObj);

        alertDialog.setView(view);

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                bookingModel.setStatus(spiner.getSelectedItem().toString());

                bookings.document(bookingModel.getDocumentID()).set(bookingModel);
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


}