package com.bugapocalypse.srilankarailwaydigital.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bugapocalypse.srilankarailwaydigital.DigitalTicket;
import com.bugapocalypse.srilankarailwaydigital.R;
import com.bugapocalypse.srilankarailwaydigital.data.Ticket;

import java.util.ArrayList;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketViewHolder> {

    Context context;
    static ArrayList<Ticket> list;
    private OnTicketListener mOnTicketListener;

    public TicketAdapter(Context context, ArrayList<Ticket> list, OnTicketListener onTicketListener) {
        this.context = context;
        this.list = list;
        this.mOnTicketListener = onTicketListener;
    }

    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.ticket_item, parent, false);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("SAMPLE", "===========>>>  CLICKED !!!!!!!!");
            }
        });
        return new TicketViewHolder(v, mOnTicketListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketViewHolder holder, int position) {
        Ticket ticket = list.get(position);
        holder.trainName.setText(ticket.getTrain());
        holder.fromTo.setText(ticket.getFrom() + " - " + ticket.getTo());
        holder.date.setText(ticket.getDate());
        holder.time.setText(ticket.getTime());
        holder.price.setText("LKR " + ticket.getPrice());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class TicketViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView trainName, fromTo, date, time, price;
        OnTicketListener onTicketListener;


        public TicketViewHolder(@NonNull View itemView, OnTicketListener onTicketListener) {
            super(itemView);
            trainName = itemView.findViewById(R.id.trainName);
            fromTo = itemView.findViewById(R.id.fromTo);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            price = itemView.findViewById(R.id.price);

            this.onTicketListener = onTicketListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onTicketListener.onTicketClick(getAdapterPosition(), list.get(getAdapterPosition()));
        }
    }

    public interface OnTicketListener {
        void onTicketClick(int position, Ticket ticket);
    }
}
