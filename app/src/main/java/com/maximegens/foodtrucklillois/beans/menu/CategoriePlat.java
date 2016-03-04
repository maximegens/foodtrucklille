package com.maximegens.foodtrucklillois.beans.menu;

import android.os.Parcel;
import android.os.Parcelable;

import com.maximegens.foodtrucklillois.beans.AdresseFoodTruck;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by a627520 on 04/03/2016.
 */
public class CategoriePlat implements Parcelable{
    private String nomCategoriePlat;
    private String informations;
    private List<Plat> listePlats;

    public String getNomCategoriePlat() {
        return nomCategoriePlat;
    }

    public void setNomCategoriePlat(String nomCategoriePlat) {
        this.nomCategoriePlat = nomCategoriePlat;
    }

    public List<Plat> getListePlats() {
        return listePlats;
    }

    public void setListePlats(List<Plat> listePlats) {
        this.listePlats = listePlats;
    }

    public String getInformations() {
        return informations;
    }

    public void setInformations(String informations) {
        this.informations = informations;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nomCategoriePlat);
        dest.writeString(informations);
        dest.writeTypedList(listePlats);
    }

    public static final Parcelable.Creator<CategoriePlat> CREATOR = new Parcelable.Creator<CategoriePlat>()
    {
        @Override
        public CategoriePlat createFromParcel(Parcel source)
        {
            return new CategoriePlat(source);
        }

        @Override
        public CategoriePlat[] newArray(int size)
        {
            return new CategoriePlat[size];
        }
    };

    public CategoriePlat(Parcel in) {
        this.nomCategoriePlat = in.readString();
        this.informations = in.readString();
        this.listePlats = new ArrayList<Plat>();
        in.readTypedList(listePlats, Plat.CREATOR);

    }
}
