package com.maximegens.foodtrucklillois.beans.menu;

import android.os.Parcel;
import android.os.Parcelable;

import com.maximegens.foodtrucklillois.beans.PlanningFoodTruck;

import java.util.ArrayList;

/**
 * Created by a627520 on 04/03/2016.
 */
public class Plat implements Parcelable {

    private String nomPlat;
    private String urlPhoto;
    private String descriptionPlat;
    private float prix;
    private float prixEnMenu;
    private boolean disponible;

    public String getNomPlat() {
        return nomPlat;
    }

    public void setNomPlat(String nomPlat) {
        this.nomPlat = nomPlat;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    public String getDescriptionPlat() {
        return descriptionPlat;
    }

    public void setDescriptionPlat(String descriptionPlat) {
        this.descriptionPlat = descriptionPlat;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public float getPrixEnMenu() {
        return prixEnMenu;
    }

    public void setPrixEnMenu(float prixEnMenu) {
        this.prixEnMenu = prixEnMenu;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nomPlat);
        dest.writeString(urlPhoto);
        dest.writeString(descriptionPlat);
        dest.writeFloat(prix);
        dest.writeFloat(prixEnMenu);
        dest.writeByte((byte) (disponible ? 1 : 0));
    }

    public static final Parcelable.Creator<Plat> CREATOR = new Parcelable.Creator<Plat>()
    {
        @Override
        public Plat createFromParcel(Parcel source)
        {
            return new Plat(source);
        }

        @Override
        public Plat[] newArray(int size)
        {
            return new Plat[size];
        }
    };

    public Plat(Parcel in) {
        this.nomPlat = in.readString();
        this.urlPhoto = in.readString();
        this.descriptionPlat = in.readString();
        this.prix = in.readFloat();
        this.prixEnMenu = in.readFloat();
        disponible = in.readByte() != 0;
    }
}
