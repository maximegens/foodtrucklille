package com.maximegens.foodtrucklillois.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maxime on 25/02/2016.
 */
public class EnseigneFoodTrucks
{
    private String nomEnseigne;
    private String siteWeb;
    private List<FoodTruck> lesFoodTrucks = new ArrayList<>();

    public String getNomEnseigne() {
        return nomEnseigne;
    }

    public void setNomEnseigne(String nomEnseigne) {
        this.nomEnseigne = nomEnseigne;
    }

    public String getSiteWeb() {
        return siteWeb;
    }

    public void setSiteWeb(String siteWeb) {
        this.siteWeb = siteWeb;
    }

    public List<FoodTruck> getLesFoodTrucks() {
        return lesFoodTrucks;
    }

    public void setLesFoodTrucks(List<FoodTruck> lesFoodTrucks) {
        this.lesFoodTrucks = lesFoodTrucks;
    }
}
