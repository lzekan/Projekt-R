package com.example.skladiteapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonTake = (Button) findViewById(R.id.buttonTake);
        buttonTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, new TakeFragment())
                        .commit();
            }
        });

        Button buttonGet = (Button) findViewById(R.id.buttonGet);
        buttonGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, new GetFragment())
                        .commit();
            }
        });

        Button buttonRelocate = (Button) findViewById(R.id.buttonRelocate);
        buttonRelocate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, new RelocateFragment())
                        .commit();
            }
        });

        Button buttonLogOut = (Button) findViewById(R.id.buttonLogout);
        buttonLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createMessage("Želite li se odjaviti?", "logout");
            }
        });


        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new TakeFragment()).commit();
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
                //nothing
            }
        });

        builder.show();
    }
}