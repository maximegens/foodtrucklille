package com.maximegens.foodtrucklillois.beans.menu;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by a627520 on 04/03/2016.
 */
public class Plat implements Parcelable {

    private final String nomPlat;
    private final String urlPhoto;
    private final String descriptionPlat;
    private final String prix;
    private final String prixEnMenu;
    private final boolean disponible;

    public String getNomPlat() {
        return nomPlat;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public String getDescriptionPlat() {
        return descriptionPlat;
    }

    public String getPrix() {
        return prix;
    }

    public String getPrixEnMenu() {
        return prixEnMenu;
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
        dest.writeString(prix);
        dest.writeString(prixEnMenu);
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

    private Plat(Parcel in) {
        this.nomPlat = in.readString();
        this.urlPhoto = in.readString();
        this.descriptionPlat = in.readString();
        this.prix = in.readString();
        this.prixEnMenu = in.readString();
        disponible = in.readByte() != 0;
    }
}
