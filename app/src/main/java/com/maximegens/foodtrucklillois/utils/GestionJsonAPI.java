package com.maximegens.foodtrucklillois.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.maximegens.foodtrucklillois.beans.FoodTruck;
import com.maximegens.foodtrucklillois.beans.FoodTruckApp;
import com.maximegens.foodtrucklillois.beans.Ville;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe API fournissant des methodes pour traiter le JSON.
 */
public class GestionJsonAPI {

    private final Context ctx;

    public GestionJsonAPI(Context ctx){
        this.ctx = ctx;
    }

    /**
     * Recupere le fichier json depuis les Assets.
     * @return un string contenant le fichier json.
     */
    public String loadJSONFromAsset() {
        String json;
        try {
            InputStream is = ctx.getAssets().open(Constantes.FICHIER_JSON_ASSET);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    /**
     * Renvoi l'objet FoodTrucApp du Json.
     * @param json le fichier json à parser.
     * @return Renvoi l'objet FoodTrucApp du Json.
     */
    public FoodTruckApp parseJsonToFTApp(String json){
        if(json != null){
            return new Gson().fromJson(json, FoodTruckApp.class);
        }else{
            return null;
        }
    }

    /**
     * Donne la liste des Foods Trucks d'une ville.
     * @param app L'objet FoodTruckApp contenant la liste des villes et de leur food trucks.
     * @param position la position de la ville dans l'objet FoodTruckApp.
     * @return Une liste de Food Trucks.
     */
    public List<FoodTruck> getListeFTByVille(FoodTruckApp app, int position){

        List<FoodTruck> lesFoodTrucks = new ArrayList<>();
        Ville ville = null;

        // On recupere la ville passé en paramétre.
        if(app != null && app.getVilles() != null){
            ville = app.getVilles().get(position);
        }

        // On recupere la liste des foods truck de la ville
        if(ville != null){
            for (FoodTruck ft : ville.getLesFoodTrucks()) {
                lesFoodTrucks.add(ft);
            }
        }
        return lesFoodTrucks;
    }
}
