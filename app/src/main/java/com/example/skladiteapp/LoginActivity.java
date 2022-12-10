package com.example.skladiteapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

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
            @Override
            public void onClick(View view) {
                boolean wasFound = false;
                Connection connect;
                String ConnectionResult = "";
                EditText editTextUsername = (EditText) findViewById(R.id.inputUsername);
                EditText editTextPassword = (EditText) findViewById(R.id.inputPassword);
                try {
                    ConnectionHelper connectionHelper = new ConnectionHelper();
                    connect = connectionHelper.connect();
                    if(connect!=null) {
                        String query = "SELECT * FROM userdb;";
                        Statement statement = connect.createStatement();
                        ResultSet rs = statement.executeQuery(query);

                        while (rs.next()) {
                            if (editTextUsername.getText().toString().equals(rs.getString(2))
                                    && editTextPassword.getText().toString().equals(rs.getString(3))) {

                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                wasFound = true;
                                break;
                            }
                        }

                        //error message
                        if(!wasFound) {
                            createMessage("Neispravno korisničko ime ili lozinka.");
                        }

                    } else {
                        ConnectionResult = "Check connection";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
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