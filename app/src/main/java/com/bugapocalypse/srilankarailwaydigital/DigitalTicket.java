// Dineth
package com.bugapocalypse.srilankarailwaydigital;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bugapocalypse.srilankarailwaydigital.data.Ticket;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class DigitalTicket extends AppCompatActivity {

    // Items Declaration
    Button btnBack;
    ImageView qrCodeImage;
    TextView txtRoute, txtTrain, txtPrice, txtDateTime, txtClass;

    // Data Attribute Daclaration
    Ticket ticket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_digital_ticket);

        // Item Initialization from xml
        btnBack = (Button) findViewById(R.id.btnCancel);
        qrCodeImage = (ImageView) findViewById(R.id.qrCodeImage);
        txtRoute = findViewById(R.id.txtRoute);
        txtTrain = findViewById(R.id.txtTrain);
        txtPrice = findViewById(R.id.txtPrice);
        txtDateTime = findViewById(R.id.txtDateTime);
        txtClass = findViewById(R.id.txtClass);

        // Get parcel from recycler view item and assign to instance
        ticket = getIntent().getParcelableExtra("ticket");
        
        txtRoute.setText(ticket.getFrom() + " - " + ticket.getTo());
        txtTrain.setText(ticket.getTrain());
        txtPrice.setText("LKR." + ticket.getPrice());
        txtDateTime.setText(ticket.getDate() + " " + ticket.getTime());
        txtClass.setText(ticket.getTrclass());

//        =========== Testing Purpose =============
//        ticket = new Ticket();
//        ticket.setId("1");
//        ticket.setUserId("1");
//        ticket.setFrom("Kurunegala");
//        ticket.setTo("Colombo");
//        ticket.setTime("10:35");
//        ticket.setDate("20/3/2021");
//        ticket.setTrclass("class 1");
//        ticket.setTrain("Yal Devi");
//        ticket.setQty("2");
//        ==========================================

        // Creating String Code
        String codeString = ticket.getId();
       /* + "," +
                ticket.getUserId() + "," +
                ticket.getFrom() + "," +
                ticket.getTo() + "," +
                ticket.getTime() + "," +
                ticket.getDate() + "," +
                ticket.getTrclass() + "," +
                ticket.getTrain() + "," +
                ticket.getQty();*/

        // Generate QR Code and Convert it to Bitmap
        MultiFormatWriter writer = new MultiFormatWriter();
        try {
            BitMatrix matrix = writer.encode(codeString, BarcodeFormat.QR_CODE,
                    300, 300);
            BarcodeEncoder encoder = new BarcodeEncoder();
            Bitmap bitmap = encoder.createBitmap(matrix);
            qrCodeImage.setImageBitmap(bitmap);  // Set Bitmap to image view
        } catch (WriterException e) {
            e.printStackTrace();
        }

        // Back Button Implementation
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DigitalTicket.super.onBackPressed();
            }
        });
    }



}