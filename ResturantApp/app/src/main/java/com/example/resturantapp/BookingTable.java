package com.example.resturantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.resturantapp.models.BookingModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class BookingTable extends AppCompatActivity {

    EditText editNumber, editDate, editTime;
    Button btnBooking;
    FirebaseFirestore db;
    FirebaseAuth auth;

    DatabaseReference reference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_table);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance("https://resturant-app-93f08-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("CustomerUser");


        editNumber = findViewById(R.id.editNumber);
        editDate = findViewById(R.id.editDate);
        editTime = findViewById(R.id.editTime);

        editDate.setInputType(InputType.TYPE_NULL);
        editTime.setInputType(InputType.TYPE_NULL);



        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDate(editDate);
            }
        });

        editTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTime(editTime);
            }
        });



        btnBooking = findViewById(R.id.btnBook);
        btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.child(auth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        CustomerUser userProfile = snapshot.getValue(CustomerUser.class);


                        String people = editNumber.getText().toString();
                        String date = editDate.getText().toString();
                        String time = editTime.getText().toString();

                        BookingModel bookingModel = new BookingModel(
                                userProfile.firstName,
                                userProfile.phone,
                                people,
                                date,
                                time
                        );

                        db.collection("Booking").document(auth.getCurrentUser().getUid()).set(bookingModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(BookingTable.this, "Booking Placed", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    private void showTime(EditText editTime) {
        Calendar calendar = Calendar.getInstance();

        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                editTime.setText(simpleDateFormat.format(calendar.getTime()));

            }
        };

        new TimePickerDialog(BookingTable.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
    }

    private void showDate(EditText editDate) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DATE,dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM dd, yyyy");

                editDate.setText(simpleDateFormat.format(calendar.getTime()));
            }
        };
        new DatePickerDialog(BookingTable.this, dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
}