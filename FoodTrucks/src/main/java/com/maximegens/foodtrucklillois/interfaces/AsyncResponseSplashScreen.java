package com.maximegens.foodtrucklillois.interfaces;

import com.maximegens.foodtrucklillois.beans.FoodTruck;

import java.util.List;

/**
 * Asynctask de reponse à l'ecran splashscreen lorsque le chargement est terminé.
 */
public interface AsyncResponseSplashScreen {

    void processFinish(List<FoodTruck> listeFt, boolean isAJour);

}
