package com.maximegens.foodtrucklillois.network;

import com.maximegens.foodtrucklillois.beans.FoodTruckApp;

import retrofit.http.GET;

/**
 * Interface Service pour la recuperation de données via RetroFit.
 */
public interface RetreiveListeFTService {

    /**
     * Recuperation de la liste des FoodTrucks.
     * @return l'objet contenant toutes les informations sur les foodTrucks.
     */
    @GET("/u/61408511/FoodTrucksApp.json")
    FoodTruckApp getListFT();
}
