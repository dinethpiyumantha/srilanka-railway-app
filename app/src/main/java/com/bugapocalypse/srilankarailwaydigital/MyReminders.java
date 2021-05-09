package com.bugapocalypse.srilankarailwaydigital;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.bugapocalypse.srilankarailwaydigital.Adapters.RemindersAdapter;
import com.bugapocalypse.srilankarailwaydigital.Adapters.TicketAdapter;
import com.bugapocalypse.srilankarailwaydigital.data.Shedule;
import com.bugapocalypse.srilankarailwaydigital.data.Ticket;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MyReminders extends Fragment implements RemindersAdapter.OnTimeListner {

   /* @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_my_reminders, container, false);

        View view = inflater.inflate(R.layout.fragment_my_reminders,container,false );
        Button btnSetReminder = (Button)view.findViewById(R.id.btnEdit);

        btnSetReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.fragment, new ReminderForm());
                fr.commit();
            }
        });
        return view;*/

    public static final String TAG = "MYREMINDER"; // Log TAG

    // Declaration Items
    Button setReminder;
    LinearLayout ticket;
    RecyclerView recyclerView;
    RemindersAdapter remindersAdapter;

    // DATABASE instances
    DatabaseReference dbRef; //Firebase Realtime DB
    FirebaseFirestore firestore; //Firebase Firestore
    FirebaseUser firebaseUser;

    // Ticket List declaration
    ArrayList<Shedule> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_reminders, container, false);


        // Initializing Items
        recyclerView = view.findViewById(R.id.my_tickets_list);
        setReminder = view.findViewById(R.id.btnSet);


        dbRef = FirebaseDatabase.getInstance().getReference("Ticket"); //Firestore getting instance by document
        firestore = FirebaseFirestore.getInstance(); //Realtime DB instance initialization
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        // Set Reminders to recycler view
        list = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        remindersAdapter = new RemindersAdapter(getContext(), list, this);
        recyclerView.setAdapter(remindersAdapter);

        // Getting Reminders Documents from fire store and add to the list
        firestore.collection("Shedules")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " <<< " + document.getData().getClass() + " >>> => " + document.getData());
                                Shedule s = new Shedule();
                                s.setId(document.getId());
                                s.setTime(document.getData().get("time").toString());
                                s.setDate(document.getData().get("date").toString());
                                s.setFrom(document.getData().get("from").toString());
                                s.setTo(document.getData().get("to").toString());

                                    list.add(s);
                            }
                            remindersAdapter.notifyDataSetChanged();
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
        // Getting Reminders from realtime db and add to the list
//        dbRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                    Ticket ticket = dataSnapshot.getValue(Ticket.class);
//                    if(ticket.getApprove().equals("1")) {
//                        list.add(ticket);
//                    }
//                    Log.d(TAG, "ticket : " + ticket.getFrom());
//                }
//                ticketAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

        // After perform on click on book button (Listener)
//        btnBookNow = view.findViewById(R.id.btnBook);
        setReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReminderEdit rf = new ReminderEdit();
                FragmentTransaction tra = getFragmentManager().beginTransaction();
                tra.replace(R.id.fragment, rf);
                tra.commit();
            }
        });
        return view;
    }

    // When clicked on specific item
    // --> Open a new activity with parceled object as parameter

    @Override
    public void onTimeClick(int position, Shedule shedule) {

    }
}

