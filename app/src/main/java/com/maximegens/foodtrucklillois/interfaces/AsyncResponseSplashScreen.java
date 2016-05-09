package com.maximegens.foodtrucklillois.interfaces;

import com.maximegens.foodtrucklillois.beans.FoodTruck;

import java.util.List;

/**
 * Created by Maxime on 23/04/2016.
 */
public interface AsyncResponseSplashScreen {

    void processFinish(List<FoodTruck> listeFt, boolean isAJour);

}
