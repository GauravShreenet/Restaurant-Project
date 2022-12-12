package com.example.resturantapp.adapters;

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

public class HistoryDetailAdapter extends RecyclerView.Adapter<HistoryDetailAdapter.HistoryDetailHolder> {

    List<MyCartModel> cartModList;

    public HistoryDetailAdapter(List<MyCartModel> cartModList) {
        this.cartModList = cartModList;
    }

    @NonNull
    @Override
    public HistoryDetailAdapter.HistoryDetailHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HistoryDetailHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.history_detail, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull HistoryDetailAdapter.HistoryDetailHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return cartModList.size();
    }

    public class HistoryDetailHolder extends RecyclerView.ViewHolder {

        TextView textName1, textPrice1, textQuantity1;

        public HistoryDetailHolder(@NonNull View itemView) {
            super(itemView);

            textName1 = itemView.findViewById(R.id.textName1);
            textPrice1 = itemView.findViewById(R.id.textPrice1);
            textQuantity1 = itemView.findViewById(R.id.textQuantity1);
        }
    }
}
