package com.maximegens.foodtrucklillois.utils;

import com.maximegens.foodtrucklillois.beans.FoodTruck;
import com.maximegens.foodtrucklillois.beans.PlageHoraireFoodTruck;
import com.maximegens.foodtrucklillois.beans.PlanningFoodTruck;

import java.util.Comparator;

/**
 * Created by Maxime on 30/03/2016.
 */
public class SortByDistanceFT implements Comparator<FoodTruck> {

    /**
     * Compare les deux objets passé en paramétre.
     * //     return 1 if ft2 should be before ft1
     * //     return -1 if ft1 should be before ft2
     * //     return 0 otherwise
     */
    public int compare(FoodTruck ft1, FoodTruck ft2) {

        if (ft1.getDistanceFromUser() == Constantes.FT_FERMER_DISTANCE && ft2.getDistanceFromUser() != Constantes.FT_FERMER_DISTANCE) {
            return 1;
        } else if (ft2.getDistanceFromUser() == Constantes.FT_FERMER_DISTANCE && ft1.getDistanceFromUser() != Constantes.FT_FERMER_DISTANCE) {
            return -1;
        } else if (ft1.getDistanceFromUser() == ft2.getDistanceFromUser()) {
            return 0;
        } else if (ft1.getDistanceFromUser() < ft2.getDistanceFromUser()) {
            return -1;
        } else {
            return 1;
        }
    }
}
