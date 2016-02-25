package com.maximegens.foodtrucklillois.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maxime on 25/02/2016.
 */
public class FoodTruckApp {

    private List<Ville> villes = new ArrayList<>();

    public List<Ville> getVilles() {
        return villes;
    }

    public void setVilles(List<Ville> villes) {
        this.villes = villes;
    }
}
