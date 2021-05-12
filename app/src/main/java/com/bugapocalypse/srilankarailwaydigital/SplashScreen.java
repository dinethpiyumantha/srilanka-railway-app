package com.bugapocalypse.srilankarailwaydigital;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

import com.bugapocalypse.srilankarailwaydigital.data.Train;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN, LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        //Run SplashScreen at begining
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                Intent i = new Intent(SplashScreen.this,CreateUser.class);
                Intent i = new Intent(SplashScreen.this,UserLogin.class);
                startActivity(i);
                finish();
            }
        }, 3000);
    }





}