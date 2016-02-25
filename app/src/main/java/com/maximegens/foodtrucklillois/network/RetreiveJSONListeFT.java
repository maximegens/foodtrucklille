package com.maximegens.foodtrucklillois.network;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.maximegens.foodtrucklillois.beans.FoodTruck;
import com.maximegens.foodtrucklillois.beans.FoodTruckApp;
import com.maximegens.foodtrucklillois.utils.Constantes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Maxime on 25/02/2016.
 */
public class RetreiveJSONListeFT extends AsyncTask<Void, Integer, FoodTruckApp>{

    @Override
    protected FoodTruckApp doInBackground(Void... params) {

        BufferedReader rd  = null;
        StringBuilder sb = null;
        String line = null;
        FoodTruckApp foodTruckApp = null;
        String json = null;
        try {
            URL url = new URL(Constantes.URL_SERVEUR_FT);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            rd  = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            sb = new StringBuilder();

            while ((line = rd.readLine()) != null)
            {
                sb.append(line + '\n');
            }

            if(sb != null){
                json = sb.toString();
            }
            if(json != null && !json.isEmpty()){
                // Conversion du json en Objet
                Gson gson = new Gson();
                foodTruckApp = gson.fromJson(json,FoodTruckApp.class);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return foodTruckApp;
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
    }

    @Override
    protected void onPostExecute(FoodTruckApp foodTruckApp) {

    }
}
