package com.maximegens.foodtrucklillois.interfaces;

import com.maximegens.foodtrucklillois.beans.FoodTruck;

/**
 * Interface définisant les actions de l'interface de la recyclerView des FT.
 */
public interface RecyclerViewListener {

    /**
     * Methode appelé lors du clique sur un FoodTruck.
     * @param ft La position de l'item cliqué.
     */
    void onClickFT(FoodTruck ft);

}
