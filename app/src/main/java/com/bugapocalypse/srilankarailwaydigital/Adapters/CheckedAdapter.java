package com.bugapocalypse.srilankarailwaydigital.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bugapocalypse.srilankarailwaydigital.R;
import com.bugapocalypse.srilankarailwaydigital.data.Cheking;

import java.util.List;

public class CheckedAdapter extends RecyclerView.Adapter<CheckedAdapter.myCheckedHolder> {

    private List<Cheking> chekings;

    public CheckedAdapter(List<Cheking> chekings) {
        this.chekings = chekings;
    }

    @NonNull
    @Override
    public CheckedAdapter.myCheckedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.checked_cart,parent,false);
        return new CheckedAdapter.myCheckedHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  CheckedAdapter.myCheckedHolder holder, int position) {
            Cheking cheking = chekings.get(position);
            holder.dateAndTime.setText(cheking.getTime());
            holder.train.setText(cheking.getTrain());
            holder.ticketId.setText(cheking.getTicketIdcheak());

    }

    @Override
    public int getItemCount() {
        return chekings.size();
    }

    public class myCheckedHolder extends RecyclerView.ViewHolder {
        TextView dateAndTime,train,ticketId;
        public myCheckedHolder(@NonNull  View itemView) {
            super(itemView);
                dateAndTime = itemView.findViewById(R.id.dateAndTimeCheck);
                train = itemView.findViewById(R.id.trainNameCheck);
                ticketId = itemView.findViewById(R.id.ticketIdCheck );

        }
    }
}
