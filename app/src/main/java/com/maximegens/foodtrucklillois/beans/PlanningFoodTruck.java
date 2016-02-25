package com.maximegens.foodtrucklillois.beans;

/**
 * Created by Maxime on 25/02/2016.
 */
public class PlanningFoodTruck {

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
}
