package com.maximegens.foodtrucklillois.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maxime on 25/02/2016.
 */
public class Ville {

    private String nom;
    private List<EnseigneFoodTrucks> lesEnseignesFoodTrucks = new ArrayList<>();

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<EnseigneFoodTrucks> getLesEnseignesFoodTrucks() {
        return lesEnseignesFoodTrucks;
    }

    public void setLesEnseignesFoodTrucks(List<EnseigneFoodTrucks> lesEnseignesFoodTrucks) {
        this.lesEnseignesFoodTrucks = lesEnseignesFoodTrucks;
    }
}
