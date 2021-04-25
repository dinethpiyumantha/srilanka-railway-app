package com.bugapocalypse.srilankarailwaydigital;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Fragment change relativly to the menu
        BottomNavigationView bottomNavView = findViewById(R.id.bottomNavigationMenu);
        bottomNavView.setOnNavigationItemSelectedListener(navListner);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListner =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch(item.getItemId()) {
                        case R.id.menuhome:
                            selectedFragment = new Home();
                            break;
                        case R.id.menutime:
                            selectedFragment = new TimeManagement();
                            break;
                        case R.id.menuticket:
                            selectedFragment = new TicketManagement();
                            break;
                        case R.id.menucheck:
                            selectedFragment = new TicketCheckingSystem();
                            break;
                        case R.id.menuuser:
                            selectedFragment = new UserManagement();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_controller_view,
                            selectedFragment).commit();
                    return true;
                }
            };

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        Toast.makeText(this, "From : " + adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}