package com.project.resturantserver.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.project.resturantserver.R;
import com.project.resturantserver.models.MyCartModel;

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

        holder.textTime.setText(cartList.get(position).getCurrentTime());
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

        TextView textDate, textName, textPrice, textQuantity, textTime;

        public DetailOrderHolder(@NonNull View itemView) {
            super(itemView);

            textTime = itemView.findViewById(R.id.textTime);
            textDate = itemView.findViewById(R.id.textDate);
            textName = itemView.findViewById(R.id.textName);
            textPrice = itemView.findViewById(R.id.textPrice);
            textQuantity = itemView.findViewById(R.id.textQuantity);
        }
    }
}
