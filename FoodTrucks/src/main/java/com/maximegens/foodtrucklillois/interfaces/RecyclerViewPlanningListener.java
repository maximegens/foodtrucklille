package com.maximegens.foodtrucklillois.interfaces;

import com.maximegens.foodtrucklillois.beans.FoodTruck;

/**
 * Interface définisant les actions de l'interface de la recyclerView deu planning des food truck.
 */
public interface RecyclerViewPlanningListener {


    /**
     * Methode appelé lors du clique sur un Food Truck du planning
     * @param  foodtruck le food truck sélectionne.
     */
    void onClickFtPlanning(FoodTruck foodtruck);

}
