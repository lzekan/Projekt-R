package com.example.skladiteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaDrm;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
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

                TextView textViewUsernameError = findViewById(R.id.loginErrorText);
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
                        if (inputPassword.getText().toString().equals("") ||
                                inputUsername.getText().toString().equals("") ||
                                inputEmail.getText().toString().equals("")) {
                            if (!textViewUsernameError.getText().toString().equals("")) {
                                textViewUsernameError.setText("Molimo unesite sve podatke!");
                                textViewUsernameError.setTextColor(getResources().getColor(R.color.red));
                                credentialsError = true;
                            }

                        } else {
                            while (rs.next()) {
                                if (rs.getString(2).equals(inputUsername.getText().toString()) &&
                                        rs.getString(4).equals(inputEmail.getText().toString())) {
                                    if (!textViewUsernameError.getText().toString().equals("")) {
                                        textViewUsernameError.setText("Korisničko ime i email se već koriste!");
                                        textViewUsernameError.setTextColor(getResources().getColor(R.color.red));
                                        credentialsError = true;
                                    }
                                }

                                else if (rs.getString(2).equals(inputUsername.getText().toString())) {
                                    //korisnicko ime u uporabi
                                    if (!textViewUsernameError.getText().toString().equals("")) {
                                        textViewUsernameError.setText("Korisničko ime se već koristi!");
                                        textViewUsernameError.setTextColor(getResources().getColor(R.color.red));
                                        credentialsError = true;
                                    }
                                }

                                else if (rs.getString(4).equals(inputEmail.getText().toString())) {
                                    //email u uporabi
                                    if (!textViewUsernameError.getText().toString().equals("")) {
                                        textViewUsernameError.setText("Email se već koristi!");
                                        textViewUsernameError.setTextColor(getResources().getColor(R.color.red));
                                        credentialsError = true;
                                    }
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
                            if (!textViewUsernameError.getText().toString().equals("")) {
                                textViewUsernameError.setText("Uspješna registracija!");
                                textViewUsernameError.setTextColor(getResources().getColor(R.color.green));
                            }
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
}