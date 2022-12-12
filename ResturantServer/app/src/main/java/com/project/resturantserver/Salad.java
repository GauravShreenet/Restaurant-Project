package com.project.resturantserver;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.project.resturantserver.Common.Common;
import com.project.resturantserver.adapters.MenuAdapters;
import com.project.resturantserver.models.EntreeModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Salad extends AppCompatActivity {
    private RecyclerView mFirestoreSalad;
    private FirebaseFirestore firebaseFirestore;
    MenuAdapters menuAdapters;
    List<EntreeModel> menuModelList;

    FirebaseStorage storage;
    StorageReference storageReference;

    EditText editName, editPrice;
    Button btnUpload, btnSelect;

    Uri saveUri;
    private final int PICK_IMAGE_REQUEST = 71;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salad);

        firebaseFirestore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        mFirestoreSalad = findViewById(R.id.fireStore_Salad);
        mFirestoreSalad.setLayoutManager(new LinearLayoutManager(this));

        menuModelList = new ArrayList<>();
        menuAdapters = new MenuAdapters(this, menuModelList);

        mFirestoreSalad.setAdapter(menuAdapters);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addFood);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddFood();
            }
        });

        EventChangeListner();

    }

    private void EventChangeListner() {

        firebaseFirestore.collection("Salads").orderBy("name", Query.Direction.ASCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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

    private void showAddFood() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Salad.this);
        alertDialog.setTitle("Add new Food");
        alertDialog.setMessage("Please fill full information");

        LayoutInflater inflater = this.getLayoutInflater();
        View add_menu_layout = inflater.inflate(R.layout.add_new_menu_layout,null);

        editName = add_menu_layout.findViewById(R.id.foodName);
        editPrice = add_menu_layout.findViewById(R.id.foodPrice);
        btnSelect = add_menu_layout.findViewById(R.id.btnSelect);
        btnUpload = add_menu_layout.findViewById(R.id.btnUpload);

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        alertDialog.setView(add_menu_layout);
        alertDialog.setIcon(R.drawable.ic_cart);

        alertDialog.show();

    }

    private void uploadImage() {
        if (saveUri != null){
            ProgressDialog mDialog = new ProgressDialog(this);
            mDialog.setMessage("Uploading....");
            mDialog.show();
            String imageName = UUID.randomUUID().toString();
            StorageReference imageFolder = storageReference.child("images/" + imageName);
            imageFolder.putFile(saveUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(Salad.this, "Uploaded !!!", Toast.LENGTH_SHORT).show();
                    imageFolder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            final HashMap<String, Object> foodMap = new HashMap<>();

                            foodMap.put("name", editName.getText().toString());
                            foodMap.put("price", Integer.parseInt(editPrice.getText().toString()));
                            foodMap.put("url", uri.toString());

                            firebaseFirestore.collection("Salads").add(foodMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentReference> task) {
                                    Toast.makeText(Salad.this, "New Food Item is added", Toast.LENGTH_SHORT).show();
                                    recreate();
                                }
                            });
                        }
                    });
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
            saveUri = data.getData();
            btnSelect.setText("Image Selected!");

        }
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        if (item.getTitle().equals(Common.UPDATE)){
            showUpdateDialog(menuModelList.get(item.getOrder()));
        }
        else if (item.getTitle().equals(Common.DELETE)){
            deleteFoodItem(menuModelList.get(item.getOrder()));
        }

        return super.onContextItemSelected(item);
    }

    private void deleteFoodItem(EntreeModel entreeModel) {
        firebaseFirestore.collection("Salads").document(entreeModel.getProductId())
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Salad.this, "Food Item Deleted", Toast.LENGTH_SHORT).show();
                            recreate();
                        }
                    }
                });
    }

    private void showUpdateDialog(EntreeModel entreeModel) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Salad.this);
        alertDialog.setTitle("Edit Food Item");
        alertDialog.setMessage("Please fill full information");

        LayoutInflater inflater = this.getLayoutInflater();
        View add_menu_layout = inflater.inflate(R.layout.add_new_menu_layout,null);

        editName = add_menu_layout.findViewById(R.id.foodName);
        editPrice = add_menu_layout.findViewById(R.id.foodPrice);
        btnSelect = add_menu_layout.findViewById(R.id.btnSelect);
        btnUpload = add_menu_layout.findViewById(R.id.btnUpload);

        editName.setText(entreeModel.getName());
        editPrice.setText(String.valueOf(entreeModel.getPrice()));

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeImage(entreeModel);
            }
        });

        alertDialog.setView(add_menu_layout);
        alertDialog.setIcon(R.drawable.ic_cart);

        alertDialog.show();

    }

    private void changeImage(EntreeModel entreeModel) {
        if (saveUri != null){
            ProgressDialog mDialog = new ProgressDialog(this);
            mDialog.setMessage("Uploading....");
            mDialog.show();
            String imageName = UUID.randomUUID().toString();
            StorageReference imageFolder = storageReference.child("images/" + imageName);
            imageFolder.putFile(saveUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(Salad.this, "Uploaded !!!", Toast.LENGTH_SHORT).show();
                    imageFolder.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            final HashMap<String, Object> foodMap = new HashMap<>();

                            foodMap.put("name", editName.getText().toString());
                            foodMap.put("price", Double.parseDouble(String.valueOf(editPrice.getText())));
                            foodMap.put("url", uri.toString());

                            firebaseFirestore.collection("Salads").document(entreeModel.getProductId()).set(foodMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(Salad.this, "Food Item is Updated", Toast.LENGTH_SHORT).show();
                                    recreate();
                                }
                            });
                        }
                    });
                }
            });
        }else if(saveUri == null){
            final HashMap<String, Object> foodMap = new HashMap<>();

            foodMap.put("name", editName.getText().toString());
            foodMap.put("price", Double.parseDouble(String.valueOf(editPrice.getText())));

            firebaseFirestore.collection("Salads").document(entreeModel.getProductId()).update(foodMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(Salad.this, "Food Item is Updated", Toast.LENGTH_SHORT).show();
                    recreate();
                }
            });
        }
    }


}