// Dineth
package com.bugapocalypse.srilankarailwaydigital;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.bugapocalypse.srilankarailwaydigital.data.Ticket;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TicketFormStation extends Fragment implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "TFS"; // Log TAG

    // Declaration of Items
    Button cancel, btnBook;
    TextView txtFrom, txtTo;
    Spinner spnTrainSelect;

    // DATABASE instances
    DatabaseReference dbRef; //Firebase Realtime DB
    FirebaseFirestore firestore; //Firebase Firestore

    // Data Attribute Declaration
    Ticket ticket;

    // Notification Channel Info Inizialize
    String name = "Notification Channel";
    String CHANNEL_ID = "ID_1";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ticket_form_station, container, false);

        // Intialize objects from xml to items
        txtFrom = view.findViewById(R.id.txtFrom);
        txtTo = view.findViewById(R.id.txtTo);
        spnTrainSelect = view.findViewById(R.id.spnTrainSelect);
        cancel = view.findViewById(R.id.btnCancel);
        btnBook = view.findViewById(R.id.btnBook);

        // Getting Firestore static instance
        firestore = FirebaseFirestore.getInstance();

        // Getting passed values on create this from TicketBooking()
        Bundle args = this.getArguments();
        txtFrom.setText(args.getString("from"));
        txtTo.setText(args.getString("to"));

        // Creating an Ticket Object and Set values to it
        ticket = new Ticket();
        ticket.setId("1");
        ticket.setPrice("500.00");
        ticket.setUserId("1");
        ticket.setFrom(args.getString("from").toString().trim());
        ticket.setTo(args.getString("to").toString().trim());
        ticket.setDate(args.getString("date").toString().trim());
        ticket.setTime(args.getString("time").toString().trim());
        ticket.setTrclass(args.getString("class").toString().trim());
        ticket.setQty(args.getString("qty").toString().trim());;
        ticket.setTrain("Sample");

        // Spinner for train selection
        ArrayAdapter adapter = ArrayAdapter.createFromResource( getContext(), R.array.stations, R.layout.custom_spinner_light);
        adapter.setDropDownViewResource(R.layout.custom_spinner_light_drop);
        spnTrainSelect.setAdapter(adapter);
        spnTrainSelect.setOnItemSelectedListener(this);

        // Creating a notification channel
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription("Hello World !");

            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }


        // on click Cancel Button
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyTicket t = new MyTicket();
                FragmentTransaction tra = getFragmentManager().beginTransaction();
                tra.replace(R.id.frag_ticket_default, t);
                tra.commit();
            }
        });

        // on click Book Button
        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save(v);
            }
        });

        return view;
    }


    // Firebase Data save method
    public void save(View view) {

        // Confirmation Dialog (YES/NO)
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setTitle("Confirm");
        alert.setMessage("Are you sure you want to request a e-ticket?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            // --> on click YES
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Get firestore Ticket instance (Realtime)
                dbRef = FirebaseDatabase.getInstance().getReference().child("Ticket");
                try {

                    // Save data (object) to Realtime
                    dbRef.push().setValue(ticket);

                    // Manipulate Ticket ID
                    Date date = new Date();
                    String documentID = "U" + new SimpleDateFormat("yyyymmddHHmmss").format(date);
//                    String documentID = "U" + new SimpleDateFormat("yyyy").format(date) +
//                                    new SimpleDateFormat("mm").format(date) +
//                                    new SimpleDateFormat("dd").format(date) +
//                                    new SimpleDateFormat("HH").format(date) +
//                                    new SimpleDateFormat("mm").format(date) +
//                                    new SimpleDateFormat("ss").format(date);

                    // Save data (object) to Firestore
                    DocumentReference documentReference = firestore.collection("Tickets").document(documentID);
                    documentReference.set(ticket).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                        }
                    });

                    // Provide success Toast to user
                    Toast.makeText(getContext(), "Ticket Booking Request Sent Successfull!", Toast.LENGTH_SHORT).show();

                    // Push a success Notification
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), CHANNEL_ID)
                            .setSmallIcon(R.mipmap.railway_launcher)
                            .setContentTitle("Ticket Booking Request Sent")
                            .setContentText("Please wait until you contact for confirm !")
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            .setAutoCancel(true);
//                    builder.setColorized(true).setColor(Color.parseColor("#dddddd"));

                    NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getContext());
                    notificationManagerCompat.notify(0, builder.build());

                } catch (Exception e) {
                    Log.i(TAG, "Save : " + e );
                }

                // Close dialog
                dialog.dismiss();

                // After perform change fragment to MyTicket()
                MyTicket t = new MyTicket();
                FragmentTransaction tra = getFragmentManager().beginTransaction();
                tra.replace(R.id.frag_ticket_default, t);
                tra.commit();
            }
        });

        // --> on click NO
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}