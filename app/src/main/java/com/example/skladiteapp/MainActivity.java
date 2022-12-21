package com.example.skladiteapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    private DrawerLayout drawer;
    private NavigationBarView navigationBarView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationBarView = findViewById(R.id.bottomNavigationView);

        navigationBarView.setOnItemSelectedListener(this::onNavigationItemSelected);
        navigationBarView.setSelectedItemId(R.id.iconIzdajAlt);

    }

    private void createMessage(String text, String type) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setMessage(text);


        builder.setTitle("Odjava.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
        builder.setNegativeButton("Odustani", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                navigationBarView.setSelectedItemId(R.id.iconIzdajAlt);
            }
        });

        builder.show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.iconIzdajAlt:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new TakeFragment()).commit();
                return true;

            case R.id.iconZaprimiAlt:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new GetFragment()).commit();
                return true;

            case R.id.iconPremjestiAlt:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new RelocateFragment()).commit();
                return true;

            case R.id.iconStanjeAlt:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new StateFragment()).commit();
                return true;

            case R.id.iconLogoutAlt:
                createMessage("Želite li se odjaviti?", "logout");
                return true;
        }
        return false;
    }
}