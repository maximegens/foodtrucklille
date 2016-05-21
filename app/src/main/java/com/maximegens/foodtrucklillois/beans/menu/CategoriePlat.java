package com.maximegens.foodtrucklillois.beans.menu;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by a627520 on 04/03/2016.
 */
public class CategoriePlat implements Parcelable{
    private final String nomCategoriePlat;
    private final String informations;
    private final List<Plat> listePlats;

    public String getNomCategoriePlat() {
        return nomCategoriePlat;
    }

    public List<Plat> getListePlats() {
        return listePlats;
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

    private CategoriePlat(Parcel in) {
        this.nomCategoriePlat = in.readString();
        this.informations = in.readString();
        this.listePlats = new ArrayList<>();
        in.readTypedList(listePlats, Plat.CREATOR);

    }
}
