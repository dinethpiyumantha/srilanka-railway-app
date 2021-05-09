package com.bugapocalypse.srilankarailwaydigital;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.bugapocalypse.srilankarailwaydigital.Adapters.CheckedAdapter;
import com.bugapocalypse.srilankarailwaydigital.data.Cheking;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class CheckedTickets extends Fragment {

    FirebaseFirestore firebaseFirestore;
    CheckedAdapter chekedAdapter;
    ArrayList<Cheking> list1 = new ArrayList<>();
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_checked_tickets, container, false);

        //recycle id
        recyclerView = view.findViewById(R.id.recycleView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //chekedAdapter = new CheckedAdapter(getCont)
        chekedAdapter = new CheckedAdapter(getContext(), list1,this);
        recyclerView.setAdapter(chekedAdapter);
        Log.d("CHEK","Line 48");

        firebaseFirestore.getInstance().collection("checking").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                //Log.d("CHEK","set Array list to data line 52");

                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        //Log.d("CHEK","set Array list to data line 56");

                        String documentId = document.getId();
                        Cheking cheking = new Cheking();
                        cheking.setDocumentId(documentId);
                        cheking.setTime(document.getData().get("time").toString());
                        cheking.setTrain(document.getData().get("train").toString());
                        cheking.setTicketIdcheak(document.getData().get("ticketIdcheak").toString());
                        cheking.setCheacked(document.getData().get("cheacked").toString());
                        Log.d("CHEK","set Array list to data line 56"+document.getData().get("cheacked").toString());

                        if(cheking.getCheacked().equals("1")) {
                            Log.d("CHEK","set Array list to data line 63"+">>"+document.getData().get("train").toString());
                            list1.add(cheking);
                        }
                    }
                    chekedAdapter.notifyDataSetChanged();

                } else {
                    Log.d("TAeG", "Error getting documents: ", task.getException());
                }
                }


        });



        return view;
    }
}