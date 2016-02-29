package com.maximegens.foodtrucklillois.network;

import android.os.AsyncTask;

import com.maximegens.foodtrucklillois.adapters.ListeFTAdapter;
import com.maximegens.foodtrucklillois.beans.FoodTruck;
import com.maximegens.foodtrucklillois.beans.FoodTruckApp;
import com.maximegens.foodtrucklillois.beans.Ville;
import com.maximegens.foodtrucklillois.utils.Constantes;

import java.util.ArrayList;
import java.util.List;

import retrofit.RestAdapter;

/**
 * AsyncTask permettant de recuperer le JSON et de le convertir.
 * Utilisation de la librairie RetroFit.
 */
public class RetreiveJSONListeFT extends AsyncTask<Void, Integer, FoodTruckApp>{

    ListeFTAdapter listeFTAdapter = null;

    public RetreiveJSONListeFT(ListeFTAdapter ListeFTAdapter){
        this.listeFTAdapter = ListeFTAdapter;
    }

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
        List<FoodTruck> lesFtsOnline = new ArrayList<>();
        Ville ville = null;
        // Pour le moment on prends la seule ville disponible "Lille"
        if(foodTruckApp != null && foodTruckApp.getVilles() != null){
            ville = foodTruckApp.getVilles().get(0);
        }

        // On recupere la liste des foods truck de la ville
        if(ville != null){
            for (FoodTruck ft : ville.getLesFoodTrucks()) {
                lesFtsOnline.add(ft);
            }
        }
        // Mise à jour de la liste dans l'adapter.
        listeFTAdapter.setFTs(lesFtsOnline,false);
    }
}
