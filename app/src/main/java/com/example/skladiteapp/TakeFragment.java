package com.example.skladiteapp;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

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

public class TakeFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //continuity check
        this.continuityCheck();

        View view = inflater.inflate(R.layout.fragment_take, container, false);

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

        AutoCompleteTextView actvType = view.findViewById(R.id.autoCompleteTextViewTypeTake);
        ArrayAdapter<String> adapterType = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, itemTypes);
        actvType.setAdapter(adapterType);

        AutoCompleteTextView actvModel = view.findViewById(R.id.autoCompleteTextViewModelTake);
        ArrayAdapter<String> adapterModel = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, allItemModels);
        actvModel.setAdapter(adapterModel);

        ArrayList<String> finalItemTypes = itemTypes;
        actvType.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String itemType = actvType.getText().toString();
                ArrayList<String> itemModels = new ArrayList<>();
                GetFragment getFragment = new GetFragment();

                if (itemType.equals("") || !finalItemTypes.contains(itemType)) {
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

        AutoCompleteTextView actvLocation = view.findViewById(R.id.autoCompleteTextViewLocationTake);
        ArrayAdapter<String> adapterLocation = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, locations);
        actvLocation.setAdapter(adapterLocation);

        TextInputEditText amountTextView = view.findViewById(R.id.addAmountTake);
        Button buttonSaveToBase = (Button) view.findViewById(R.id.buttonAddToBaseTake);
        buttonSaveToBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // umetanje u bazu....
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
        // poruka...
    }

    public void continuityCheck() {
        NavigationBarView navigationBarView = getActivity().findViewById(R.id.bottomNavigationView);

        if (navigationBarView.getSelectedItemId() != R.id.iconIzdajAlt) {
            navigationBarView.setSelectedItemId(R.id.iconIzdajAlt);
        }

    }

}

