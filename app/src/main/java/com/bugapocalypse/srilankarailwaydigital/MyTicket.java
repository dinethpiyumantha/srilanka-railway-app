// Dineth
package com.bugapocalypse.srilankarailwaydigital;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bugapocalypse.srilankarailwaydigital.Adapters.TicketAdapter;
import com.bugapocalypse.srilankarailwaydigital.data.Ticket;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MyTicket extends Fragment implements TicketAdapter.OnTicketListener {

    public static final String TAG = "MYTIC"; // Log TAG

    // Declaration Items
    Button btnBookNow;
    LinearLayout ticket;
    RecyclerView recyclerView;
    TicketAdapter ticketAdapter;

    // DATABASE instances
    DatabaseReference dbRef; //Firebase Realtime DB
    FirebaseFirestore firestore; //Firebase Firestore
    FirebaseUser firebaseUser;

    // Ticket List declaration
    ArrayList<Ticket> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_ticket, container, false);


        // Initializing Items
        recyclerView = view.findViewById(R.id.my_tickets_list);
        btnBookNow = view.findViewById(R.id.btnBook);


        dbRef = FirebaseDatabase.getInstance().getReference("Ticket"); //Firestore getting instance by document
        Log.d("MT", "Ref = " + dbRef);
        firestore = FirebaseFirestore.getInstance(); //Realtime DB instance initialization
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        // Set tickets to recycler view
        list = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ticketAdapter = new TicketAdapter(getContext(), list, this);
        recyclerView.setAdapter(ticketAdapter);

        // Getting Ticket Documents from firestore and add to the list
        firestore.collection("Tickets")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " <<< " + document.getData().getClass() + " >>> => " + document.getData());
                                Ticket ticket = new Ticket(); //Set values getting from document to a new ticket object
                                ticket.setId(document.getId());
                                ticket.setDate(document.getData().get("date").toString());
                                ticket.setApprove(document.getData().get("approve").toString());
                                ticket.setQty(document.getData().get("qty").toString());
                                ticket.setFrom(document.getData().get("from").toString());
                                ticket.setTrclass(document.getData().get("trclass").toString());
                                ticket.setTo(document.getData().get("to").toString());
                                ticket.setTime(document.getData().get("time").toString());
                                ticket.setUserId(document.getData().get("userId").toString());
                                ticket.setTrain(document.getData().get("train").toString());
                                ticket.setPrice(document.getData().get("price").toString());

                                if(ticket.getApprove().equals("1") && String.valueOf(firebaseUser.getUid()).equals(ticket.getUserId().toString())) {
                                    list.add(ticket);
                                }
                            }
                            ticketAdapter.notifyDataSetChanged();
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        // Getting Ticket from realtime db and add to the list
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
        btnBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TicketBooking tb = new TicketBooking();
                FragmentTransaction tra = getFragmentManager().beginTransaction();
                tra.replace(R.id.frag_ticket_default, tb);
                tra.commit();
            }
        });
        return view;
    }

    // When clicked on specific item
    // --> Open a new activity with parceled object as parameter
    @Override
    public void onTicketClick(int position, Ticket ticket) {
        Intent intent = new Intent(getContext(), DigitalTicket.class);
        intent.putExtra("ticket", ticket);
        startActivity(intent);
        Log.i(TAG, "onTicketClick: Clicked !" + position);
    }

    @Override
    public void onTicketDelete(int position, Ticket ticket) {
        final EditText input = new EditText(getContext());

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Confirm Deletion");
        alert.setMessage("\n" + "If you delete this ticket it will not be refunded and no refund will be made." +
                "\nAre you sure ?. (If there any reason, type below)");
        alert.setView(input);
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                ticket.setNote(input.getText().toString());

                firestore.collection("Tickets").document(ticket.getId())
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getContext(), "Deleted "+ ticket.getId() , Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "DocumentSnapshot successfully deleted!");

                                // Save data (object) to Firestore
                                DocumentReference documentReference = firestore.collection("DeletedTickets").document(ticket.getId());
                                documentReference.set(ticket).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                    }
                                });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "Error : Cannot Deleted Ticket", Toast.LENGTH_SHORT).show();
                                Log.w(TAG, "Error deleting document", e);
                            }
                        });
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();
    }
}