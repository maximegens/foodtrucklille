package com.maximegens.foodtrucklillois.utils;

import android.content.Context;
import android.content.res.Configuration;

import java.text.DecimalFormat;

/**
 * Created by Maxime on 30/03/2016.
 */
public class Utils {

    /**
     * Convertie la distance de métre en kilométre.
     * @param metre La distance en métre
     * @return float La distance en kilométre.
     */
    public static float metreToKm(float metre){
        String result;
        DecimalFormat df = new DecimalFormat("#####.##");
        result = df.format(metre * 0.001);
        return Float.parseFloat(result.replace(',', '.'));
    }

    /**
     * Retourne le nombre de colonne pour la recyclerView en fonction de l'orientation du téléphone.
     * @return
     */
    public static int getNbColonneForScreen(Context ctx) {
        return ctx.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? 2 : 3;
    }
}
