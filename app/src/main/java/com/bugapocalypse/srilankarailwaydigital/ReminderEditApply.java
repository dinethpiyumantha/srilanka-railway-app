package com.bugapocalypse.srilankarailwaydigital;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ReminderEditApply extends AppCompatActivity {

    Button time, date;
    Spinner from, to;
    Button apply, clear, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_reminder_edit);

        time = findViewById(R.id.timeEdit);
        date = findViewById(R.id.dateEdit);
        from = findViewById(R.id.fromEdit);
        to = findViewById(R.id.toEdit);

        back = (Button)findViewById((R.id.btnback3));
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ReminderEdit.class));
                finish();
            }
        }) ;

        apply = (Button)findViewById((R.id.btnApply));
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processinsert();
            }

        }) ;

    }

    private void processinsert() {

        Map<String,Object> map = new HashMap<>();
        map.put("time", time.getText().toString());
        map.put("date", date.getText().toString());
        map.put("from", from.getSelectedItem().toString());
        map.put("to", to.getSelectedItem().toString());
        FirebaseDatabase.getInstance().getReference().child("").push().setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                time.setText("");
                date.setText("");
                //from.setText("");
                //to.setText("");
                Toast.makeText(getApplicationContext(), "Inserted Successfully", Toast.LENGTH_LONG).show();
            }
        })
         .addOnFailureListener(new OnFailureListener() {
             @Override
             public void onFailure(@NonNull Exception e) {
                 Toast.makeText(getApplicationContext(), "Could not Inserted Successfully", Toast.LENGTH_LONG).show();
             }
         }) ;
    }
}
