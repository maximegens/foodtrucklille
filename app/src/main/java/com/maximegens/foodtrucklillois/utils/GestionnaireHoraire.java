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

    private static final String MIDI_DEBUT = "1990-01-01 11:00:00";
    private static final String MIDI_FIN = "1990-01-01 15:00:00";
    private static final String SOIR_DEBUT = "1990-01-01 17:00:00";
    private static final String SOIR_FIN = "1990-01-01 23:59:59";
    private static final String MINUIT = "1990-01-01 00:00:00";

    /**
     * Creation d'un objet Calendar à la date du jour.
     * @return un Calendar a la date du jour.
     */
    public static Calendar createCalendarToday(){
        Calendar calendarToday = Calendar.getInstance();
        calendarToday.setTime(new Date());
        return calendarToday;
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
     * Donne le numero du jour d'aujourd'hui.
     * 1 = lundi; 2 = mardi ...
     * @return l'entier représentant le jour dans la semaine
     */
    public static int getNumeroJourDansLaSemaine() {
        Calendar calendar = createCalendarToday();
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
     * Indique si on est actuellement le midi
     * @return un boolean vrai si on est le midi.
     */
    public static boolean isMidi(){
        Calendar calendarToday = createCalendarToday();
        if(calendarToday != null && createCalendar(MIDI_DEBUT) != null && createCalendar(MIDI_FIN) != null){
            return calendarToday.get(Calendar.HOUR_OF_DAY) >= createCalendar(MIDI_DEBUT).get(Calendar.HOUR_OF_DAY)
                    && calendarToday.get(Calendar.HOUR_OF_DAY) < createCalendar(MIDI_FIN).get(Calendar.HOUR_OF_DAY);
        }else{
            return false;
        }
    }

    /**
     * Indique si on est avant le midi
     * @return un boolean vrai si on est avant le midi.
     */
    public static boolean isBeforeMidi(){
        Calendar calendarToday = createCalendarToday();
        if(calendarToday != null && createCalendar(MINUIT) != null && createCalendar(MIDI_DEBUT) != null){
            return calendarToday.get(Calendar.HOUR_OF_DAY) >= createCalendar(MINUIT).get(Calendar.HOUR_OF_DAY)
                    && calendarToday.get(Calendar.HOUR_OF_DAY) < createCalendar(MIDI_DEBUT).get(Calendar.HOUR_OF_DAY);
        }else{
            return false;
        }
    }

    /**
     * Indique si on est actuellement le soir
     * @return un boolean vrai si on est le soir.
     */
    public static boolean isSoir(){
        Calendar calendarToday = createCalendarToday();
        if(calendarToday != null && createCalendar(SOIR_DEBUT) != null && createCalendar(SOIR_FIN) != null){
            return calendarToday.get(Calendar.HOUR_OF_DAY) >= createCalendar(SOIR_DEBUT).get(Calendar.HOUR_OF_DAY)
                    && calendarToday.get(Calendar.HOUR_OF_DAY) < createCalendar(SOIR_FIN).get(Calendar.HOUR_OF_DAY);
        }else{
            return false;
        }
    }

    /**
     * Indique si on est avant le soir mais apres le midi
     * @return un boolean vrai si on est avant le soir mais apres .
     */
    public static boolean isBeforeSoirButAfterMidi(){
        Calendar calendarToday = createCalendarToday();
        if(calendarToday != null && createCalendar(MIDI_FIN) != null && createCalendar(SOIR_FIN) != null){
            return calendarToday.get(Calendar.HOUR_OF_DAY) >= createCalendar(MIDI_FIN).get(Calendar.HOUR_OF_DAY)
                    && calendarToday.get(Calendar.HOUR_OF_DAY) < createCalendar(SOIR_FIN).get(Calendar.HOUR_OF_DAY);
        }else{
            return false;
        }
    }

    /**
     * Permet de savoir si on se trouve a midi ou avant midi ( si on est entre minuit et 15h)
     * @return vrai si on est avant 15h
     */
    public static boolean isMidiOrBeforeMidi(){
        return isMidi() || isBeforeMidi();
    }

    /**
     * Permet de savoir si on se trouve en soirée ou juste avant ( si on est entre 15h et 23h59:59)
     * @return vrai si on est entre 15h et 23h59.
     */
    public static boolean isSoirOrBeforeSoirButAfterMidi(){
        return isSoir() || isBeforeSoirButAfterMidi();
    }

    /**
     * Indique si le foot truck est actuellement ouvert.
     * @param calToday le calendar du jour.
     * @param calFTouverture le calendar de l'heure d'ouverture du food truck.
     * @param calFTFermeture le calendar de l'heure de fermeture du food truck.
     * @return un boolean indiquant true si le food truc est actuellement ouvert.
     */
    public static boolean isOpenBetween(Calendar calToday, Calendar calFTouverture, Calendar calFTFermeture){
        if(calToday != null &&calFTouverture != null && calFTFermeture != null){
            return calToday.get(Calendar.HOUR_OF_DAY) >= calFTouverture.get(Calendar.HOUR_OF_DAY)
                    && calToday.get(Calendar.HOUR_OF_DAY) < calFTFermeture.get(Calendar.HOUR_OF_DAY);
        }else{
            return false;
        }
    }

    /**
     * Indique si l'heure de la date du jour est avant l'heure de la date passé en paramétre.
     * @param date la date a verifier.
     * @return true si l'heure du jour est avant l'heure de la date.
     */
    public static boolean isTodayBeforeDate(String date){
        Calendar calendarDate = createCalendar(date);
        Calendar today = createCalendarToday();
        return today.get(Calendar.HOUR_OF_DAY) < calendarDate.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * Indique si la date 1 est avant la date 2
     * @param cal1 la date 1
     * @param cal2 la date 2
     * @return vrai la date 1 est avant la date 2
     */
    public static boolean isBefore(Calendar cal1,Calendar cal2){
        if(cal1 != null && cal2 != null){
            if(cal1.get(Calendar.HOUR_OF_DAY) < cal2.get(Calendar.HOUR_OF_DAY)){
                return true;
            }else if(cal1.get(Calendar.HOUR_OF_DAY) == cal2.get(Calendar.HOUR_OF_DAY)){
                return cal1.get(Calendar.MINUTE) < cal2.get(Calendar.MINUTE);
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

}
