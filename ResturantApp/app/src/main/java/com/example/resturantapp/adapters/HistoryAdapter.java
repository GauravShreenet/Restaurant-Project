package com.example.resturantapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.resturantapp.R;
import com.example.resturantapp.models.HistoryModel;
import com.example.resturantapp.models.MyCartModel;
import com.example.resturantapp.models.Request;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryHolder> {

    Context context;
    List<Request> list;
    List<MyCartModel> mList = new ArrayList<>();

    CollectionReference collectionReference;

    public HistoryAdapter(Context context, List<Request> list) {
        this.context = context;
        this.list = list;

    }


    @NonNull
    @Override
    public HistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HistoryHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.history_list, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.HistoryHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.textTotalPrice.setText(list.get(position).getTotalOrderPrice());

        boolean isExpandable = list.get(position).isExpandable();
        holder.expandLayout.setVisibility(isExpandable ? View.VISIBLE : View.GONE);

        DetailOrderAdapter detailOrderAdapter = new DetailOrderAdapter(mList);
        holder.historyDetail.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        holder.historyDetail.setAdapter(detailOrderAdapter);
        holder.linLayout.setOnClickListener(new View.OnClickListener() {
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

    public class HistoryHolder extends RecyclerView.ViewHolder {

        TextView textTotalPrice;
        RelativeLayout expandLayout;
        LinearLayout linLayout;

        RecyclerView historyDetail;

        public HistoryHolder(@NonNull View itemView) {
            super(itemView);


            textTotalPrice = itemView.findViewById(R.id.textTotalPrice);
            linLayout = itemView.findViewById(R.id.lin_layout);
            expandLayout = itemView.findViewById(R.id.expand_layout);
            historyDetail = itemView.findViewById(R.id.historyDetail);
        }
    }
}
