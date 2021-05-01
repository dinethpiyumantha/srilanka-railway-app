package com.bugapocalypse.srilankarailwaydigital;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.bugapocalypse.srilankarailwaydigital.data.Ticket;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TicketFormStation extends Fragment {

    Button cancel, btnBook;
    TextView txtFrom, txtTo;

    String name = "Notification Channel";
    String CHANNEL_ID = "ID_1";

    DatabaseReference dbRef;
    Ticket ticket;

    private static final String TAG = "TicketFormStation";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ticket_form_station, container, false);

        txtFrom = view.findViewById(R.id.txtFrom);
        txtTo = view.findViewById(R.id.txtTo);

        Bundle args = this.getArguments();
        txtFrom.setText(args.getString("from"));
        txtTo.setText(args.getString("to"));

        ticket = new Ticket();
        ticket.setFrom(args.getString("from").toString().trim());
        ticket.setTo(args.getString("to").toString().trim());
        ticket.setDate(args.getString("date").toString().trim());
        ticket.setTime(args.getString("time").toString().trim());
        ticket.setTrclass(args.getString("class").toString().trim());
        ticket.setQty(args.getString("qty").toString().trim());;

//        Create Notification Channel
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription("Hello World !");

            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        cancel = view.findViewById(R.id.btnCancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyTicket t = new MyTicket();
                FragmentTransaction tra = getFragmentManager().beginTransaction();
                tra.replace(R.id.frag_ticket_default, t);
                tra.commit();
            }
        });

        btnBook = view.findViewById(R.id.btnBook);
        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyTicket t = new MyTicket();

////              Norification Push
//                NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), CHANNEL_ID)
//                        .setSmallIcon(R.mipmap.railway_launcher)
//                        .setContentTitle("Ticket Booking Request Sent")
//                        .setContentText("Please wait until you contact for confirm !")
//                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                        .setAutoCancel(true);
//
//                NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getContext());
//                notificationManagerCompat.notify(0, builder.build());

                save(v);
//                Change Fragment
                FragmentTransaction tra = getFragmentManager().beginTransaction();
                tra.replace(R.id.frag_ticket_default, t);
                tra.commit();

            }
        });
        return view;
    }

//    Insert Operation FIREBASE
    public void save(View view) {
        dbRef = FirebaseDatabase.getInstance().getReference().child("Student");
        try {
//            Send object to the FIrebase
            dbRef.push().setValue(ticket);

            Toast.makeText(getContext(), "Ticket Booking Request Sent Successfull!", Toast.LENGTH_SHORT).show();

//            Norification Push
            NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), CHANNEL_ID)
                    .setSmallIcon(R.mipmap.railway_launcher)
                    .setContentTitle("Ticket Booking Request Sent")
                    .setContentText("Please wait until you contact for confirm !")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setAutoCancel(true);

            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getContext());
            notificationManagerCompat.notify(0, builder.build());

        } catch (Exception e) {
            Log.i(TAG, "Save : " + e );
        }
    }
}