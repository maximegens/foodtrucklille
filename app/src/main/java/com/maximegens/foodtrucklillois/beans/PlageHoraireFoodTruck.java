package com.maximegens.foodtrucklillois.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maxime on 25/02/2016.
 */
public class PlageHoraireFoodTruck implements Parcelable{

    private String horaire;
    private List<AdresseFoodTruck> adresses;

    public String getHoraire() {
        return horaire;
    }

    public void setHoraire(String horaire) {
        this.horaire = horaire;
    }

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
        dest.writeString(horaire);
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
        this.horaire = in.readString();
        this.adresses = new ArrayList<AdresseFoodTruck>();
        in.readTypedList(adresses,AdresseFoodTruck.CREATOR);

    }
}
