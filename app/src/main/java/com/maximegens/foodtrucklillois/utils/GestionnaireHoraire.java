package com.maximegens.foodtrucklillois.utils;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Classe de gestion des horaires, date et calendrier de l'application.
 */
public class GestionnaireHoraire {

    /**
     * Donne le numero du jour dans la semaine.
     * 1 = lundi; 2 = mardi ...
     * @param calendar le calendar.
     * @return l'entier représentant le jour dans la semaine
     */
    public static int getNumeroJourDansLaSemaine(Calendar calendar) {
        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case GregorianCalendar.MONDAY:
                return 1;
            case GregorianCalendar.TUESDAY:
                return 2;
            case GregorianCalendar.WEDNESDAY:
                return 3;
            case GregorianCalendar.THURSDAY:
                return 4;
            case GregorianCalendar.FRIDAY:
                return 5;
            case GregorianCalendar.SATURDAY:
                return 6;
            case GregorianCalendar.SUNDAY:
                return 7;
            default:
                return 0;
        }
    }

    /**
     * Indique si le foot truck est actuellement ouvert.
     * @param calToday le calendar du jour.
     * @param calFTouverture le calendar de l'heure d'ouverture du food truck.
     * @param calFTFermeture le calendar de l'heure de fermeture du food truck.
     * @return un boolean indiquant true si le food truc est actuellement ouvert.
     */
    public static boolean isOpen(Calendar calToday, Calendar calFTouverture, Calendar calFTFermeture){
        if(calToday != null &&calFTouverture != null && calFTFermeture != null){
            return calToday.get(Calendar.HOUR_OF_DAY) > calFTouverture.get(Calendar.HOUR_OF_DAY)
                    && calToday.get(Calendar.HOUR_OF_DAY) < calFTFermeture.get(Calendar.HOUR_OF_DAY);
        }else{
            return false;
        }
    }

    /**
     * Creation d'un objet Calendar avec le format de date passé en paramétre.
     * @param dateEnString le format de date en string.
     * @return un Calendar avec le format de la date donné.
     */
    public static Calendar createCalendar(String dateEnString){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(Constantes.FORMAT_HORAIRE_FOOD_TRUCK, Locale.FRANCE);
        try {
            calendar.setTime(sdf.parse(dateEnString));
        } catch (ParseException e) {
            Log.v(Constantes.LOG_PARSE_DATE,"Erreur lors du parsing de la date : "+dateEnString);
            e.printStackTrace();
        }
        return calendar;
    }

    /**
     * Creation d'un objet Calendar à la date du jour.
     * @return un Calendar a la date du jour.
     */
    public static Calendar createCalendarToday(){
        Calendar calendarToday = Calendar.getInstance();
        calendarToday.setTime(new Date());
        return calendarToday;
    }
    
}
