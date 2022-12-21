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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.textfield.TextInputEditText;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class GetFragment extends Fragment{


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_get, container, false);

        ArrayList<String> itemTypes = new ArrayList<>();
        executeQuery("SELECT * FROM itemtype", itemTypes, 2);
        AutoCompleteTextView actvType = view.findViewById(R.id.autoCompleteTextViewTypeGet);
        ArrayAdapter<String> adapterType = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, itemTypes);
        actvType.setAdapter(adapterType);

        ArrayList<String> allItemModels = new ArrayList<>();
        executeQuery("SELECT * FROM model", allItemModels, 3);
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

        ArrayList<String> locations = new ArrayList<>();
        executeQuery("SELECT * FROM location", locations, 2);
        AutoCompleteTextView actvLocation = view.findViewById(R.id.autoCompleteTextViewLocationGet);
        ArrayAdapter<String> adapterLocation = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, locations);
        actvLocation.setAdapter(adapterLocation);

        TextInputEditText ammountTextView = view.findViewById(R.id.addAmount);
        Button buttonSaveToBase = (Button) view.findViewById(R.id.buttonAddToBase);
        buttonSaveToBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itemType = actvType.getText().toString();
                String itemModel = actvModel.getText().toString();
                String location = actvLocation.getText().toString();
                String ammount = ammountTextView.getText().toString();
                ArrayList<String> emptyArrey = new ArrayList<>();

                if (itemType.equals("") || itemModel.equals("") || location.equals("")) {
                    createMessage("Molimo unesite sve podatke.","error");
                }
                else {
                    if (!itemTypes.contains(itemType)) {
                        executeQuery("INSERT INTO itemtype (idtype,typename) " +
                                "VALUES (nextval('seq_idtype'),'"+itemType+"');",
                                emptyArrey, -1);

                        executeQuery("INSERT INTO item (iditem,barcode,ammount,idmodel,idlocation,idtype) " +
                                        " VALUES (nextval('seq_iditem'),'barcode'," +ammount+
                                        " ,(select idmodel from model where modelname='"+itemModel+"'"+
                                        " ),(select idlocation from location where sectorname='"+location+"'"+
                                        " ),(select idtype from itemtype where typename='"+itemType+"'));",
                                emptyArrey, -1);
                    }
                    else if (!locations.contains(location)) {
                        executeQuery("INSERT INTO location (idlocation,sectorname) " +
                                "VALUES (nextval('seq_idlocation'),'"+location+"');",
                                emptyArrey, -1);

                        executeQuery("INSERT INTO item (iditem,barcode,ammount,idmodel,idlocation,idtype) " +
                                        " VALUES (nextval('seq_iditem'),'barcode'," +ammount+
                                        " ,(select idmodel from model where modelname='"+itemModel+"'"+
                                        " ),(select idlocation from location where sectorname='"+location+"'"+
                                        " ),(select idtype from itemtype where typename='"+itemType+"'));",
                                emptyArrey, -1);
                    }
                    else if (!allItemModels.contains(itemModel)) {
                        executeQuery("INSERT INTO model (idmodel,manufacturername,modelname) " +
                                "VALUES (nextval('seq_idmodel'),'SMISLITI MODEL','"+itemModel+"');",
                                emptyArrey, -1);

                        executeQuery("INSERT INTO item (iditem,barcode,ammount,idmodel,idlocation,idtype) " +
                                        " VALUES (nextval('seq_iditem'),'barcode'," +ammount+
                                        " ,(select idmodel from model where modelname='"+itemModel+"'"+
                                        " ),(select idlocation from location where sectorname='"+location+"'"+
                                        " ),(select idtype from itemtype where typename='"+itemType+"'));",
                                emptyArrey, -1);

                    } else {
                        executeQuery("UPDATE item i1 " +
                                "SET ammount=(select ammount from item i2 join model on i2.idmodel=model.idmodel where model.modelname='"+itemModel.trim()+"') + "+ammount+
                                " WHERE i1.iditem=(select iditem from item as i2 join model on i2.idmodel = model.idmodel where model.modelname='"+itemModel.trim()+"');",
                                emptyArrey, -1);
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


}
