package com.bugapocalypse.srilankarailwaydigital;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class before_admin_gif extends AppCompatActivity {
    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_admin_gif);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(before_admin_gif.this,Qr_scanner.class);
                startActivities(new Intent[]{intent});
                finish();
            }
        },5000);
    }


}
