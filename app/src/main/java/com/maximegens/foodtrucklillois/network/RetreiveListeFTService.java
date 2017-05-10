package com.maximegens.foodtrucklillois.network;

import com.maximegens.foodtrucklillois.beans.FoodTruckApp;

import retrofit.http.GET;

/**
 * Interface Service pour la recuperation de données via RetroFit.
 */
interface RetreiveListeFTService {

    /**
     * Recuperation de la liste des FoodTrucks.
     * @return l'objet contenant toutes les informations sur les foodTrucks.
     */
    @GET("/docs/securesc/ha0ro937gcuc7l7deffksulhg5h7mbp1/6ljoo2h0plenc6hd764f4nttival0ihu/1494403200000/00153049752493752133/*/0B0WKDOwC2VM-Mkh3UXVQZXFxYVk?e=download")
    FoodTruckApp getListFT();
}
