package com.example.skladiteapp;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;;

public class ConnectionHelper {
    URL url;
    String method;

    public ConnectionHelper(URL url){
        this.url = url;
    }

    public static ArrayList<String> getJSON(String url, String column) throws InterruptedException {
        ArrayList<String> result = new ArrayList<>();

        Thread thread = new Thread(() -> {
            try{
                URL cUrl = new URL(url);
                HttpURLConnection http = (HttpURLConnection) cUrl.openConnection();
                http.setRequestMethod("GET");
                http.connect();


                try(BufferedReader br = new BufferedReader(new InputStreamReader(cUrl.openStream(), StandardCharsets.UTF_8))) {
                    String str = br.readLine();
                    String[] arr;
                    if (str != null) {
                        str = str.substring(1, str.length() - 1);
                        arr = str.split(",");
                        for(int i = 0; i < arr.length; i++){
                            if(i % 2 != 0){
                                String json = arr[i-1] + ',' + arr[i];
                                JSONObject obj = new JSONObject(json);
                                result.add(String.valueOf(obj.get(column)));
                            }
                        }
                    }
                }


                http.disconnect();
            }
            catch(Exception e){

            }
        });

        thread.run();
        return result;
    }

    public static String postJSON(String url, JSONObject obj){
        String message = null;
        Thread thread = new Thread(() -> {
            try{
                URL cUrl = new URL(url);
                HttpURLConnection http = (HttpURLConnection) cUrl.openConnection();
                http.setRequestMethod("POST");
                http.setDoOutput(true);
                http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                http.setUseCaches(false);
                http.connect();

                try (DataOutputStream dos = new DataOutputStream(http.getOutputStream())) {
                    dos.writeBytes(String.valueOf(obj));
                }


                try (BufferedReader br = new BufferedReader(new InputStreamReader(
                        http.getInputStream())))
                {
                    String line;
                    while ((line = br.readLine()) != null) {
                        System.out.println(line);
                    }
                }

                http.disconnect();


            } catch (Exception e){

            }
        });

        thread.run();

        return message;
    }


}
