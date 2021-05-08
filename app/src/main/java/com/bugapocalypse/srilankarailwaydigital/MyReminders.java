package com.bugapocalypse.srilankarailwaydigital;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MyReminders extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_my_reminders, container, false);

        View view = inflater.inflate(R.layout.fragment_my_reminders,container,false );
        Button btnSetReminder = (Button)view.findViewById(R.id.btnSetReminder);

        btnSetReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment, new ReminderForm());
                fr.commit();
            }
        });
        return view;
    }
}

