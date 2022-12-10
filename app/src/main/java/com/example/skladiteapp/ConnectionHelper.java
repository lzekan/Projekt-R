package com.example.skladiteapp;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.StrictMode;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionHelper extends AppCompatActivity {
    Connection connection;
    String username, password, ip, port, database;


    @SuppressLint("NewApi")
    public Connection connect() {
        ip = "dpg-cdr5gdta4991vasl3c7g-a.frankfurt-postgres.render.com";
        database = "demo";
        port = "5432";
        username = "hrvoje";
        password = "kQq8UdiQANJE7ELlIxdftlXx6T7ip9dc";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String ConnectionURL = null;

        try {
            Class.forName("org.postgresql.Driver");
            ConnectionURL = "jdbc:postgresql://"+ip+":"+port+"/"+database;
            connection = DriverManager.getConnection(ConnectionURL, username, password);


        } catch (Exception e) {
            createMessage("Molimo provjerite internetsku vezu i pokušajte ponovno.");
        }

        return connection;
    }
    private void createMessage(String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ConnectionHelper.this);
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
