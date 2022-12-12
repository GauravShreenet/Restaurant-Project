package com.project.resturantserver.adapters;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.project.resturantserver.Common.Common;
import com.project.resturantserver.R;
import com.project.resturantserver.models.BookingModel;


import java.util.List;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingHolder> {

    Context context;
    List<BookingModel> list;
    CollectionReference collectionReference;

    public BookingAdapter(Context context, List<BookingModel> list) {
        this.context = context;
        this.list = list;
        collectionReference = FirebaseFirestore.getInstance().collection("Booking");
    }

    @NonNull
    @Override
    public BookingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BookingHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.booking_list, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull BookingAdapter.BookingHolder holder, int position) {
        holder.customerNo.setText(list.get(position).getPhone());
        holder.bookingStatus.setText(list.get(position).getStatus());
        holder.customerName.setText(list.get(position).getName());
        holder.numberPeople.setText(list.get(position).getNumberOfPeople());
        holder.bookingDate.setText(list.get(position).getDate());
        holder.bookingTime.setText(list.get(position).getTime());


    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class BookingHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        TextView customerNo, bookingStatus, customerName, numberPeople, bookingDate, bookingTime;

        public BookingHolder(@NonNull View itemView) {
            super(itemView);

            customerNo = itemView.findViewById(R.id.customerNo);
            bookingStatus = itemView.findViewById(R.id.BookingStatus);
            customerName = itemView.findViewById(R.id.customerName);
            numberPeople = itemView.findViewById(R.id.numberPeople);
            bookingDate = itemView.findViewById(R.id.bookingDate);
            bookingTime = itemView.findViewById(R.id.bookingTime);

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
