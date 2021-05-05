package com.bugapocalypse.srilankarailwaydigital;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class UserLogin extends AppCompatActivity {
    EditText email,PasswordL;
    Button login;
    TextView CreatBtn,ResetPasswordBtn;
   // ProgressBar progressBar;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        email = (EditText) findViewById(R.id.UsernameL);
        PasswordL = (EditText) findViewById(R.id.logPaswrd);
        CreatBtn = (TextView) findViewById(R.id.CreatBtn);
        ResetPasswordBtn = (TextView) findViewById(R.id.ResetPasswordBtn);
        auth = FirebaseAuth.getInstance();
        login = (Button) findViewById(R.id.login);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = email.getText().toString().trim();
                String password = PasswordL.getText().toString().trim();

                if (TextUtils.isEmpty(Email)) {
                    email.setError("Enter email address!");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    PasswordL.setError("Enter  Password!");
                    return;
                }
                //authonticate user

                auth.signInWithEmailAndPassword(Email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(UserLogin.this, "Login SuccessFully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            Toast.makeText(UserLogin.this, "Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();


                        }

                    }

            });
            }
        });


        CreatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserLogin.this, CreateUser.class);
                startActivity(i);
            }
        });
    }

//
//    public void gotoCreate(View v) {
//        startActivity(new Intent(this, ViewTicket.class));
//        Log.i("HELLO", "Atarted CREATE......---->");
//    }
}