package com.maximegens.foodtrucklillois.beans.menu;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Bean représentant le menu d'un food truck.
 */
public class Menu implements Parcelable{

    private final List<CategoriePlat> lesCategorieDePlat;
    private final int idFoodTruck;
    private final String information;
    private final String descriptionMenu;

    public List<CategoriePlat> getLesCategorieDePlat() {
        return lesCategorieDePlat;
    }

    public String getInformation() {
        return information;
    }

    public String getDescriptionMenu() {
        return descriptionMenu;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(lesCategorieDePlat);
        dest.writeInt(idFoodTruck);
        dest.writeString(information);
        dest.writeString(descriptionMenu);
    }

    public static final Parcelable.Creator<Menu> CREATOR = new Parcelable.Creator<Menu>()
    {
        @Override
        public Menu createFromParcel(Parcel source)
        {
            return new Menu(source);
        }

        @Override
        public Menu[] newArray(int size)
        {
            return new Menu[size];
        }
    };

    public Menu(Parcel in) {
        this.lesCategorieDePlat = new ArrayList<>();
        in.readTypedList(lesCategorieDePlat, CategoriePlat.CREATOR);
        this.idFoodTruck = in.readInt();
        this.information = in.readString();
        this.descriptionMenu = in.readString();
    }
}
