package com.maximegens.foodtrucklillois.beans.menu;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by a627520 on 04/03/2016.
 */
public class Menu implements Parcelable{

    private List<CategoriePlat> lesCategorieDePlat;
    private int idFoodTruck;
    private String information;
    private String descriptionMenu;

    public List<CategoriePlat> getLesCategorieDePlat() {
        return lesCategorieDePlat;
    }

    public void setLesCategorieDePlat(List<CategoriePlat> lesCategorieDePlat) {
        this.lesCategorieDePlat = lesCategorieDePlat;
    }

    public int getIdFoodTruck() {
        return idFoodTruck;
    }

    public void setIdFoodTruck(int idFoodTruck) {
        this.idFoodTruck = idFoodTruck;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getDescriptionMenu() {
        return descriptionMenu;
    }

    public void setDescriptionMenu(String description_menu) {
        this.descriptionMenu = description_menu;
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
        this.lesCategorieDePlat = new ArrayList<CategoriePlat>();
        in.readTypedList(lesCategorieDePlat, CategoriePlat.CREATOR);
        this.idFoodTruck = in.readInt();
        this.information = in.readString();
        this.descriptionMenu = in.readString();
    }
}
