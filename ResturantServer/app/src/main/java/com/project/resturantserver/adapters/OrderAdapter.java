package com.project.resturantserver.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.project.resturantserver.Common.Common;
import com.project.resturantserver.R;
import com.project.resturantserver.models.MyCartModel;
import com.project.resturantserver.models.Request;

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
        return new OrderHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.orderlists, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.OrderHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.orderNumber.setText(list.get(position).getPhone());
        holder.status.setText((list.get(position).getStatus()));

        boolean isExpandable = list.get(position).isExpandable();
        holder.expandableLayout.setVisibility(isExpandable ? View.VISIBLE : View.GONE);

        DetailOrderAdapter detailOrderAdapter = new DetailOrderAdapter(mList);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        holder.recyclerView.setAdapter(detailOrderAdapter);

        holder.downArrow.setOnClickListener(new View.OnClickListener() {
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

    public class OrderHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{

        TextView orderNumber, status;

        LinearLayout linearLayout;
        RelativeLayout expandableLayout;
        RecyclerView recyclerView;
        ImageView downArrow;

        public OrderHolder(@NonNull View itemView) {
            super(itemView);

            orderNumber = itemView.findViewById(R.id.orderNumber);
            status = itemView.findViewById(R.id.status);
            linearLayout = itemView.findViewById(R.id.linear_layout);

            recyclerView = itemView.findViewById(R.id.orderDetail);
            expandableLayout = itemView.findViewById(R.id.expandable_layout);
            downArrow = itemView.findViewById(R.id.downArrow);

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
