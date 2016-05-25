package com.maximegens.foodtrucklillois.beans;

import java.util.List;

/**
 * Bean représentant une ville contenant un ensemble de Food Truck.
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
