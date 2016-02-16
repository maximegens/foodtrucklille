package com.maximegens.foodtrucklillois.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * Bean représantant un Food Truck.
 */
public class FoodTruck {
    private String nom;
    private String adresse;
    private String logo;

    /**
     * Construceur.
     * @param nom Le nom.
     * @param adresse L'adresse.
     * @param logo Le nom de l'image en interne représentant le logo.
     */
    public FoodTruck(String nom, String adresse, String logo) {
        this.nom = nom;
        this.adresse = adresse;
        this.logo = logo;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    /**
     * Methode de tri de la liste des Foods Trucks en fonction de la recherche de l'utilisateur.
     * @param lesFTs La liste des FTS à triée.
     * @param recherche La recherche de l'utilisateur.
     * @return La liste des Foods Trucks Triée.
     */
    public static List<FoodTruck> filterListeFTs(List<FoodTruck> lesFTs, String recherche) {
        recherche = recherche.toLowerCase();

        final List<FoodTruck> filteredFTList = new ArrayList<>();
        for (FoodTruck model : lesFTs) {
            final String text = model.getNom().toLowerCase();
            if (text.contains(recherche)) {
                filteredFTList.add(model);
            }
        }
        return filteredFTList;
    }
}
