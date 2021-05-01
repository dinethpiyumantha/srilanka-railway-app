package com.bugapocalypse.srilankarailwaydigital;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class Home extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//         Inflate the layout for this fragment
//                Dropdown menu
//        Spinner spin = this.findViewById(R.id.spnmonth);
//        ArrayAdapter adapter = ArrayAdapter.createFromResource(
//                this,
//                R.array.stations,
//                R.layout.custom_spinner_light
//        );
//        adapter.setDropDownViewResource(R.layout.custom_spinner_light_drop);
//        spin.setAdapter(adapter);
//        spin.setOnItemSelectedListener(this);
//

        return inflater.inflate(R.layout.fragment_home, container, false);
    }
}