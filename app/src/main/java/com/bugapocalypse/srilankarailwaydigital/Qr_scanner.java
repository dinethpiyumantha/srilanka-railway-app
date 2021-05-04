package com.bugapocalypse.srilankarailwaydigital;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

public class Qr_scanner extends AppCompatActivity {

    CodeScanner codeScanner;
    CodeScannerView codeScannerView;
    String resultData;




    @Override
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_scanner);

        codeScannerView = findViewById(R.id.scannerView);
        codeScanner = new CodeScanner(this,codeScannerView);

        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                 runOnUiThread(new Runnable() {
                     @Override
                     public void run() {
                            resultData = result.getText();
                         Toast.makeText(Qr_scanner.this,resultData,Toast.LENGTH_LONG).show();
                     }
                 });
            }

        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        codeScanner.startPreview();

    }


}
