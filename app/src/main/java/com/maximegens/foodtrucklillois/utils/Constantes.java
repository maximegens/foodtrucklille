package com.maximegens.foodtrucklillois.utils;

import com.maximegens.foodtrucklillois.beans.FoodTruck;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe contenant toutes les constantes de l'application.
 */
public class Constantes {

    public static List<FoodTruck> lesFTs = new ArrayList<>();
    public static final String URL_SERVEUR = "https://dl.dropboxusercontent.com";
    public static final String PHOTO_NOT_AVAILABLE = "photonotavailable";
    public static final String FICHIER_JSON_ASSET = "FoodTrucksApp.json";
    public static final String FAVORITE_SHAREPREFERENCE = "FAVORITE";
    public static final String RESTORE_FRAGMENT = "restoreFragment";

    public static final String FORMAT_HORAIRE_FOOD_TRUCK = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_DECIMAL_MINUTE = "%02d";
    public static final String RETOUR_CHARIOT = "\n";
    private static final String TABULATION = "\t";
    public static final String TABULATION_DOUBLE = TABULATION +TABULATION;

    public static final String LOG_PARSE_DATE = "ERREUR PARSING DATE";
    public static final String ERROR_NETWORK = "Erreur Internet";

    public static final long TIME_BETWEEN_UPDATE_GPS = 5000;

    public static final String GPS_CENTRE_CARTE_MARC_BAROEUL_LATITUDE = "50.659660";
    public static final String GPS_CENTRE_CARTE_MARC_BAROEUL_LONGITUDE = "3.113962";

    public static final String MIDI = "midi";
    public static final String SOIR = "soir";
    public static final String FERMER = "Fermé";
    public static final String KM = "km";
    public static final String TOUS_FT = "Tous les food trucks";
    public static final String ET = " et ";
    public static final float FT_FERMER_DISTANCE = 0;


}
