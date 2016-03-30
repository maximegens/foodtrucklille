package com.maximegens.foodtrucklillois.utils;

import com.google.android.gms.maps.model.LatLng;
import com.maximegens.foodtrucklillois.beans.FoodTruck;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe contenant toutes les constantes de l'application.
 */
public class Constantes {

    public static List<FoodTruck> lesFTs = new ArrayList<>();
    public static String URL_SERVEUR = "https://dl.dropboxusercontent.com";
    public static String URL_SERVEUR_FT = "https://dl.dropboxusercontent.com/u/61408511/FoodTrucksApp.json";
    public static String PHOTO_NOT_AVAILABLE = "photonotavailable";
    public static String FICHIER_JSON_ASSET = "FoodTrucksApp.json";
    public static String FAVORITE_SHAREPREFERENCE = "FAVORITE";

    public static String FORMAT_HORAIRE_FOOD_TRUCK = "yyyy-MM-dd HH:mm:ss";
    public static String FORMAT_DECIMAL_MINUTE = "%02d";
    public static String RETOUR_CHARIOT = "\n";
    public static String TABULATION = "\t";
    public static String TABULATION_DOUBLE = TABULATION +TABULATION;

    public static String LOG_PARSE_DATE = "ERREUR PARSING DATE";

    public static String ERROR_NETWORK = "Erreur Internet";

    public static String GPS_LILLE_LATITUDE = "50.62925";
    public static String GPS_LILLE_LONGITUDE = "3.057256000000052";

    public static String GPS_CENTRE_CARTE_MARC_BAROEUL_LATITUDE = "50.659660";
    public static String GPS_CENTRE_CARTE_MARC_BAROEUL_LONGITUDE = "3.113962";

    public static String MIDI = "midi";
    public static String SOIR = "soir";
    public static String FERMER = "Fermé";

}
