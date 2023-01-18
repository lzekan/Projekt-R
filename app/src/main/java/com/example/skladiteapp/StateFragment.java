package com.example.skladiteapp;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationBarView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class StateFragment extends Fragment {

    RecyclerView recyclerView ;
    List<ItemModel> itemModels ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //continuity check
        this.continuityCheck();

        View view = inflater.inflate(R.layout.fragment_state, container, false);

        fakeDataInit();
        //setRecyclerView();
//        ArrayList<String> itemTypes = new ArrayList<>();
//        executeQuery("SELECT * FROM itemtype", itemTypes, 2);
//        Collections.sort(itemTypes);
//        LinearLayout linearInsideScroll = view.findViewById(R.id.linearInsideScroll);
//        for (String itemType : itemTypes) {
//
//            System.out.println("sada sam na " + itemType);
//
//            TextView itemTypeTextView = new TextView(getActivity());
//            itemTypeTextView.setText(itemType);
//            itemTypeTextView.setTextSize(25);
//            itemTypeTextView.setPadding((int) (15 * getResources().getDisplayMetrics().density),
//                    (int) (20 * getResources().getDisplayMetrics().density), 0, 0);
//
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//                    ViewGroup.LayoutParams.WRAP_CONTENT,
//                    ViewGroup.LayoutParams.WRAP_CONTENT);
//            linearInsideScroll.addView(itemTypeTextView, params);
//
//            //mapa tipa "Vile za kopanje" : ["A-1,50", "D-2,70"]
//            Map<String, ArrayList<String>> models = new TreeMap<>();
//            ArrayList<Integer> rows = new ArrayList<>();
//            rows.addAll(Arrays.asList(5, 11, 13));
//            executeQueryMultiple("select * from itemtype join item on itemtype.idtype = item.idtype " +
//                    "join model on item.idmodel = model.idmodel " +
//                    "join location on item.idlocation = location.idlocation " +
//                    "where typename = '" + itemType + "' and ammount > 0", models, rows);
//
//            System.out.println("vraaceno je " + models);
//
//            for (String model : models.keySet()) {
//                TextView modelTextView = new TextView(getActivity());
//                modelTextView.setText(model);
//                modelTextView.setTextSize(20);
//
//                modelTextView.setPadding((int) (35 * getResources().getDisplayMetrics().density), 0, 0, 0);
//                linearInsideScroll.addView(modelTextView, params);
//
//                for (String combinedString : models.get(model)) {
//                    String sector = combinedString.split(",")[0];
//                    String amount = combinedString.split(",")[1];
//
//                    TextView sectorAmountTV = new TextView(getActivity());
//                    sectorAmountTV.setText("Na " + sector + ": " + amount);
//                    sectorAmountTV.setTextSize(15);
//                    sectorAmountTV.setPadding((int) (45 * getResources().getDisplayMetrics().density), 0, 0, 0);
//                    linearInsideScroll.addView(sectorAmountTV, params);
//
//                }
//            }
//        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setRecyclerView();
        super.onViewCreated(view, savedInstanceState);
    }

    private void setRecyclerView() {
        ItemModelAdapter itemModelAdapter = new ItemModelAdapter(itemModels) ;
        recyclerView = getActivity().findViewById(R.id.state_recycler) ;
        recyclerView.setAdapter(itemModelAdapter);
        recyclerView.setHasFixedSize(true);
    }

    private void fakeDataInit() {
        itemModels = new ArrayList<>() ;

        itemModels.add(new ItemModel("Lopate", "Frankfurt lopata", "B1 : 56")) ;
        itemModels.add(new ItemModel("Kacige", "Kaciga  200XC", "D2 : 170")) ;
        itemModels.add(new ItemModel("Materijali", "Cement 1kg", "A2 : 60")) ;
        itemModels.add(new ItemModel("Alati", "Busilica", "D3 : 45")) ;
        itemModels.add(new ItemModel("Sadnice", "Mrkva sjeme 100g", "A1 : 460")) ;
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void executeQueryMultiple (String query, Map<String, ArrayList<String>> modelMap, ArrayList<Integer> rows) {
        Connection connect;
        try {
            DatabaseHelperPostgre databaseHelperPostgre = new DatabaseHelperPostgre();
            connect = databaseHelperPostgre.connect();

            if(connect != null) {
                Statement statement = connect.createStatement();

                ResultSet rs = statement.executeQuery(query);
                while (rs.next()) {

                    if (!modelMap.keySet().contains(rs.getString(rows.get(1)))) {
                        ArrayList<String> listOfSectorAmount = new ArrayList<>();
                        listOfSectorAmount.add(rs.getString(rows.get(2))+","+rs.getString(rows.get(0)));
                        modelMap.put(rs.getString(rows.get(1)), listOfSectorAmount);
                    } else {
                        modelMap.get(rs.getString(rows.get(1))).add(rs.getString(rows.get(2))+","+rs.getString(rows.get(0)));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void continuityCheck() {
        NavigationBarView navigationBarView = getActivity().findViewById(R.id.bottomNavigationView);

        if (navigationBarView.getSelectedItemId() != R.id.iconStanjeAlt) {
            navigationBarView.setSelectedItemId(R.id.iconStanjeAlt);
        }

    }
}
