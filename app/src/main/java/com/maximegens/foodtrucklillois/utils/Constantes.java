package com.maximegens.foodtrucklillois.utils;

import com.maximegens.foodtrucklillois.beans.FoodTruck;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe contenant toutes les constantes de l'application.
 */
public class Constantes {

    public static List<FoodTruck> lesFTs = new ArrayList<>();
    public static String URL_SERVEUR_FT = "https://dl.dropboxusercontent.com";

    public static void ajouterFT(){
        lesFTs.add(new FoodTruck("El Camion","logo_el_camion"));
        lesFTs.add(new FoodTruck("Chez Greg","logo_chez_greg"));
        lesFTs.add(new FoodTruck("La Marmitte Mobile","logo_la_marmitte_mobile"));
        lesFTs.add(new FoodTruck("Peko Peko","logo_peko_peko"));
        lesFTs.add(new FoodTruck("Le Comptoir Volant","logo_le_comptoir_volant"));
        lesFTs.add(new FoodTruck("Bistro Truck","logo_bistro_truck"));
    }
}
