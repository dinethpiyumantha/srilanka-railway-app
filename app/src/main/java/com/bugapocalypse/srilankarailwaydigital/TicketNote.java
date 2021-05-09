package com.bugapocalypse.srilankarailwaydigital;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bugapocalypse.srilankarailwaydigital.data.Cheking;
import com.bugapocalypse.srilankarailwaydigital.data.Ticket;
import com.bugapocalypse.srilankarailwaydigital.data.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.Date;


public class TicketNote extends Fragment {
    private TextView ticketID;
    private TextView destination;
    private TextView trainNamee;
    private TextView classTrain;
    private TextView dateAndTime;
    private TextView userNic;
    private TextView fullName;
    private TextView numbernew;
    private TextView emailnew;
    private String noteString;
    private String userid;
    private String dateAndTimeuser;
    private String fromTrain;
    private String ticketIDString;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<Ticket> list = new ArrayList<>();
    ArrayList<User> userList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_ticket_note, container, false);
        ticketID = v.findViewById(R.id.ticketID);
        destination = v.findViewById(R.id.destination);
        trainNamee = v.findViewById(R.id.trainNamee);
        classTrain = v.findViewById(R.id.classTrain);
        dateAndTime = v.findViewById(R.id.dateAndTime);
        userNic = v.findViewById(R.id.userIdnew);
        fullName = v.findViewById(R.id.fullNamenew);
        numbernew = v.findViewById(R.id.numbernew);
        emailnew = v.findViewById(R.id.emailnew);
        EditText note = v.findViewById(R.id.note);
        Button btn = v.findViewById(R.id.checkTicket);
        String vall= getArguments().getString("id");

        //get data from database
        db.collection("Tickets")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Ticket ticket = new Ticket();
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
                                //set data ticket object
                                if(ticket.getId().equals(vall)) {
                                    // Set values to user object
                                        ticketID.setText(ticket.getId());
                                        ticketIDString = ticket.getId();
                                        destination.setText(ticket.getFrom());
                                        trainNamee.setText(ticket.getTrain());
                                        classTrain.setText(ticket.getTrclass());
                                        dateAndTime.setText(ticket.getDate() + " " + ticket.getTime());
                                        userid = ticket.getUserId().toString();
                                        dateAndTimeuser = ticket.getDate() + " " + ticket.getTime();
                                        fromTrain = ticket.getFrom();

                                    FirebaseFirestore.getInstance().collection("users")
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    Log.d("2DB","Done line 106");
                                                    User user = new User();
                                                    if (task.isSuccessful()) {
                                                        Log.d("2DB","Done if line 109");
                                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                                            Log.d("2DB","Done for lines 111 = " + document.getId());
                                                            //Log.d("TESTID", document.getId() + " <<< " + document.getData().getClass() + " >>> => " + document.getData());
                                                            user.setId(document.getId());
                                                            user.setFullname(document.getData().get("fullName").toString());
                                                            user.setNic(document.getData().get("nic").toString());
                                                            user.setPhon(document.getData().get("phone").toString());
                                                            user.setEmail(document.getData().get("email").toString());

                                                            if(user.getId().equals(userid)) {
                                                                Log.d("2DB","Done for-if line 128");
                                                                userNic.setText(user.getNic());
                                                                fullName.setText(user.getFullname());
                                                                numbernew.setText(user.getPhon());
                                                                emailnew.setText(user.getEmail());
                                                               userList.add(user);
                                                            }


                                                        }

                                                    } else {
                                                        Log.d("TAG", "Error getting documents: ", task.getException());
                                                    }
                                                }
                                            });


                                }
                            }

                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //chang fragment
                CheckedTickets fragment = new CheckedTickets();
                FragmentTransaction tra = getFragmentManager().beginTransaction();
                tra.replace(R.id.ticketnoteFagment, fragment);
                Log.i("TAGPASS", "test pass");
                tra.commit();
                //create checking object
                Cheking cheking = new Cheking();
                cheking.setTicketIdcheak(ticketIDString);
                cheking.setTime(dateAndTimeuser);
                cheking.setTrain(fromTrain);
                noteString = note.getText().toString();
                cheking.setNote(noteString);
                Date date = java.util.Calendar.getInstance().getTime();
                cheking.setDate(date);
                //create cheking table in Firebase Insert data
                FirebaseFirestore.getInstance().collection("checking")
                        .document()
                        .set(cheking).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                    }
                });
            }

        });


        return v;
    }
}