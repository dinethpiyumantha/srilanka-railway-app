package com.bugapocalypse.srilankarailwaydigital;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class Qr_scanner extends AppCompatActivity {

    CodeScanner codeScanner;
    CodeScannerView codeScannerView;
    String resultData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_scanner);
        //Get Scanner View
        codeScannerView = findViewById(R.id.scannerView);
        codeScanner = new CodeScanner(this,codeScannerView);
        //scanning and decoding
        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                 runOnUiThread(new Runnable() {
                     @Override
                     public void run() {
                            resultData = result.getText();
                            //View toast masg in ticket id
                         Toast.makeText(Qr_scanner.this,resultData,Toast.LENGTH_LONG).show();
                 }
                 });
            }

        });
        codeScannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codeScannerView.setVisibility(View.GONE);
               FragmentManager fragmentManager=getSupportFragmentManager();
               TicketNote figment = new TicketNote();
               // send data scanner fragment to another fragment
              fragmentManager.beginTransaction().replace(R.id.qrScan,figment).commit();
                Bundle bundle = new Bundle();
               bundle.putString("id",resultData);
               figment.setArguments(bundle);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        //
        requestForCamare();

    }

    private void requestForCamare() {
        Dexter.withActivity(this).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                codeScanner.startPreview();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {
                Toast.makeText(Qr_scanner.this,"Camarea permission is Requied",Toast.LENGTH_LONG).show();

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                token.continuePermissionRequest();

            }
        }).check();
    }


}
