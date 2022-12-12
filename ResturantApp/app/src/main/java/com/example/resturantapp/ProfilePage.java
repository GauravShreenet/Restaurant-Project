package com.example.resturantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.resturantapp.databinding.ActivityEntreeBinding;
import com.example.resturantapp.databinding.ActivityProfilePageBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfilePage extends DrawerBaseActivity {

    private Button btnLogout;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    ActivityProfilePageBinding activityProfilePageBinding;
    //private TextView firstName, lastName, email, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityProfilePageBinding = ActivityProfilePageBinding.inflate(getLayoutInflater());
        setContentView(activityProfilePageBinding.getRoot());
        allocateActivityTitle("Profile Detail");

        btnLogout = (Button) findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ProfilePage.this, MainActivity.class));
                finish();
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance("https://resturant-app-93f08-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("CustomerUser");
        userID = user.getUid();

        final TextView firstNameText = (TextView) findViewById(R.id.firstNameFinal);
        final TextView lastNameText = (TextView) findViewById(R.id.lastNameFinal);
        final TextView emailAddText = (TextView) findViewById(R.id.emailFinal);
        final TextView phoneNoText = (TextView) findViewById(R.id.phoneFinal);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                CustomerUser userProfile = snapshot.getValue(CustomerUser.class);

                if (userProfile != null){
                    String firstName = userProfile.firstName;
                    String lastName = userProfile.lastName;
                    String email = userProfile.email;
                    String phone = userProfile.phone;

                    firstNameText.setText(firstName);
                    lastNameText.setText(lastName);
                    emailAddText.setText(email);
                    phoneNoText.setText(phone);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfilePage.this, "Something wrong happened!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}