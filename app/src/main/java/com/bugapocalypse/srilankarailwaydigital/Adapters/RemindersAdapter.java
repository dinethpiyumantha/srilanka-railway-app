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

import com.bugapocalypse.srilankarailwaydigital.R;
import com.bugapocalypse.srilankarailwaydigital.data.Shedule;
import com.bugapocalypse.srilankarailwaydigital.data.Ticket;

import java.util.ArrayList;

public class RemindersAdapter extends RecyclerView.Adapter<RemindersAdapter.ReminderViewHolder>{

    Context context;
    static ArrayList<Shedule> list;
    private OnTimeListner monTimeListner;

    public RemindersAdapter(Context context, ArrayList<Shedule> list, OnTimeListner onTimeListener) {
            this.context = context;
            this.list = list;
            this.monTimeListner = onTimeListener;
    }

    @NonNull
    @Override
    public ReminderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(context).inflate(R.layout.ticket_item, parent, false);

            v.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
            Log.i("SAMPLE", "===========>>>  CLICKED !!!!!!!!");
            }
            });
            return new ReminderViewHolder(v, monTimeListner);
            }

    @Override
    public void onBindViewHolder(@NonNull ReminderViewHolder holder, int position) {
            Shedule shedule = list.get(position);
            holder.time.setText(shedule.getTime());
            holder.date.setText(shedule.getDate());
            holder.from.setText(shedule.getFrom());
            holder.to.setText(shedule.getTo());
            }

    @Override
    public int getItemCount() {
            return list.size();
            }

    public static class ReminderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView time, date, from, to;
        RemindersAdapter.OnTimeListner onTimeListener;


        public ReminderViewHolder(@NonNull View itemView, RemindersAdapter.OnTimeListner onTmeListener) {
            super(itemView);
            time = itemView.findViewById(R.id.time);
            date = itemView.findViewById(R.id.date);
            to = itemView.findViewById(R.id.fromTo);

            this.onTimeListener = onTimeListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onTimeListener.onTimeClick(getAdapterPosition(), list.get(getAdapterPosition()));
        }
    }

    public interface OnTimeListner {
        void onTimeClick(int position, Shedule shedule);
    }

}


