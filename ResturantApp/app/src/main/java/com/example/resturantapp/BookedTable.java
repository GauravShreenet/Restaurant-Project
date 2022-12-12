package com.example.resturantapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.resturantapp.databinding.ActivityBeerBinding;
import com.example.resturantapp.databinding.ActivityBookedTableBinding;
import com.example.resturantapp.models.BookingModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class BookedTable extends DrawerBaseActivity {

    ActivityBookedTableBinding activityBookedTableBinding;

    FirebaseFirestore db;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBookedTableBinding = ActivityBookedTableBinding.inflate(getLayoutInflater());
        setContentView(activityBookedTableBinding.getRoot());
        allocateActivityTitle("Booked Table");

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        final TextView textPeople = findViewById(R.id.textPeople);
        final TextView textDateCon = findViewById(R.id.textDateCon);
        final TextView textTimeCon = findViewById(R.id.textTimeCon);

        db.collection("Booking").document(auth.getCurrentUser().getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                BookingModel booking = value.toObject(BookingModel.class);

                if (booking != null){
                    textPeople.setText(booking.getNumberOfPeople());
                    textDateCon.setText(booking.getDate());
                    textTimeCon.setText(booking.getTime());
                }else{
                    Toast.makeText(BookedTable.this, "You haven't make any booking", Toast.LENGTH_SHORT).show();
                }



            }




        });

    }
}