package com.bugapocalypse.srilankarailwaydigital;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.bugapocalypse.srilankarailwaydigital.data.Station;
import com.bugapocalypse.srilankarailwaydigital.data.Train;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Fragment change relativly to the menu
        BottomNavigationView bottomNavView = findViewById(R.id.bottomNavigationMenu);
        bottomNavView.setOnNavigationItemSelectedListener(navListner);


//      PRE-EXECUTORS ----------------------------
//        new Executor().execStations();
//        new Executor().execTrains();


    }

//    Main Home Nav
    private BottomNavigationView.OnNavigationItemSelectedListener navListner =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch(item.getItemId()) {
                        case R.id.menuhome:
                            selectedFragment = new Home();
                            break;
                        case R.id.menutime:
                            selectedFragment = new TimeManagement();
                            break;
                        case R.id.menuticket:
                            selectedFragment = new TicketManagement();
                            break;
                        case R.id.menucheck:
                            selectedFragment = new TicketCheckingSystem();
                            break;
                        case R.id.menuuser:
                            selectedFragment = new UserManagement();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_controller_view,
                            selectedFragment).commit();
                    return true;
                }
            };

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        Toast.makeText(this, "From : " + adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public class Executor {
        DatabaseReference dbRef; //Firebase Realtime DB
        FirebaseFirestore firestore; //Firebase Firestore
        FirebaseUser firebaseUser;

        public void execTrains() {
            String[] array = getResources().getStringArray(R.array.train_array);

            for(int i = 0;i<array.length ; i++)
            {
                Train train = new Train(String.valueOf(i+1), array[i]);
                Log.i("ARRAY ", Train.getCollection() + " " + train.getName());

                firestore = FirebaseFirestore.getInstance();
                DocumentReference documentReference = firestore.collection(Train.getCollection()).document(String.valueOf(i+1));
                documentReference.set(train).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i("ARRAY ", Train.getCollection() + " " + train.getName());
                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("ARRAY ", "Failed!");
                    }
                });
            }
        }

        public void execStations() {
            String[] array = getResources().getStringArray(R.array.stations);

            for(int i = 0;i<array.length ; i++)
            {
                Station arrayAd = new Station(String.valueOf(i+1), array[i]);
                Log.i("ARRAY ", Train.getCollection() + " " + arrayAd.getName());

                firestore = FirebaseFirestore.getInstance();
                DocumentReference documentReference = firestore.collection(Station.getCollection()).document(String.valueOf(i+1));
                documentReference.set(arrayAd).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i("ARRAY ", Station.getCollection() + " " + arrayAd.getName());
                    }

                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i("ARRAY ", "Failed!");
                    }
                });
            }
        }
    }

}