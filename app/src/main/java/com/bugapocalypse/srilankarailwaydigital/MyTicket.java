package com.bugapocalypse.srilankarailwaydigital;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class MyTicket extends Fragment {

    Button btnBookNow;
    LinearLayout ticket;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_ticket, container, false);

        btnBookNow = view.findViewById(R.id.btnCancel);
        btnBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TicketBooking tb = new TicketBooking();
                FragmentTransaction tra = getFragmentManager().beginTransaction();
                tra.replace(R.id.frag_ticket_default, tb);
                tra.commit();
            }
        });

        ticket = view.findViewById(R.id.btnTicket);
        ticket.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DigitalTicket.class);
                startActivity(intent);

            }
        });

        return view;
    }

}