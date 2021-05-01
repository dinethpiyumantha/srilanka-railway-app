package com.bugapocalypse.srilankarailwaydigital;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.bugapocalypse.srilankarailwaydigital.data.Ticket;

import java.io.Serializable;
import java.util.Calendar;

import static android.content.ContentValues.TAG;

public class TicketBooking extends Fragment implements AdapterView.OnItemSelectedListener {

//    Object Declarations
    Button btnNext, btnDate, btnTime;
    Spinner spnFrom, spnTo, spnClasses;
    EditText edtQty;

    DatePickerDialog.OnDateSetListener dateSetListener;
    TimePickerDialog.OnTimeSetListener timeSetListener;

    Ticket ticketBookingIns;


//    ON CREATE FRAGMENT
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ticket_booking, container, false);


//        Initialize Objects
        spnFrom = view.findViewById(R.id.spnFrom);
        spnTo = view.findViewById(R.id.spnTo);
        spnClasses = view.findViewById(R.id.spnClasses);
        edtQty = view.findViewById(R.id.edtQty);

//        Set Arrays to Dropdowns
        ArrayAdapter adapter = ArrayAdapter.createFromResource( getContext(), R.array.stations, R.layout.custom_spinner_light);
        ArrayAdapter classAdapter = ArrayAdapter.createFromResource( getContext(), R.array.classes, R.layout.custom_spinner_light);
        adapter.setDropDownViewResource(R.layout.custom_spinner_light_drop);
        classAdapter.setDropDownViewResource(R.layout.custom_spinner_light_drop);

        spnFrom.setAdapter(adapter);
        spnTo.setAdapter(adapter);
        spnClasses.setAdapter(classAdapter);

        spnFrom.setOnItemSelectedListener(this);
        spnTo.setOnItemSelectedListener(this);
        spnClasses.setOnItemSelectedListener(this);

//        Go to Next Frag Button
        btnNext = view.findViewById(R.id.btnBook);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Submit after validtion checking
                if (btnDate.getText().toString().equals("Select...")) {
                    btnDate.setError("Please Select a Date!");
                    btnDate.requestFocus();
                } else if (btnTime.getText().toString().equals("Select...")) {
                    btnTime.setError("Please Select a Time!");
                    btnTime.requestFocus();
                } else if (TextUtils.isEmpty(edtQty.getText())) {
                    edtQty.setError("Ticket quantity is required!");
                    edtQty.requestFocus();
                } else {
                    String from = (spnFrom.getSelectedItem().toString() + " To " + spnTo.getSelectedItem().toString() + " On " + btnDate.getText());
                    Toast.makeText(getContext(), from, Toast.LENGTH_SHORT).show();

                    TicketFormStation t = new TicketFormStation();

//                    Pass Values
                    Bundle args = new Bundle();
                    args.putString("from", spnFrom.getSelectedItem().toString());
                    args.putString("to", spnTo.getSelectedItem().toString());
                    args.putString("date", btnDate.getText().toString());
                    args.putString("time", btnTime.getText().toString());
                    args.putString("class", spnClasses.getSelectedItem().toString());
                    args.putString("qty", edtQty.getText().toString());

                    t.setArguments(args);
                    FragmentTransaction tra = getFragmentManager().beginTransaction();
                    tra.replace(R.id.frag_ticket_default, t);
                    tra.commit();
                }
            }
        });

//        Date Picker
        btnDate = view.findViewById(R.id.btnDate);

        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog, dateSetListener, year, month, day);
                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: dd/mm/yyyy" + day + "/" + month + "/" + year);

                String date = day + "/" + month + "/" + year;
                btnDate.setText(date);
            }
        };

//        Time Picker
        btnTime = view.findViewById(R.id.btnTime);
        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int hours = cal.get(Calendar.HOUR_OF_DAY);
                int minutes = cal.get(Calendar.MINUTE);

                TimePickerDialog dialog = new TimePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog, timeSetListener, hours, minutes, true);
                dialog.show();
            }
        });

        timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hours, int minute) {
                Log.d(TAG, "onDateSet: hh:mm" + hours + ":" + minute);
                String time = hours + ":" + minute;
                btnTime.setText(time);
            }
        };

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

