package com.example.skladiteapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
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
                            }
                        }

                        //ERROR MESSAGE
                        if(!wasFound) {
                            TextView textViewError = (TextView) findViewById(R.id.loginErrorText);
                            textViewError.setTextColor(getResources().getColor(R.color.red));
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