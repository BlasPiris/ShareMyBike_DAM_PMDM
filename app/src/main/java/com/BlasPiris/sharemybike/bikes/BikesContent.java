package com.BlasPiris.sharemybike.bikes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


import com.BlasPiris.sharemybike.pojos.Bike;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class BikesContent {

    //List of all the bikes to be listed in the RecyclerView
    public static List<Bike> ITEMS = new ArrayList<Bike>();
    public static String selectedDate;

    //METODO QUE RECOGE LA FECHA DEL CALENDARIO
    public static void setSelectedDate(String selectedDate) {
        BikesContent.selectedDate = selectedDate;
    }

    //METODO QUE CARGA LOS DATOS DEL JSON
    public static void loadBikesFromJSON(Context c) {

        String json = null;
        try {
            InputStream is =
                    c.getAssets().open("bikeList.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

            JSONObject jsonObject = new JSONObject(json);
            JSONArray couchList = jsonObject.getJSONArray("bike_list");
            for (int i = 0; i < couchList.length(); i++) {
                JSONObject jsonCouch = couchList.getJSONObject(i);
                String owner = jsonCouch.getString("owner");
                String description = jsonCouch.getString("description");
                String city = jsonCouch.getString("city");
                String location = jsonCouch.getString("location");
                String email = jsonCouch.getString("email");
                Bitmap photo = null;
                try {
                    photo = BitmapFactory.decodeStream(
                            c.getAssets().open("images/" +
                                    jsonCouch.getString("image")));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ITEMS.add(new Bike(photo, owner, description, city, location, email));
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

    }

}

