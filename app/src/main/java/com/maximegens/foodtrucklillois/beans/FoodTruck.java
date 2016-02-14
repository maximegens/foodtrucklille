package com.maximegens.foodtrucklillois.beans;

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
}
