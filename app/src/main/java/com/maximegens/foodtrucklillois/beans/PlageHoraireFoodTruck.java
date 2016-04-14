package com.maximegens.foodtrucklillois.beans;

import android.os.Parcel;
import android.os.Parcelable;

import com.maximegens.foodtrucklillois.utils.Constantes;
import com.maximegens.foodtrucklillois.utils.GestionnaireHoraire;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Maxime on 25/02/2016.
 */
public class PlageHoraireFoodTruck implements Parcelable{

    private String horaireOuverture;
    private String horaireFermeture;
    private List<AdresseFoodTruck> adresses;


    public List<AdresseFoodTruck> getAdresses() {
        return adresses;
    }

    public void setAdresses(List<AdresseFoodTruck> adresses) {
        this.adresses = adresses;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(horaireOuverture);
        dest.writeString(horaireFermeture);
        dest.writeTypedList(adresses);

    }

    public static final Parcelable.Creator<PlageHoraireFoodTruck> CREATOR = new Parcelable.Creator<PlageHoraireFoodTruck>()
    {
        @Override
        public PlageHoraireFoodTruck createFromParcel(Parcel source)
        {
            return new PlageHoraireFoodTruck(source);
        }

        @Override
        public PlageHoraireFoodTruck[] newArray(int size)
        {
            return new PlageHoraireFoodTruck[size];
        }
    };

    public PlageHoraireFoodTruck(Parcel in) {
        this.horaireOuverture = in.readString();
        this.horaireFermeture = in.readString();
        this.adresses = new ArrayList<AdresseFoodTruck>();
        in.readTypedList(adresses,AdresseFoodTruck.CREATOR);

    }

    public String getHoraireOuverture() {
        return horaireOuverture;
    }

    public void setHoraireOuverture(String horaireOuverture) {
        this.horaireOuverture = horaireOuverture;
    }

    public String getHoraireFermeture() {
        return horaireFermeture;
    }

    public void setHoraireFermeture(String horaireFermeture) {
        this.horaireFermeture = horaireFermeture;
    }

    /**
     * Donne l'heure d'ouverture en string formatté.
     * @return l'heure en string (ex : 12h00).
     */
    public String getHeureOuvertureEnString() {
        Calendar calendarOuverture = GestionnaireHoraire.createCalendar(getHoraireOuverture());
        String heure = String.valueOf(calendarOuverture.get(Calendar.HOUR_OF_DAY));
        String minute = String.format(Constantes.FORMAT_DECIMAL_MINUTE,calendarOuverture.get(Calendar.MINUTE));
        return minute.equals("00") ? heure + "h" : heure + "h"+ minute;
    }

    /**
     * Donne l'heure de fermeture en string formatté.
     * @return l'heure en string (ex : 14h30).
     */
    public String getHeureFermetureEnString() {
        Calendar calendarFermeture = GestionnaireHoraire.createCalendar(getHoraireFermeture());
        String heure = String.valueOf(calendarFermeture.get(Calendar.HOUR_OF_DAY));
        String minute = String.format(Constantes.FORMAT_DECIMAL_MINUTE, calendarFermeture.get(Calendar.MINUTE));
        return minute.equals("00") ? heure + "h" : heure + "h"+ minute;
    }

}
