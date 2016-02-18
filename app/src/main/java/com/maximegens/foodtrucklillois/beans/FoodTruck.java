package com.maximegens.foodtrucklillois.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Bean représantant un Food Truck.
 */
public class FoodTruck implements Parcelable{

    public static String KEY_FOOD_TRUCK = "foodtruck";
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nom);
        dest.writeString(adresse);
        dest.writeString(logo);
    }

    public static final Parcelable.Creator<FoodTruck> CREATOR = new Parcelable.Creator<FoodTruck>()
    {
        @Override
        public FoodTruck createFromParcel(Parcel source)
        {
            return new FoodTruck(source);
        }

        @Override
        public FoodTruck[] newArray(int size)
        {
            return new FoodTruck[size];
        }
    };

    public FoodTruck(Parcel in) {
        this.nom = in.readString();
        this.adresse = in.readString();
        this.logo = in.readString();
    }
}
