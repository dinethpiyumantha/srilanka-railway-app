package com.bugapocalypse.srilankarailwaydigital;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.bugapocalypse.srilankarailwaydigital.Components.PopupDialog;

public class Home extends Fragment {

    Button btnTitle;

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

        View v = inflater.inflate(R.layout.fragment_home, container, false);

        btnTitle = v.findViewById(R.id.btnTitle);
        btnTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupDialog popupDialog = new PopupDialog();
                popupDialog.show(getParentFragmentManager(), "PopupDialog");
            }
        });

        return v;
    }
}