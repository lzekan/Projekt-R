package com.example.skladiteapp;

import static java.nio.charset.StandardCharsets.UTF_8;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView btn = findViewById(R.id.jumpToRegister);
        TextView main = findViewById(R.id.btnLogin);

        //Register
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        //Login
        main.setOnClickListener(new View.OnClickListener() {
            boolean wasFound = false;
            EditText editTextUsername = (EditText) findViewById(R.id.inputUsername);
            EditText editTextPassword = (EditText) findViewById(R.id.inputPassword);

            @Override
            public void onClick(View view) {
                Thread thread = new Thread(() -> {
                    URL url = null;
                    try {
                        url = new URL("http://192.168.62.166:8080/api/userByUsername?username="+editTextUsername.getText().toString());
                        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                        urlConnection.setRequestMethod("GET");
                        urlConnection.connect();

                        StringBuilder sb = new StringBuilder();

                        try(BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), UTF_8))){
                            String str;
                            while ((str = br.readLine()) != null) {
                                sb.append(str);
                            }
                        }

                        if(sb.toString().length() == 0) {
                            createMessage("Neispravno korisničko ime ili lozinka.");
                        }

                        JSONObject obj = new JSONObject(sb.toString());

                        if(editTextPassword.getText().toString().equals(obj.get("password"))){
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            wasFound = true;
                        }



                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                });

                thread.start();

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(!wasFound) {
                    createMessage("Neispravno korisničko ime ili lozinka.");
                }

            }
        });
    }

    private void createMessage(String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setCancelable(true);
        builder.setMessage(text);
        builder.setTitle("Dogodila se greška!");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }
}