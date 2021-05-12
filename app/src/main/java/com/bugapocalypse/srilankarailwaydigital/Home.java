package com.bugapocalypse.srilankarailwaydigital;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;


import com.bugapocalypse.srilankarailwaydigital.Components.PopupDialog;

public class Home extends Fragment {
    
    TextView txtFrom;
    TextView txtTo;
    TextView txtDate;
    Button btnFind;
    DatabaseReference dbref;
    HomeFindbtn hm;
    
    private void clearData(){
        txtFrom.setText("");
        txtTo.setText("");
        txtDate.setText("");
    }


    Button btnTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        
        //return inflater.inflate(R.layout.fragment_home, container, false);
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        txtFrom = (TextView)view.findViewById(R.id.txtfrom);
        txtTo = (TextView)view.findViewById(R.id.txtto);
        txtDate = (TextView)view.findViewById(R.id.txtdate);

        Button btnfind = view.findViewById(R.id.btnfind);

        hm = new HomeFindbtn();

        btnfind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.nav_controller_view,new TimeSchedule());
                fr.commit();
            }
        });

       

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


        btnTitle = view.findViewById(R.id.btnTitle);
        btnTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupDialog popupDialog = new PopupDialog();
                popupDialog.show(getParentFragmentManager(), "PopupDialog");
            }
        });

        return view;
    }

}