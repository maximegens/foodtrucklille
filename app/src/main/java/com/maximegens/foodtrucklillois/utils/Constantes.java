package com.maximegens.foodtrucklillois.utils;

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

    public static String FORMAT_HORAIRE_FOOD_TRUCK = "yyyy-MM-dd HH:mm:ss";
    public static String FORMAT_DECIMAL_MINUTE = "%02d";
    public static String RETOUR_CHARIOT = "\n";
    public static String TABULATION = "\t";
    public static String TABULATION_DOUBLE = TABULATION +TABULATION;

    public static String LOG_PARSE_DATE = "ERREUR PARSING DATE";

    public static String ERROR_NETWORK = "Erreur Internet";

}
