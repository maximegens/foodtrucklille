package com.maximegens.foodtrucklillois.network;

import com.maximegens.foodtrucklillois.beans.FoodTruckApp;

import retrofit.http.GET;

/**
 * Interface Service pour la recuperation de donnees via RetroFit.
 */
interface RetreiveListeFTService {

    /**
     * Recuperation de la liste des FoodTrucks.
     * @return l'objet contenant toutes les informations sur les foodTrucks.
     */
    @GET("/docs/securesc/ha0ro937gcuc7l7deffksulhg5h7mbp1/fg01s6rii1r37e38o2am404sq5fk29cb/1494864000000/00963818180161148476/*/0B8NgVqQ70DmQMWlxU3VSZTBibkE?e=download")
    FoodTruckApp getListFT();
}
