package com.maximegens.foodtrucklillois.network;

import android.app.Activity;
import android.content.Context;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.maximegens.foodtrucklillois.R;
import com.maximegens.foodtrucklillois.adapters.ListeFTAdapter;
import com.maximegens.foodtrucklillois.beans.FoodTruck;
import com.maximegens.foodtrucklillois.beans.FoodTruckApp;
import com.maximegens.foodtrucklillois.beans.Ville;
import com.maximegens.foodtrucklillois.utils.Constantes;
import com.maximegens.foodtrucklillois.utils.GestionJsonAPI;
import com.maximegens.foodtrucklillois.utils.SortListeFT;
import com.maximegens.foodtrucklillois.utils.Utils;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit.RestAdapter;
import retrofit.RetrofitError;

/**
 * AsyncTask permettant de recuperer le JSON et de le convertir.
 * Utilisation de la librairie RetroFit.
 */
public class RetreiveJSONListeFT extends AsyncTask<Boolean, Integer, FoodTruckApp>{

    private ProgressBar loader;
    private TextView indicationChargementFT;
    private TextView indicationListeFTVide;
    private GestionJsonAPI apiJson;
    private boolean swipeRefreshActive;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListeFTAdapter listeFTAdapter = null;
    private Activity activity;

    Context ctx;

    /**
     * Constructeur de l'asynstack.
     * @param ListeFTAdapter l'adapter à mettre à jour.
     * @param activity le activity.
     * @param swipeRefreshActive indique si l'utilisateur à lui meme demander le refresh de la liste des FT avec le pull to refresh.
     */
    public RetreiveJSONListeFT(ListeFTAdapter ListeFTAdapter, Activity activity, boolean swipeRefreshActive, TextView indicationListeFTVide){
        this.ctx = ctx;
        this.listeFTAdapter = ListeFTAdapter;
        this.swipeRefreshActive = swipeRefreshActive;
        this.indicationListeFTVide = indicationListeFTVide;
        this.activity = activity;
        apiJson = new GestionJsonAPI(this.ctx);
    }

    /**
     * Recupere la progress bar pour l'afficher pendant le téléchargement.
     * @param loader le loader.
     */
    public void setProgressBar(ProgressBar loader, TextView indicationChargementFT) {
        this.loader = loader;
        this.indicationChargementFT = indicationChargementFT;
    }

    /**
     * Recupere le swipeRefresh
     * @param swipeRefreshLayout le SwipeRefreshLayout.
     */
    public void setswipeRefresh(SwipeRefreshLayout swipeRefreshLayout) {
        this.swipeRefreshLayout = swipeRefreshLayout;
    }

    @Override
    protected void onPreExecute(){

        // On affiche le loader si il s'agit du chargement automatique.
        if(swipeRefreshActive == false){
            loader.setVisibility(View.VISIBLE);
            indicationChargementFT.setVisibility(View.VISIBLE);
        }
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

        // Affectation et tri des ft récupérés a la varibale global.
        Constantes.lesFTs = lesFts;
        Collections.sort(Constantes.lesFTs, new SortListeFT(false));

        // Mise a jour de la liste avec un affichage classique (true)
        ListeFTAdapter listeFTAdapter = new ListeFTAdapter(Constantes.lesFTs, activity,true);
        RecyclerView recyclerView = (RecyclerView) activity.findViewById(R.id.recycler_view_liste_ft);
        recyclerView.setLayoutManager(new GridLayoutManager(activity, Utils.getNbColonneForScreen(activity)));
        recyclerView.setAdapter(listeFTAdapter);

        // On arrete le swipeRefresh si il a était lancé sinon on masque le loader du début de lancement de l'applicaiton.
        if(swipeRefreshActive){
            swipeRefreshLayout.setRefreshing(false);
        }else{
            // On masque le loader
            loader.setVisibility(View.GONE);
            indicationChargementFT.setVisibility(View.GONE);
        }

        // Si il n'y a pas de food trucks dans la liste alors on affiche un message indiquant que la liste est vide.
        if(foodTruckApp == null || foodTruckApp.getVilles() == null || foodTruckApp.getVilles().isEmpty()){
            indicationListeFTVide.setVisibility(View.VISIBLE);
        }else{
            indicationListeFTVide.setVisibility(View.GONE);
        }
    }
}
