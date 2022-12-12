package com.example.resturantapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.resturantapp.R;
import com.example.resturantapp.models.MyCartModel;
import com.example.resturantapp.models.Request;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderHolder> {

    Context context;
    List<Request> list;
    List<MyCartModel> mList = new ArrayList<>();

    CollectionReference collectionReference;

    public OrderAdapter(Context context, List<Request> list) {
        this.context = context;
        this.list = list;

        collectionReference = FirebaseFirestore.getInstance().collection("Request");
    }

    @NonNull
    @Override
    public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.orderlist, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.OrderHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.orderNumber.setText(list.get(position).getTotalOrderPrice());
        holder.status.setText((list.get(position).getStatus()));

        boolean isExpandable = list.get(position).isExpandable();
        holder.expandableLayout.setVisibility(isExpandable ? View.VISIBLE : View.GONE);

        DetailOrderAdapter detailOrderAdapter = new DetailOrderAdapter(mList);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        holder.recyclerView.setAdapter(detailOrderAdapter);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.get(position).setExpandable(!list.get(position).isExpandable());
                mList = list.get(position).getFoods();
                notifyItemChanged(holder.getAdapterPosition());
            }
        });

    }




    @Override
    public int getItemCount() {
        return list.size();
    }

    public class OrderHolder extends RecyclerView.ViewHolder {

        TextView orderNumber, status;
        RelativeLayout expandableLayout;
        LinearLayout linearLayout;

        RecyclerView recyclerView;


        public OrderHolder(@NonNull View itemView) {
            super(itemView);

            orderNumber = itemView.findViewById(R.id.orderNumber);
            status = itemView.findViewById(R.id.status);
            linearLayout = itemView.findViewById(R.id.linear_layout);
            expandableLayout = itemView.findViewById(R.id.expandable_layout);
            recyclerView = itemView.findViewById(R.id.orderDetail);
        }
    }
}
