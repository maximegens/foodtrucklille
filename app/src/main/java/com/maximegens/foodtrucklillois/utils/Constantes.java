package com.maximegens.foodtrucklillois.utils;

import com.maximegens.foodtrucklillois.beans.FoodTruck;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe contenant toutes les constantes de l'application.
 */
public class Constantes {

    public static List<FoodTruck> lesFTs = new ArrayList<>();

    public static void ajouterFT(){
        lesFTs.add(new FoodTruck("El Camion","4 rue barberousse","logo_el_camion"));
        lesFTs.add(new FoodTruck("Chez Greg","58 rue de l'impasse","logo_chez_greg"));
        lesFTs.add(new FoodTruck("La Marmitte Mobile","58 rue de l'impasse","logo_la_marmitte_mobile"));
        lesFTs.add(new FoodTruck("Peko Peko","55 avenue de la République","logo_peko_peko"));
        lesFTs.add(new FoodTruck("Le Comptoir Volant","12 place Sébastopol","logo_le_comptoir_volant"));
        lesFTs.add(new FoodTruck("Bistro Truck","12 place Sébastopol","logo_bistro_truck"));
    }
}
