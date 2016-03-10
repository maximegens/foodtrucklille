package com.maximegens.foodtrucklillois.network;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.maximegens.foodtrucklillois.adapters.ListeFTAdapter;
import com.maximegens.foodtrucklillois.beans.FoodTruck;
import com.maximegens.foodtrucklillois.beans.FoodTruckApp;
import com.maximegens.foodtrucklillois.beans.Ville;
import com.maximegens.foodtrucklillois.utils.Constantes;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import retrofit.RestAdapter;
import retrofit.RetrofitError;

/**
 * AsyncTask permettant de recuperer le JSON et de le convertir.
 * Utilisation de la librairie RetroFit.
 */
public class RetreiveJSONListeFT extends AsyncTask<Void, Integer, FoodTruckApp>{

    ProgressBar loader;
    private ListeFTAdapter listeFTAdapter = null;

    Context ctx;

    public RetreiveJSONListeFT(ListeFTAdapter ListeFTAdapter,Context ctx){
        this.listeFTAdapter = ListeFTAdapter;
        this.ctx = ctx;
    }

    /**
     * Recupere la progress bar pour l'afficher pendant le téléchargement.
     * @param loader
     */
    public void setProgressBar(ProgressBar loader) {
        this.loader = loader;
    }

    @Override
    protected void onPreExecute(){
        // On affiche le loader.
        loader.setVisibility(View.VISIBLE);
    }

    @Override
    protected FoodTruckApp doInBackground(Void... params) {

        try{
            // Creation du restAdapter avec RetroFit.
            RetreiveListeFTService retreiveListeFTService = new RestAdapter.Builder()
                    .setEndpoint(Constantes.URL_SERVEUR)
                    .build()
                    .create(RetreiveListeFTService.class);

            // Recuperation et conversion du JSON.
            return retreiveListeFTService.getListFT();
        }catch (RetrofitError cause){
            //TODO gestion des erreurs à améliorer - trop sommaire pour l'instant.
            // Gestion des erreurs
            if(cause.getCause() instanceof SocketTimeoutException){
                Log.v(Constantes.ERROR_NETWORK, "Timeout dépassé !");
            }
            else if(cause.getCause() instanceof ConnectException){
                Log.v(Constantes.ERROR_NETWORK,"Pas de connexion !");
            }else{
                Log.v(Constantes.ERROR_NETWORK,"-- Probléme non référencé  --");
                cause.getMessage();
            }
            return null;
        }
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
        Constantes.lesFTs = lesFtsOnline;

        // On masque le loader
        loader.setVisibility(View.GONE);
    }
}
