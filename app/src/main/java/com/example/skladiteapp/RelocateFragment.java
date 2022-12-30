package com.example.skladiteapp;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

import com.google.android.material.textfield.TextInputEditText;

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
        View view =  inflater.inflate(R.layout.fragment_relocate, container, false);

        ArrayList<String> itemTypes = new ArrayList<>();
        ArrayList<String> allItemModels = new ArrayList<>();
        ArrayList<String> locations = new ArrayList<>();

        executeQuery("SELECT * FROM itemtype", itemTypes, 2);
        executeQuery("SELECT * FROM model", allItemModels, 3);
        executeQuery("SELECT * FROM location", locations, 2);

        Collections.sort(itemTypes);
        Collections.sort(allItemModels);
        Collections.sort(locations);

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
                String itemType = spinnerType.getSelectedItem().toString();
                String model = spinnerModel.getSelectedItem().toString();
                String startLoc = spinnerStartLoc.getSelectedItem().toString();
                String endLoc = spinnerEndLoc.getSelectedItem().toString();
                String amount = amountTextView.getText().toString();

                if (amount=="") {
                    createMessage("Molimo unesite količinu.","error");
                }  else {
                    ArrayList<String> queryAmount = new ArrayList<>();
                    executeQuery(" select * from item join itemtype on item.idtype = itemtype.idtype " +
                            "join model on item.idmodel = model.idmodel " +
                            "join location on item.idlocation = location.idlocation " +
                            "where modelname = '" + model + "' " +
                            "and typename='" + itemType + "' " +
                            "and sectorname = '" + startLoc + "' ", queryAmount, 3);

                    if (queryAmount.size() == 0) {
                        createMessage("Unesena je neispravna kombinacija podataka.", "error");
                    } else if (Integer.parseInt(queryAmount.get(0)) < Integer.parseInt(amount)) {
                        createMessage("Unesena je prevelika količina, trenutna količina: " + queryAmount.get(0), "error");
                    } else {
                        int newValue = Integer.parseInt(queryAmount.get(0)) - Integer.parseInt(amount);
                        executeQuery("update item " +
                                "set ammount = " + newValue + " " +
                                "where idtype = (select idtype from itemtype where typename='" + itemType + "')" +
                                "and idmodel = (select idmodel from model where modelname='" + model + "')" +
                                "and idlocation = (select idlocation from location where sectorname='" + startLoc + "')", queryAmount, -1);

                        executeQuery(" select * from item join itemtype on item.idtype = itemtype.idtype " +
                                "join model on item.idmodel = model.idmodel " +
                                "join location on item.idlocation = location.idlocation " +
                                "where modelname = '" + model + "' " +
                                "and typename='" + itemType + "' " +
                                "and sectorname = '" + endLoc + "' ", queryAmount, 3);

                        if(queryAmount.size()>1) {
                            int toUpdateValue = Integer.parseInt(queryAmount.get(1)) + Integer.parseInt(amount);
                            executeQuery("update item " +
                                    "set ammount = " + toUpdateValue + " " +
                                    "where idtype = (select idtype from itemtype where typename='" + itemType + "')" +
                                    "and idmodel = (select idmodel from model where modelname='" + model + "')" +
                                    "and idlocation = (select idlocation from location where sectorname='" + endLoc + "')", queryAmount, -1);
                        } else {
                            executeQuery("INSERT INTO item (iditem,barcode,ammount,idmodel,idlocation,idtype) " +
                                            " VALUES (nextval('seq_iditem'),'barcode'," +amount+
                                            " ,(select idmodel from model where modelname='"+model+"'"+
                                            " ),(select idlocation from location where sectorname='"+endLoc+"'"+
                                            " ),(select idtype from itemtype where typename='"+itemType+"'));", queryAmount, -1);
                        }
                        createMessage("Artikl uspješno premješten.", "success");
                    }

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
}
