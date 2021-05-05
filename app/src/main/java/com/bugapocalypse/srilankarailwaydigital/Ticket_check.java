package com.bugapocalypse.srilankarailwaydigital;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Ticket_check extends AppCompatActivity {

    private TextView id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_check);

        id = findViewById(R.id.textView2);

        String idn = getIntent().getStringExtra("id");

        id.setText(idn);
    }
}