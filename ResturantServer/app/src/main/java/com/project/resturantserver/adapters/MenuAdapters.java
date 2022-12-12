package com.project.resturantserver.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.project.resturantserver.Common.Common;
import com.project.resturantserver.Entree;
import com.project.resturantserver.R;
import com.project.resturantserver.models.EntreeModel;

import java.util.List;
import java.util.UUID;

public class MenuAdapters extends RecyclerView.Adapter<MenuAdapters.MenuHolder>{


    Context context;
    List<EntreeModel> list;
    FirebaseFirestore fireStore;
    FirebaseAuth auth;

    FirebaseStorage storage;
    StorageReference storageReference;


    public MenuAdapters(Context context, List<EntreeModel> list) {
        this.context = context;
        this.list = list;

        fireStore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

    }

    @NonNull
    @Override
    public MenuHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MenuHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false));
    }


    @Override
    public void onBindViewHolder(@NonNull MenuHolder holder, @SuppressLint("RecyclerView") int position) {

        Glide.with(context).load(list.get(position).getUrl()).circleCrop().into((holder.imageView));
        holder.name.setText(list.get(position).getName());
        holder.price.setText("$" + list.get(position).getPrice());

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MenuHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{

        ImageView imageView;
        TextView name, price;



        public MenuHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.entree_name);
            price = itemView.findViewById(R.id.entree_price);
            imageView = itemView.findViewById(R.id.imageChange);

            itemView.setOnCreateContextMenuListener(this);

        }


        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.setHeaderTitle("Select the action");
            menu.add(0,0, getAdapterPosition(), Common.UPDATE);
            menu.add(0,1, getAdapterPosition(), Common.DELETE);
        }
    }
}