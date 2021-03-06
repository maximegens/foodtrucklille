package com.maximegens.foodtrucklillois.beans;

import android.os.Parcel;
import android.os.Parcelable;

import com.maximegens.foodtrucklillois.utils.Constantes;

/**
 * Bean représentant le planning du Food Truck.
 */
public class PlanningFoodTruck implements Parcelable{

    private static final int FLAGS_MIDI = 1;
    private static final int FLAGS_SOIR = 2;

    private String nomJour;
    private int numJour;
    private PlageHoraireFoodTruck midi;

    public int getNumJour() {
        return numJour;
    }

    private PlageHoraireFoodTruck soir;

    public String getNomJour() {
        return nomJour;
    }

    public PlageHoraireFoodTruck getMidi() {
        return midi;
    }

    public PlageHoraireFoodTruck getSoir() {
        return soir;
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

    private PlanningFoodTruck(Parcel in) {
        this.nomJour = in.readString();
        this.numJour = in.readInt();
        this.midi = in.readParcelable(PlageHoraireFoodTruck.class.getClassLoader());
        this.soir = in.readParcelable(PlageHoraireFoodTruck.class.getClassLoader());

    }

    /**
     * Indique si le food truck est fermé sur la journée.
     * @return true si le food truck est fermé.
     */
    public boolean isFermerToday(){
        return getMidi() == null && getSoir() == null || (!isOpenMidi() && !isOpenSoir());
    }

    /**
     * Indique si le Food Truck est ouvert le midi.
     * @return true si il est ouvert le midi.
     */
    public boolean isOpenMidi(){
        return getMidi() != null && getMidi().getHoraireOuverture() != null && getMidi().getHoraireFermeture() != null;
    }

    /**
     * Indique si le Food Truck est ouvert le soir.
     * @return true si il est ouvert le soir.
     */
    public boolean isOpenSoir(){
        return getSoir() != null && getSoir().getHoraireOuverture() != null && getSoir().getHoraireFermeture() != null;
    }

    /**
     * Retourne la tranche horaire d'ouverture/fermeture du Food truck.
     * @return la tranche horaire.
     */
    public String getTrancheHorairePlanning(){
        StringBuilder trancheHoraire = new StringBuilder();
        String horaireOuvertureMidi;
        String horaireFermetureSoir;

        if (getMidi() != null) {
            horaireOuvertureMidi = getMidi().getHeureOuvertureEnString() + " - " + getMidi().getHeureFermetureEnString();
            trancheHoraire.append(horaireOuvertureMidi);
        }
        if (getSoir() != null) {
            horaireFermetureSoir = getSoir().getHeureOuvertureEnString() + " - " + getSoir().getHeureFermetureEnString();
            if(getMidi() != null){
                trancheHoraire.append(Constantes.ET);
            }
            trancheHoraire.append(horaireFermetureSoir);
        }

        return trancheHoraire.toString();
    }
}
