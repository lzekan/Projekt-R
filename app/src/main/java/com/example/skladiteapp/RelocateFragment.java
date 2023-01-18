package com.example.skladiteapp;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RelocateFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //continuity check
        this.continuityCheck();

        View view =  inflater.inflate(R.layout.fragment_relocate, container, false);

        ArrayList<String> itemTypes = null;
        ArrayList<String> allItemModels = null;
        ArrayList<String> locations = null;

        try {
            itemTypes = ConnectionHelper.getJSON("http://192.168.62.166:8080/api/get/all/itemtype", "typeName");
            allItemModels = ConnectionHelper.getJSON("http://192.168.62.166:8080/api/get/all/model", "modelName");
            locations = ConnectionHelper.getJSON("http://192.168.62.166:8080/api/get/all/location", "sectorName");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Spinner spinnerType = view.findViewById(R.id.spinnerTip);
        ArrayAdapter<String> adapterTip = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, itemTypes);
        spinnerType.setAdapter(adapterTip);

        Spinner spinnerModel = view.findViewById(R.id.spinnerModel);
        ArrayAdapter<String> adapterModel = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, allItemModels);
        spinnerModel.setAdapter(adapterModel);

        Spinner spinnerStartLoc = view.findViewById(R.id.spinnerStartLoc);
        ArrayAdapter<String> adapterStartLoc = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, locations);
        spinnerStartLoc.setAdapter(adapterStartLoc);

        Spinner spinnerEndLoc = view.findViewById(R.id.spinnerEndLoc);
        ArrayAdapter<String> adapterEndLoc = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, locations);
        spinnerEndLoc.setAdapter(adapterEndLoc);

        TextInputEditText amountTextView = view.findViewById(R.id.relocateAmount);
        Button buttonSaveToBase = (Button) view.findViewById(R.id.buttonRelocate);
        buttonSaveToBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itemType = String.valueOf(spinnerType.getSelectedItem());
                String model = String.valueOf(spinnerModel.getSelectedItem());
                String startLoc = String.valueOf(spinnerStartLoc.getSelectedItem());
                String endLoc = String.valueOf(spinnerEndLoc.getSelectedItem());
                String amount = String.valueOf(amountTextView.getText());


                if (itemType == "" || model == "" || startLoc == "" || endLoc == "" || amount == "") {
                    createMessage("Molimo unesite sve potrebne podatke.","error");
                } else {
                    JSONObject json = new JSONObject();
                    try{
                        json.put("itemType", itemType);
                        json.put("model", model);
                        json.put("locationFrom", startLoc);
                        json.put("locationTo", endLoc);
                        json.put("amount", amount);
                        json.put("barcode", "");
                    } catch(Exception e){
                        createMessage("Pogreska u unosu.","error");
                    }

                    String msg = ConnectionHelper.postJSON("http://192.168.62.166:8080/api/relocate/item", json);
                    createMessage(msg, (msg.charAt(0) == 'A' ? "success" : "error"));
                }
            }
        });

        return view;
    }

    public void executeQuery(String query, ArrayList<String> arrayList, int row) {
        Connection connect;
        try {
            DatabaseHelperPostgre databaseHelperPostgre = new DatabaseHelperPostgre();
            connect = databaseHelperPostgre.connect();

            if(connect != null) {
                Statement statement = connect.createStatement();

                if (row == -1) {
                    statement.executeUpdate(query);
                } else {
                    ResultSet rs = statement.executeQuery(query);
                    while (rs.next()) {
                        arrayList.add(rs.getString(row));
                    }
                }

            }
        } catch (SQLException e) {
            createMessage("Pokušajte ponovno!", "error");
        }
    }

    private void createMessage(String text, String type) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCancelable(true);
        builder.setMessage(text);

        if (type.equals("error")) {
            builder.setTitle("Dogodila se greška!");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //nothing
                }
            });
        } else if (type.equals("success")) {
            builder.setTitle("Uspjeh!");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //nothing
                }
            });
        }

        builder.show();
    }

    public void continuityCheck() {
        NavigationBarView navigationBarView = getActivity().findViewById(R.id.bottomNavigationView);

        if (navigationBarView.getSelectedItemId() != R.id.iconPremjestiAlt) {
            navigationBarView.setSelectedItemId(R.id.iconPremjestiAlt);
        }

    }
}
