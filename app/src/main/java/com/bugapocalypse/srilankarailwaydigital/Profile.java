package com.bugapocalypse.srilankarailwaydigital;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.concurrent.Executor;


public class Profile extends   Fragment {
    public static final String TAG="Profile";
    TextView username,fullname ,email,phone;
    FirebaseFirestore storef;
    String userID;
    Button button;
   FirebaseAuth auth;
  // FirebaseAuth.AuthStateListener authListener;








    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        username=(TextView) view.findViewById(R.id.UserName_profile);
        fullname =(TextView) view.findViewById(R.id.full_Name);
        email=(TextView) view.findViewById(R.id.Email_profile);
        phone=(TextView) view.findViewById(R.id.phone_Profile);
        button=(Button) view.findViewById(R.id.logout1);

        auth=FirebaseAuth.getInstance();
        storef=FirebaseFirestore.getInstance();
        userID=auth.getCurrentUser().getUid();

//        DocumentReference documentReference = storef.collection("users").document(userID);
//        documentReference.addSnapshotListener( this, new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
//
//                username.setText(documentSnapshot.getString("userName"));
//                fullname .setText(documentSnapshot.getString("fillName"));
//                email.setText(documentSnapshot.getString("email"));
//                phone.setText(documentSnapshot.getString("phone"));
//
//            }
//        });
        DocumentReference docRef = storef.collection("users").document(userID);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                username.setText(documentSnapshot.getString("userName"));
                fullname .setText(documentSnapshot.getString("fullName"));
                email.setText(documentSnapshot.getString("email"));
                phone.setText(documentSnapshot.getString("phone"));

            }
        });
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(getActivity(), UserLogin.class);
                        startActivity(intent);
                    }

                });

        return view;
    }
}