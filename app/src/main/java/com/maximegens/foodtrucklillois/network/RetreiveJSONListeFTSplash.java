package com.maximegens.foodtrucklillois.network;

import android.app.Activity;
import android.content.Context;
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
import com.maximegens.foodtrucklillois.interfaces.AsyncResponseSplashScreen;
import com.maximegens.foodtrucklillois.utils.Constantes;
import com.maximegens.foodtrucklillois.utils.GestionJsonAPI;
import com.maximegens.foodtrucklillois.utils.SortListeFT;
import com.maximegens.foodtrucklillois.utils.Utils;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.Collections;
import java.util.List;

import retrofit.RestAdapter;
import retrofit.RetrofitError;

/**
 * AsyncTask permettant de recuperer le JSON et de le convertir.
 * Utilisation de la librairie RetroFit.
 */
public class RetreiveJSONListeFTSplash extends AsyncTask<Boolean, String, FoodTruckApp>{

    private ProgressBar loader;
    private TextView indicationLoader;
    private GestionJsonAPI apiJson;
    private Activity activity;
    private Context context;
    private AsyncResponseSplashScreen responseAsynctask = null;
    private boolean isAJour;


    /**
     * Constructeur de l'asynstack.
     * @param activity le activity.
     */
    public RetreiveJSONListeFTSplash(Activity activity, ProgressBar loader, TextView indicationLoader){
        this.activity = activity;
        this.context = activity.getApplicationContext();
        this.apiJson = new GestionJsonAPI(this.activity);
        this.loader = loader;
        this.indicationLoader = indicationLoader;
        this.responseAsynctask = (AsyncResponseSplashScreen) this.activity;
    }


    @Override
    protected void onPreExecute(){
        loader.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onProgressUpdate(String... progress) {
        indicationLoader.setText(progress[0]);
    }

    @Override
    protected FoodTruckApp doInBackground(Boolean... param) {

        boolean online = param[0];

        publishProgress(context.getString(R.string.splash_recup_ft));

        if(online){
            // Recuperation des données en ligne.
            try{
                // Creation du restAdapter avec RetroFit.
                RetreiveListeFTService retreiveListeFTService = new RestAdapter.Builder()
                        .setEndpoint(Constantes.URL_SERVEUR)
                        .build()
                        .create(RetreiveListeFTService.class);

                isAJour = true;

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
                return getFoodTruckInterne();
            }
        }else{
            return getFoodTruckInterne();
        }

    }

    FoodTruckApp getFoodTruckInterne() {
        // Recuperation des données en interne.
        isAJour = false;
        String json = apiJson.loadJSONFromAsset();
        return apiJson.parseJsonToFTApp(json);
    }

    @Override
    protected void onPostExecute(FoodTruckApp foodTruckApp) {

        // Pour l'instant on sélection la ville de Lille à l'index 0.
        List<FoodTruck> lesFts = apiJson.getListeFTByVille(foodTruckApp,0);

        // On masque le loader.
        loader.setVisibility(View.INVISIBLE);
        indicationLoader.setText(context.getString(R.string.splash_lancement_ft));

        // Callback vers l'activity pour lui indiquer que le traitement est fini
        if(responseAsynctask != null){
            responseAsynctask.processFinish(lesFts, isAJour);
        }
    }
}
