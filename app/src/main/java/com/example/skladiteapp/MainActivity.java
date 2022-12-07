package com.example.skladiteapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

//import com.example.skladiteapp.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    //private AppBarConfiguration appBarConfiguration;
    //private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ViewFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_view);
        }
        //setSupportActionBar(binding.toolbar);

        //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        //appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

       // binding.fab.setOnClickListener(new View.OnClickListener() {
       //     @Override
       //     public void onClick(View view) {
       //         Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
       //                 .setAction("Action", null).show();
       //     }//});
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_view:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ViewFragment()).commit();
                break;
            case R.id.nav_get:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new GetFragment()).commit();
                break;
            case R.id.nav_take:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new TakeFragment()).commit();
                break;
            case R.id.nav_relocate:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new RelocateFragment()).commit();
                break;
            case R.id.nav_logout:
                TextView log = findViewById(R.id.btnLogin);
                log.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    }
                });
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //@Override
    //public boolean onSupportNavigateUp() {
    //    NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
    //    return NavigationUI.navigateUp(navController, appBarConfiguration)
    //            || super.onSupportNavigateUp();
    //}




    ArrayList<String> printedButtons = new ArrayList<>();
    public void GetTextFromSQL(View v) {
        Connection connect;
        String ConnectionResult = "";
        LinearLayout linearLayout = findViewById(R.id.viewFragment);

        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connect = connectionHelper.connect();
            if(connect!=null) {
                String query = "SELECT * FROM itemtype;";
                Statement statement = connect.createStatement();
                ResultSet rs = statement.executeQuery(query);

                while (rs.next()) {
                    if(!printedButtons.contains(rs.getString(2))) {


                        Button newButton = new Button(this);
                        newButton.setText(rs.getString(2));
                        newButton.setTextSize(30);

                        newButton.setLayoutParams(new ViewGroup.LayoutParams(200*getResources().getDisplayMetrics().densityDpi/160,
                                120*getResources().getDisplayMetrics().densityDpi/160));

                        linearLayout.addView(newButton);
                        printedButtons.add(rs.getString(2));
                    }

                }
            } else {
                ConnectionResult="Check connection";
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}