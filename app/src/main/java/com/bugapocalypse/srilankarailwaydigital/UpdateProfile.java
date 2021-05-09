package com.bugapocalypse.srilankarailwaydigital;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class UpdateProfile extends AppCompatActivity {
        public  static final String TAG="TAG";
        EditText userNameSave,fullNameSave,emailSave,phoneSave;
        Button saveBtn;
        FirebaseAuth auth;
        ImageView profilepic;
        FirebaseFirestore storeS;
        FirebaseUser user;
       // StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        Intent data=getIntent();
        String userName= data.getStringExtra("username");
        String fulName= data.getStringExtra("fullname");
        String emails= data.getStringExtra("email");
        String phones= data.getStringExtra("phone");


        auth=FirebaseAuth.getInstance();
        storeS=FirebaseFirestore.getInstance();
        user = auth.getCurrentUser();
      //  storageReference= FirebaseStorage.getInstance().getReference();
       // StorageReference profic= storageReference.child("profile.jpg");

        userNameSave=findViewById(R.id.usernamesave);
        fullNameSave=findViewById(R.id.fullNameSave);
        emailSave=findViewById(R.id.emailSave);
        phoneSave=findViewById(R.id.phoneSave);
        saveBtn=findViewById(R.id.saveBtn);
        profilepic=(ImageView) findViewById(R.id.propic1);


        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userNameSave.getText().toString().isEmpty() || fullNameSave.getText().toString().isEmpty() || emailSave.getText().toString().isEmpty() || phoneSave.getText().toString().isEmpty()) {
                    Toast.makeText(UpdateProfile.this, "Fields Are Empty", Toast.LENGTH_SHORT).show();
                    return;

                }

                String email = emailSave.getText().toString();
                user.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        DocumentReference documentReference=storeS.collection("users").document(user.getUid());
                        Map<String,Object> edit= new HashMap<>();
                        edit.put("email",email);
                        edit.put("userName",userNameSave.getText().toString());
                        edit.put("fullName",fullNameSave.getText().toString());
                        edit.put("phone",phoneSave.getText().toString());
                        documentReference.update(edit).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(UpdateProfile.this, "save is done", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                finish();
                            }
                        });
                        Toast.makeText(UpdateProfile.this, "Email is changed", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UpdateProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }


        });
//        profilepic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //open glry
//                Intent galleryOpen=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(galleryOpen,1000);
//
//            }
//
//        });


        userNameSave.setText(userName);
        fullNameSave.setText(fulName);
        emailSave.setText(emails);
        phoneSave.setText(phones);

        Log.d(TAG,"oncrate"+userName+" "+fulName+" "+emails+" "+phones);
    }


//        @Override
//    protected void onActivityResult(int requestCode, int resultCode,@androidx.annotation.Nullable  Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//       if(requestCode == 1000){
//        // if(requestCode == Activity.RESULT_OK){
//                Uri imageUri= data.getData();
//            //    profilepic.setImageURI(imageUri);
//
//                uploadImgToFirebase(imageUri);
//          }

     // }
//    }
//
//    private void uploadImgToFirebase(Uri imageUri) {
//        StorageReference fileReference= storageReference.child("profile.jpg");
//        fileReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//               // Toast.makeText(UpdateProfile.this, "image uploaded successfully", Toast.LENGTH_SHORT).show();
//            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                @Override
//                public void onSuccess(Uri uri) {
//
//                    Picasso.get().load(uri).into(profilepic);
//
//                }
//            });
//
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(UpdateProfile.this, "image upload unsuccessfully", Toast.LENGTH_SHORT).show();
//            }
//        });

    //}

}