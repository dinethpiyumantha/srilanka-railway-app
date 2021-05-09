package com.bugapocalypse.srilankarailwaydigital;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//import com.bugapocalypse.srilankarailwaydigital.data.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreateUser extends AppCompatActivity {
    public static final String TAG = "TAG";
    EditText Username,Fullname,NIC,Email,Phone,inputPassword,ReinputPassword;
     Button Creat,Cancel;
    // ProgressBar progressBar;
     FirebaseAuth auth;
     FirebaseFirestore storef;
     String userID;

//     DatabaseReference dbRef;
//     User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        Username = (EditText) findViewById(R.id.Username);
        Fullname = (EditText) findViewById(R.id.fullName);
        NIC = (EditText) findViewById(R.id.nic);
        Email = (EditText) findViewById(R.id.email);
        Phone = (EditText) findViewById(R.id.phone);
        inputPassword = (EditText) findViewById(R.id.password);
        ReinputPassword = (EditText) findViewById(R.id.Repassword);
        Creat = (Button) findViewById(R.id.btnfind);
        Cancel = (Button) findViewById(R.id.logout1);
       // progressBar = (ProgressBar) findViewById(R.id.progressBar);



        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        storef=FirebaseFirestore.getInstance();

        if(auth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();

        }

        Creat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = Username.getText().toString();
                String fullname = Fullname.getText().toString();
                String nic = NIC.getText().toString();
                String email = Email.getText().toString().trim();
                String phone = Phone.getText().toString();
                String password = inputPassword.getText().toString().trim();
                String password2 = ReinputPassword.getText().toString().trim();

                if (TextUtils.isEmpty(username)) {
                    Username.setError("Enter email address!");
                    return;
                }
                if (TextUtils.isEmpty(fullname)) {
                    Fullname.setError("Enter email address!");
                    return;
                }
                if (TextUtils.isEmpty(nic)) {
                    NIC.setError("Enter email address!");
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    Phone.setError("Enter email address!");
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    Email.setError("Enter email address!");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    inputPassword.setError("Enter email Password!");
                    return;
                }
                if (TextUtils.isEmpty(password2)) {
                    ReinputPassword.setError("Enter email Password!");
                    return;
                }
                if (password.length() < 6) {
                    inputPassword.setError(" Password must greater than six character");
                    return;
                }
                if (password2.length() < 6) {
                    ReinputPassword.setError(" Password must greater than six character");
                    return;
                }
//                progressBar.setVisibility(View.VISIBLE);

//           user.setUsername(username);
//                user.setFullname(fullname);
//                user.setEmail(email);
//                user.setNic(nic);
//                user.setPassword(password);
//                user.getPhon(phone);
//
//                dbRef = FirebaseDatabase.getInstance().getReference().child("User");
//                try {
//                    dbRef.push().setValue(user);
//                } catch (Exception e) {
//                    Log.i(TAG, "Save : " + e);
//                }
                //--------------Firebase----------------------------------
//
//
                     auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                       if (task.isSuccessful()) {
                            Toast.makeText(CreateUser.this, "User Creating", Toast.LENGTH_SHORT).show();
                            userID=auth.getCurrentUser().getUid();
                            DocumentReference documentReference=storef.collection("users").document(userID);
                            Map<String, Object> user = new HashMap<>();
                            user.put("userName",username);
                            user.put("fullName",fullname);
                            user.put("nic",nic);
                            user.put("email",email);
                            user.put("phone",phone );

                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                              public void onSuccess(Void aVoid){
                                     //   Log.d(TAG, "onSuccess: User profile criated"+userID);

                                }
                            });



                startActivity(new Intent(getApplicationContext(), UserLogin.class));
            }
            else {
                            Toast.makeText(CreateUser.this, "Error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();


                        }

                    }
                });


            }
        });

    }
}