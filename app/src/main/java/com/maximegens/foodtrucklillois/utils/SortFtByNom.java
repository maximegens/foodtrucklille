package com.maximegens.foodtrucklillois.utils;

import com.maximegens.foodtrucklillois.beans.FoodTruck;

import java.util.Comparator;

/**
 * Comparateur de Food trucks les uns par rapport aux autres selon leur nom.
 */
public class SortFtByNom implements Comparator<FoodTruck> {

    /**
     * Compare deux food trucks passé en paramétre
eturn 1 if ft2 should be before ft1
     *  return -1 if ft1 should be before ft2
     *  return 0 otherwise
     */
    public int compare(FoodTruck ft1, FoodTruck ft2) {
        return ft1.getNom().compareToIgnoreCase(ft2.getNom());
    }

}
