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
     * o1 > o2 = 1
     * o1 < o2 = -1
     * o1 = o2 = 0
     */
    public int compare(FoodTruck ft1, FoodTruck ft2) {

        return (ft1.getDistanceFromUser() < ft2.getDistanceFromUser() ? -1 : (ft1.getDistanceFromUser() == ft2.getDistanceFromUser() ? 0 : 1));
    }
}
