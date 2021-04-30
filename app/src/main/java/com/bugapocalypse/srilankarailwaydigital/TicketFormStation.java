package com.bugapocalypse.srilankarailwaydigital;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bugapocalypse.srilankarailwaydigital.data.Ticket;

public class TicketFormStation extends Fragment {

    Button cancel, btnBook;
    TextView txtFrom, txtTo;
    public Ticket T;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ticket_form_station, container, false);

        txtFrom = view.findViewById(R.id.txtFrom);
        txtTo = view.findViewById(R.id.txtTo);

//        Bundle args = this.getArguments();
//        Ticket t = (Ticket) args.getSerializable("key");
//        txtFrom.setText(t.getFrom());
//        txtTo.setText(t.getTo());

        cancel = view.findViewById(R.id.btnCancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyTicket t = new MyTicket();
                FragmentTransaction tra = getFragmentManager().beginTransaction();
                tra.replace(R.id.frag_ticket_default, t);
                tra.commit();
            }
        });

        btnBook = view.findViewById(R.id.btnBook);
        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyTicket t = new MyTicket();
                FragmentTransaction tra = getFragmentManager().beginTransaction();
                tra.replace(R.id.frag_ticket_default, t);
                tra.commit();
            }
        });
        return view;
    }
}