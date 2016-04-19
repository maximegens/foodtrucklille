package com.maximegens.foodtrucklillois.utils;

import com.maximegens.foodtrucklillois.beans.FoodTruck;

import java.util.Comparator;

/**
 * Comparateur de Food trucks les uns par rapport aux autres.
 */
public class SortListeFT implements Comparator<FoodTruck> {

    boolean gpsActive;

    /**
     * Constructeur
     * @param gpsActive boolean indiquant si le gps est activé.
     */
    public SortListeFT(boolean gpsActive){
        this.gpsActive = gpsActive;
    }

    /**
     * Compare deux food trucks passé en paramétre
     * Le classement est le suivant :
     *
     *  Avec GPS activé :
     *      Ouvert
     *      Distance
     *  Sans le GPS :
     *      Ouvert
     *      Ordre Alphabétique
     *
     *  return 1 if ft2 should be before ft1
     *  return -1 if ft1 should be before ft2
     *  return 0 otherwise
     */
    public int compare(FoodTruck ft1, FoodTruck ft2) {

        // On verifie d'abord si les ft sont ouvert
        if(ft1.isOpenNow() && ft2.isOpenNow()){

            if(gpsActive){
                    return compareDistance(ft1,ft2);
            }else{
                // On compare par nom
                return ft1.getNom().compareToIgnoreCase(ft2.getNom());
            }

        }else if(ft1.isOpenNow() && !ft2.isOpenNow()){
            return -1;
        }else if(!ft1.isOpenNow() && ft2.isOpenNow()){
            return 1;
        }else{
            // les deux food truc sont fermé.
            boolean ft1OpenToday = ft1.isDateBeforeLastHoraireFermeture(GestionnaireHoraire.createCalendarToday());
            boolean ft2OpenToday = ft2.isDateBeforeLastHoraireFermeture(GestionnaireHoraire.createCalendarToday());
            if(ft1OpenToday && ft2OpenToday){
                return compareDistance(ft1,ft2);
            }else if(ft1OpenToday && !ft2OpenToday){
                return -1;
            }else if(!ft1OpenToday && ft2OpenToday){
                return 1;
            }else{
                return 0;
            }
        }
    }

    public int compareDistance(FoodTruck ft1, FoodTruck ft2){
        // On compare la distance.
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
