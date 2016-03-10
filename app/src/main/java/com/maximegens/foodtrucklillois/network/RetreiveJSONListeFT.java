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
import com.maximegens.foodtrucklillois.utils.GestionJsonAPI;

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
public class RetreiveJSONListeFT extends AsyncTask<Boolean, Integer, FoodTruckApp>{

    private ProgressBar loader;
    private GestionJsonAPI apiJson;
    private ListeFTAdapter listeFTAdapter = null;

    Context ctx;

    public RetreiveJSONListeFT(ListeFTAdapter ListeFTAdapter, Context ctx){
        this.ctx = ctx;
        this.listeFTAdapter = ListeFTAdapter;
        apiJson = new GestionJsonAPI(this.ctx);
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
    protected FoodTruckApp doInBackground(Boolean... param) {

        boolean online = param[0];

        if(online){
            // Recuperation des données en ligne.
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
        }else{
            // Recuperation des données en interne.
            String json = apiJson.loadJSONFromAsset();
            return apiJson.parseJsonToFTApp(json);
        }

    }

    @Override
    protected void onPostExecute(FoodTruckApp foodTruckApp) {

        //TODO refacto pour prendre un compte n'importe qu'elle ville passé en paramétre.
        // Pour l'instant on sélection la ville de Lille à l'index 0.
        List<FoodTruck> lesFts = apiJson.getListeFTByVille(foodTruckApp,0);

        // Mise à jour de la liste dans l'adapter.
        listeFTAdapter.setFTs(lesFts,false);
        Constantes.lesFTs = lesFts;

        // On masque le loader
        loader.setVisibility(View.GONE);
    }
}
