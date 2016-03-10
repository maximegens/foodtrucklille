package com.maximegens.foodtrucklillois.beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Maxime on 25/02/2016.
 */
public class AdresseFoodTruck implements Parcelable{

    private String adresse;
    private String latitude;
    private String longitude;
    private String map;

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getMap() {
        return map;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(adresse);
        dest.writeString(latitude);
        dest.writeString(longitude);
        dest.writeString(map);
    }

    public static final Parcelable.Creator<AdresseFoodTruck> CREATOR = new Parcelable.Creator<AdresseFoodTruck>()
    {
        @Override
        public AdresseFoodTruck createFromParcel(Parcel source)
        {
            return new AdresseFoodTruck(source);
        }

        @Override
        public AdresseFoodTruck[] newArray(int size)
        {
            return new AdresseFoodTruck[size];
        }
    };

    public AdresseFoodTruck(Parcel in) {
        this.adresse = in.readString();
        this.latitude = in.readString();
        this.longitude = in.readString();
        this.map = in.readString();

    }
}
