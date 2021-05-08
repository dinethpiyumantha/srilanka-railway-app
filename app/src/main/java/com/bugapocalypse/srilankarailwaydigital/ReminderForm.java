package com.bugapocalypse.srilankarailwaydigital;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ReminderForm extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_reminder_form, container, false);
        View view = inflater.inflate(R.layout.fragment_reminder_form, container, false);

        Button btnedit = (Button)view.findViewById(R.id. btnedit);
        Button btnback2 = (Button)view.findViewById(R.id. btnback2);

        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment, new ReminderEdit());
                fr.commit();
            }
        });

        btnback2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment, new MyReminders());
                fr.commit();
            }
        });
        return view;

    }
}