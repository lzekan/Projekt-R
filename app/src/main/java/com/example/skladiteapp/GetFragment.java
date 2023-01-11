package com.example.skladiteapp;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputEditText;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;

public class GetFragment extends Fragment{

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //continuity check
        this.continuityCheck();

        View view = inflater.inflate(R.layout.fragment_get, container, false);

        ArrayList<String> itemTypes = new ArrayList<>();
        ArrayList<String> allItemModels = new ArrayList<>();
        ArrayList<String> locations = new ArrayList<>();

        Collections.sort(itemTypes);
        Collections.sort(allItemModels);
        Collections.sort(locations);

        executeQuery("SELECT * FROM itemtype", itemTypes, 2);
        executeQuery("SELECT * FROM model", allItemModels, 3);
        executeQuery("SELECT * FROM location", locations, 2);

        AutoCompleteTextView actvType = view.findViewById(R.id.autoCompleteTextViewTypeGet);
        ArrayAdapter<String> adapterType = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, itemTypes);
        actvType.setAdapter(adapterType);

        AutoCompleteTextView actvModel = view.findViewById(R.id.autoCompleteTextViewModelGet);
        ArrayAdapter<String> adapterModel = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, allItemModels);
        actvModel.setAdapter(adapterModel);

        actvType.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String itemType = actvType.getText().toString();
                ArrayList<String> itemModels = new ArrayList<>();
                GetFragment getFragment = new GetFragment();

                if (itemType.equals("") || !itemTypes.contains(itemType)) {
                    getFragment.executeQuery("SELECT * FROM model", itemModels, 3);
                    adapterModel.clear();
                    adapterModel.addAll(itemModels);
                } else {
                    getFragment.executeQuery("SELECT modelname " +
                            "FROM item join model ON item.idmodel = model.idmodel " +
                            "join itemtype on item.idtype = itemtype.idtype " +
                            "where typename = '" + itemType + "';", itemModels, 1);
                    adapterModel.clear();
                    adapterModel.addAll(itemModels);
                }
                adapterModel.notifyDataSetChanged();
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        AutoCompleteTextView actvLocation = view.findViewById(R.id.autoCompleteTextViewLocationGet);
        ArrayAdapter<String> adapterLocation = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, locations);
        actvLocation.setAdapter(adapterLocation);

        TextInputEditText amountTextView = view.findViewById(R.id.addAmount);
        Button buttonSaveToBase = (Button) view.findViewById(R.id.buttonAddToBase);
        buttonSaveToBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itemType = actvType.getText().toString();
                String model = actvModel.getText().toString();
                String location = actvLocation.getText().toString();
                String amount = amountTextView.getText().toString();
                ArrayList<String> emptyArray = new ArrayList<>();

                if (itemType.equals("") || model.equals("") || location.equals("")) {
                    createMessage("Molimo unesite sve podatke.","error");
                } else {
                    if (!itemTypes.contains(itemType)) {
                        executeQuery("INSERT INTO itemtype (idtype,typename) " +
                                "VALUES (nextval('seq_idtype'),'"+itemType+"');",
                                emptyArray, -1);

                        executeQuery("INSERT INTO item (iditem,barcode,ammount,idmodel,idlocation,idtype) " +
                                        " VALUES (nextval('seq_iditem'),'barcode'," +amount+
                                        " ,(select idmodel from model where modelname='"+model+"'"+
                                        " ),(select idlocation from location where sectorname='"+location+"'"+
                                        " ),(select idtype from itemtype where typename='"+itemType+"'));",
                                emptyArray, -1);
                    }
                    if (!locations.contains(location)) {
                        executeQuery("INSERT INTO location (idlocation,sectorname) " +
                                "VALUES (nextval('seq_idlocation'),'"+location+"');",
                                emptyArray, -1);

                        executeQuery("INSERT INTO item (iditem,barcode,ammount,idmodel,idlocation,idtype) " +
                                        " VALUES (nextval('seq_iditem'),'barcode'," +amount+
                                        " ,(select idmodel from model where modelname='"+model+"'"+
                                        " ),(select idlocation from location where sectorname='"+location+"'"+
                                        " ),(select idtype from itemtype where typename='"+itemType+"'));",
                                emptyArray, -1);
                    }
                    if (!allItemModels.contains(model)) {
                        executeQuery("INSERT INTO model (idmodel,manufacturername,modelname) " +
                                "VALUES (nextval('seq_idmodel'),'SMISLITI MODEL','"+model+"');",
                                emptyArray, -1);

                    }
                    ArrayList<String> amountInDb = new ArrayList<>();
                    executeQuery(" select * from item join itemtype on item.idtype = itemtype.idtype " +
                            "join model on item.idmodel = model.idmodel " +
                            "join location on item.idlocation = location.idlocation " +
                            "where modelname = '" + model + "' " +
                            "and typename='" + itemType + "' " +
                            "and sectorname = '" + location + "' ", amountInDb, 3);
                    if (amountInDb.size() > 0) {
                        int amountToSet = Integer.parseInt(amountInDb.get(0)) + Integer.parseInt(amount);
                        executeQuery("update item " +
                                "set ammount = " + amountToSet + " " +
                                        "where idtype = (select idtype from itemtype where typename='" + itemType + "')" +
                                        "and idmodel = (select idmodel from model where modelname='" + model + "')" +
                                        "and idlocation = (select idlocation from location where sectorname='" + location + "')"
                                , emptyArray, -1);

                    } else {
                        executeQuery("INSERT INTO item (iditem,barcode,ammount,idmodel,idlocation,idtype) " +
                                        " VALUES (nextval('seq_iditem'),'barcode'," +amount+
                                        " ,(select idmodel from model where modelname='"+model+"'"+
                                        " ),(select idlocation from location where sectorname='"+location+"'"+
                                        " ),(select idtype from itemtype where typename='"+itemType+"'));",
                                emptyArray, -1);
                    }


                    createMessage("Artikl uspješno dodan.","success");
                }
            }
        });


        return view;
    }

    public static void executeQuery (String query, ArrayList<String> arrayList, int row) {
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
                        if(!arrayList.contains(rs.getString(row)))
                            arrayList.add(rs.getString(row));
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
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

        if (navigationBarView.getSelectedItemId() != R.id.iconZaprimiAlt) {
            navigationBarView.setSelectedItemId(R.id.iconZaprimiAlt);
        }

    }


}
