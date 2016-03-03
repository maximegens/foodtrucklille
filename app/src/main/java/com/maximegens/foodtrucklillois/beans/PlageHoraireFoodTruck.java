package com.maximegens.foodtrucklillois.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
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
}
