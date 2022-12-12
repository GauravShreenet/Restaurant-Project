package com.example.resturantapp.adapters;

import static java.lang.String.valueOf;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.resturantapp.Detail;
import com.example.resturantapp.EntreeModel;
import com.example.resturantapp.R;
import com.google.android.gms.common.internal.service.Common;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class MenuAdapters extends RecyclerView.Adapter<MenuAdapters.MenuHolder> {

    Context context;
    List<EntreeModel> list;
    FirebaseFirestore fireStore;
    FirebaseAuth auth;
    int totalQuantity = 1;


    public MenuAdapters(Context context, List<EntreeModel> list) {
        this.context = context;
        this.list = list;

        fireStore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

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
        holder.price.setText("$"+list.get(position).getPrice());

        double overTotalPrice = (list.get(position).getPrice());
        holder.totalPrice = overTotalPrice;
        
        String mProductId = (list.get(position).getProductId());
        holder.productId = mProductId;
        


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Detail.class);
                intent.putExtra("detail", list.get(position));
                context.startActivities(new Intent[]{intent});
            }
        });

        holder.addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.addToCart();

            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MenuHolder extends RecyclerView.ViewHolder {

        public String productId;
        double totalPrice;

        ImageView imageView, addCart;
        TextView name, price, quantity;


        public MenuHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.entree_name);
            price = itemView.findViewById(R.id.entree_price);
            imageView = itemView.findViewById(R.id.imageChange);
            addCart = itemView.findViewById(R.id.add_cart);
            quantity = itemView.findViewById(R.id.quantityObj);

        }

        public void addToCart() {
            String saveCurrentDate, saveCurrentTime;
            Calendar calForDate = Calendar.getInstance();

            SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
            saveCurrentDate = currentDate.format(calForDate.getTime());

            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
            saveCurrentTime = currentTime.format(calForDate.getTime());


            final HashMap<String,Object> cartMap = new HashMap<>();

            cartMap.put("productName", name.getText());
            cartMap.put("productPrice", price.getText().toString());
            cartMap.put("currentDate",saveCurrentDate);
            cartMap.put("currentTime",saveCurrentTime);
            cartMap.put("totalQuantity", Integer.parseInt((String) quantity.getText()));

            cartMap.put("totalPrice", totalPrice);

            
            fireStore.collection("CurrentUser").document(auth.getCurrentUser().getUid())
                    .collection("AddToCart").add(cartMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            Toast.makeText(context,  "Added to the Cart", Toast.LENGTH_SHORT).show();

                        }
                    });
        }
    }



}
