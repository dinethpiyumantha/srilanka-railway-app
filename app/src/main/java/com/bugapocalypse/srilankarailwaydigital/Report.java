package com.bugapocalypse.srilankarailwaydigital;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bugapocalypse.srilankarailwaydigital.data.Ticket;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Report extends Fragment {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    LocalDate myObj = LocalDate.now();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_report, container, false);
        Log.d("TEST","Line49"+">>"+myObj);
        GraphView graph = (GraphView)v.findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        graph.addSeries(series);

        db.collection("checking")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Ticket ticket = new Ticket();
                                ticket.setId(document.getId());
                                Log.d("TEST","Line49");
                                //Log.d("DATEMY",document.getData().get("date").toString());
                                //String time = document.getData().get("date").toString();
                                Timestamp time = (Timestamp) document.getData().get("date");
                                Log.d("TEST","Line62");
                                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                                //System.out.println(formatter.format(time));
                                Log.d("TEST","Line65");
                                String dateMy = formatter.format(time);
                                Log.d("TEST","Line67");
                                Log.d("TEST","Line61"+">>"+dateMy);
                            }
                        }
                    }
                });

        return v;
    }

}