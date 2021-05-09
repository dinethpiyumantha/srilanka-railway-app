package com.bugapocalypse.srilankarailwaydigital;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;


public class Profile extends   Fragment {
    public static final String TAG="Profile";
    TextView username,fullname ,email,phone;
    FirebaseFirestore storef;
    String userID;
    Button button;
    Button passwordReSetBtn;
    Button UpdateProBtn;
    ImageView profilepic;
    StorageReference storageReference;

   FirebaseAuth auth;
   FirebaseUser user;
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
        button=(Button) view.findViewById(R.id.saveBtn);
        profilepic=(ImageView) view.findViewById(R.id.propic);

        passwordReSetBtn=(Button) view.findViewById(R.id.Reset_password);
        UpdateProBtn=(Button) view.findViewById(R.id.updateProfile);


        auth=FirebaseAuth.getInstance();
        storef=FirebaseFirestore.getInstance();
        userID=auth.getCurrentUser().getUid();
        user=auth.getCurrentUser();
        storageReference= FirebaseStorage.getInstance().getReference();
        StorageReference proPic= storageReference.child("users/"+auth.getCurrentUser().getUid()+"/profile.jpg");
        proPic.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profilepic);
            }
        });

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

                passwordReSetBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final EditText resetpwrd= new EditText(v.getContext());

                       final AlertDialog.Builder passwordReset =new AlertDialog.Builder(v.getContext());
                        passwordReset.setTitle("Reset Password ?");
                        passwordReset.setMessage("Enter New Password (characters > 6)");
                        passwordReset.setView(resetpwrd);

                        passwordReset.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                String newPassowrd = resetpwrd.getText().toString();
                             user.updatePassword(newPassowrd).addOnSuccessListener(new OnSuccessListener<Void>() {
                                 @Override
                                 public void onSuccess(Void aVoid) {
                                     Toast.makeText(getActivity(), "Password Reset Successfully !", Toast.LENGTH_SHORT).show();

                                 }

                                }).addOnFailureListener(new OnFailureListener() {
                                 @Override
                                 public void onFailure(@NonNull Exception e) {
                                     Toast.makeText(getActivity(), "Erroe !", Toast.LENGTH_SHORT).show();
                                 }
                             });


                            }
                        });

                        passwordReset.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        passwordReset.create().show();
                    }


                });






           UpdateProBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intents= new Intent(v.getContext(),UpdateProfile.class);
                intents.putExtra("username",username.getText().toString());
                intents.putExtra("fullname",fullname.getText().toString());
                intents.putExtra("email",email.getText().toString());
                intents.putExtra("phone",phone.getText().toString());
                startActivity( intents);
            }

        });
        profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open glry
                Intent galleryOpen=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryOpen,1000);

            }

        });



        return view;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode,@androidx.annotation.Nullable  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1000){
            // if(requestCode == Activity.RESULT_OK){
            Uri imageUri= data.getData();
             //   profilepic.setImageURI(imageUri);

            uploadImgToFirebase(imageUri);
        }

        // }
    }
    private void uploadImgToFirebase(Uri imageUri) {
        StorageReference fileReference= storageReference.child("users/"+auth.getCurrentUser().getUid()+"/profile.jpg");
        fileReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Toast.makeText(UpdateProfile.this, "image uploaded successfully", Toast.LENGTH_SHORT).show();
                fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        Picasso.get().load(uri).into(profilepic);

                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
              //  Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
            }
        });

    }


}
