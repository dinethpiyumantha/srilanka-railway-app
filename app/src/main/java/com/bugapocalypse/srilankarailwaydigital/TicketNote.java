package com.bugapocalypse.srilankarailwaydigital;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TicketNote extends Fragment {
    private TextView id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_ticket_note, container, false);

       id = v.findViewById(R.id.userId);
      String vall= getArguments().getString("id");

       // String idn = getIntent().getStringExtra("id");

        id.setText(vall);

        return v;
    }
}