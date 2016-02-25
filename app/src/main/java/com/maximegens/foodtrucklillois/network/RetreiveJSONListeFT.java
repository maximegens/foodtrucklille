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

import retrofit.RestAdapter;

/**
 * Created by Maxime on 25/02/2016.
 */
public class RetreiveJSONListeFT extends AsyncTask<Void, Integer, FoodTruckApp>{

    @Override
    protected FoodTruckApp doInBackground(Void... params) {

        // Creation du restAdapter avec RetroFit.
        RetreiveListeFTService retreiveListeFTService = new RestAdapter.Builder()
                .setEndpoint(Constantes.URL_SERVEUR)
                .build()
                .create(RetreiveListeFTService.class);

        // Recuperation et conversion du JSON.
        FoodTruckApp foodTruckApp = retreiveListeFTService.getListFT();
        return foodTruckApp;
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
    }

    @Override
    protected void onPostExecute(FoodTruckApp foodTruckApp) {

    }
}
