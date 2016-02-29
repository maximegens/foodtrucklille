package com.maximegens.foodtrucklillois.beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Maxime on 25/02/2016.
 */
public class PlanningFoodTruck implements Parcelable{

    public static int FLAGS_MIDI = 1;
    public static int FLAGS_SOIR = 2;

    private String nomJour;
    private int numJour;
    private PlageHoraireFoodTruck midi;
    private PlageHoraireFoodTruck soir;

    public String getNomJour() {
        return nomJour;
    }

    public void setNomJour(String nomJour) {
        this.nomJour = nomJour;
    }

    public int getNumJour() {
        return numJour;
    }

    public void setNumJour(int numJour) {
        this.numJour = numJour;
    }

    public PlageHoraireFoodTruck getMidi() {
        return midi;
    }

    public void setMidi(PlageHoraireFoodTruck midi) {
        this.midi = midi;
    }

    public PlageHoraireFoodTruck getSoir() {
        return soir;
    }

    public void setSoir(PlageHoraireFoodTruck soir) {
        this.soir = soir;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nomJour);
        dest.writeInt(numJour);
        dest.writeParcelable(midi,FLAGS_MIDI);
        dest.writeParcelable(soir,FLAGS_SOIR);

    }

    public static final Parcelable.Creator<PlanningFoodTruck> CREATOR = new Parcelable.Creator<PlanningFoodTruck>()
    {
        @Override
        public PlanningFoodTruck createFromParcel(Parcel source)
        {
            return new PlanningFoodTruck(source);
        }

        @Override
        public PlanningFoodTruck[] newArray(int size)
        {
            return new PlanningFoodTruck[size];
        }
    };

    public PlanningFoodTruck(Parcel in) {
        this.nomJour = in.readString();
        this.numJour = in.readInt();
        this.midi = in.readParcelable(PlageHoraireFoodTruck.class.getClassLoader());
        this.soir = in.readParcelable(PlageHoraireFoodTruck.class.getClassLoader());


    }
}
