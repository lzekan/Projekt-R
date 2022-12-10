package com.example.skladiteapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
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
                EditText editTextUsername = (EditText) findViewById(R.id.inputUsername);
                EditText editTextPassword = (EditText) findViewById(R.id.inputPassword);

                DatabaseHelperSQLite databaseHelperSQLite = new DatabaseHelperSQLite(LoginActivity.this);
                Cursor data = databaseHelperSQLite.getData();
                while(data.moveToNext()) {
                    if (editTextUsername.getText().toString().equals(data.getString(1))
                            && editTextPassword.getText().toString().equals(data.getString(2))) {

                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        wasFound = true;
                        break;
                    }
                }

                //error message
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