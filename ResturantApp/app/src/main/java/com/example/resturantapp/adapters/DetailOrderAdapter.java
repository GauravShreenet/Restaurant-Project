package com.example.resturantapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.resturantapp.R;
import com.example.resturantapp.models.MyCartModel;

import java.util.List;

public class DetailOrderAdapter extends RecyclerView.Adapter<DetailOrderAdapter.DetailOrderHolder>{

    List<MyCartModel> cartList;

    public DetailOrderAdapter(List<MyCartModel> cartList) {
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public DetailOrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DetailOrderHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_order, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull DetailOrderAdapter.DetailOrderHolder holder, int position) {
        holder.textDate.setText(cartList.get(position).getCurrentDate());
        holder.textName.setText(cartList.get(position).getProductName());
        holder.textPrice.setText("" + cartList.get(position).getTotalPrice());
        holder.textQuantity.setText("" + cartList.get(position).getTotalQuantity());

    }


    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class DetailOrderHolder extends RecyclerView.ViewHolder {

        TextView textDate, textName, textPrice, textQuantity;

        public DetailOrderHolder(@NonNull View itemView) {
            super(itemView);

            textDate = itemView.findViewById(R.id.textDate);
            textName = itemView.findViewById(R.id.textName);
            textPrice = itemView.findViewById(R.id.textPrice);
            textQuantity = itemView.findViewById(R.id.textQuantity);
        }
    }
}
