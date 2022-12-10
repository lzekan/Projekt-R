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


public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        TextView btnRegister = findViewById(R.id.btnRegister);
        TextView btnLogin = findViewById(R.id.jumpToLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean credentialsError = false;
                Connection connect;
                String ConnectionResult = "";


                EditText inputUsername = (EditText) findViewById(R.id.inputUsername);
                EditText inputEmail = (EditText) findViewById(R.id.inputEmail);
                EditText inputPassword = (EditText) findViewById(R.id.inputPassword);

                try {
                    ConnectionHelper connectionHelper = new ConnectionHelper();
                    connect = connectionHelper.connect();
                    if(connect!=null) {
                        String querySelect = "SELECT * FROM userdb;";
                        Statement statement = connect.createStatement();
                        ResultSet rs = statement.executeQuery(querySelect);

                        if (inputPassword.getText().toString().equals("")
                                    || inputUsername.getText().toString().equals("")
                                    || inputEmail.getText().toString().equals("")) {
                                createMessage("Molimo unesite sve podatke.", true);
                                credentialsError = true;
                        } else {
                            while (rs.next()) {

                                if (rs.getString(2).equals(inputUsername.getText().toString()) &&
                                        rs.getString(4).equals(inputEmail.getText().toString())) {
                                    createMessage("Korisničko ime i email se već koriste.", true);
                                    credentialsError = true;
                                }
                                else if (rs.getString(2).equals(inputUsername.getText().toString())) {
                                    createMessage("Korisničko ime se već koristi.", true);
                                    credentialsError = true;
                                }
                                else if (rs.getString(4).equals(inputEmail.getText().toString())) {
                                    createMessage("Email se već koristi.", true);
                                    credentialsError = true;
                                }

                            }
                        }

                        if (!credentialsError) {
                            String queryInsert = "INSERT INTO userdb (iduser,username,password,email) values (" +
                                    "nextval('seq_iduser')" + ", '"
                                    + inputUsername.getText().toString() + "', '"
                                    + inputPassword.getText().toString() +  "', '"
                                    + inputEmail.getText().toString() + "');";
                            statement.executeUpdate(queryInsert);
                            createMessage("Možete se prijaviti sa svojim novim računom.", false);
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

    private void createMessage(String text, boolean isError) {
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
        builder.setCancelable(true);
        builder.setMessage(text);

        if (isError) {
            builder.setTitle("Dogodila se greška!");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //nothing
                }
            });
        } else {
            builder.setTitle("Prijava uspješna!");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                }
            });
        }
        builder.show();
    }
}