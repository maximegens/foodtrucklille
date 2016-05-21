package com.maximegens.foodtrucklillois.beans;

import java.util.List;

/**
 * Created by Maxime on 25/02/2016.
 */
public class Ville {

    private String nom;
    private List<FoodTruck> lesFoodTrucks;

    public String getNom() {
        return nom;
    }

    public List<FoodTruck> getLesFoodTrucks() {
        return lesFoodTrucks;
    }

}
