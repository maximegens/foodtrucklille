package com.maximegens.foodtrucklillois.network;

import com.maximegens.foodtrucklillois.beans.FoodTruckApp;

import retrofit.http.GET;

/**
 * Created by Maxime on 25/02/2016.
 */
public interface RetreiveListeFTService {

    @GET("/u/61408511/FoodTrucksApp.json")
    FoodTruckApp getListFT();
}
