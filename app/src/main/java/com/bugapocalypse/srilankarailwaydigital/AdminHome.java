package com.bugapocalypse.srilankarailwaydigital;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.bugapocalypse.srilankarailwaydigital.data.Cheking;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AdminHome extends Fragment {

    FirebaseFirestore firebaseFirestore;
    Button button;
    Button btnCheck;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_admin_home, container, false);

        //firbase

        firebaseFirestore = FirebaseFirestore.getInstance();
        //----------------------------
        button = v.findViewById(R.id.button5);
        btnCheck = v.findViewById(R.id.btnCheck);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),before_admin_gif.class);
                startActivity(intent);
            }
        });


        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cheking c = new Cheking();
                c.setId("ak123");
                c.setNote("lsjnfgdslfjldsjl nmflsdjfg");

                DocumentReference documentReference = firebaseFirestore.collection("Ckeking").document("1");
                documentReference.set(c).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getContext(),"success",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        return v;

    }

}