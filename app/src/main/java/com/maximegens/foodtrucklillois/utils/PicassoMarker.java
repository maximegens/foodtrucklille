package com.maximegens.foodtrucklillois.utils;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.maximegens.foodtrucklillois.R;
import com.maximegens.foodtrucklillois.beans.AdresseFoodTruck;
import com.maximegens.foodtrucklillois.beans.PlanningFoodTruck;
import com.maximegens.foodtrucklillois.fragments.EmplacementAllFragment;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.HashSet;
import java.util.Set;

/**
 * Implement de Target pour les Googles Map.
 * Permet de récuperer une image via Picasso et de l'afficher en tant d'icon sans perdre la référence au Marker a cause du garbage collector.
 */
public class PicassoMarker implements Target {

    Marker mMarker;
    PlanningFoodTruck planning;
    AdresseFoodTruck adresse;
    String periode;

    public PicassoMarker(Marker marker,PlanningFoodTruck planning, AdresseFoodTruck adresse, String periode) {
        mMarker = marker;
        this.planning = planning;
        this.adresse = adresse;
        this.periode = periode;
        mMarker.setSnippet(creationInfoBulle());
    }

    @Override
    public boolean equals(Object o ){
        if(o instanceof PicassoMarker) {
            Marker marker = ((PicassoMarker) o).mMarker;
            return mMarker.equals(marker);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return mMarker.hashCode();
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        mMarker.setIcon(BitmapDescriptorFactory.fromBitmap(bitmap));
        EmplacementAllFragment.protectedFromGarbageCollectorTargets.remove(this);
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {
        mMarker.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_truck));
        EmplacementAllFragment.protectedFromGarbageCollectorTargets.remove(this);
    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {

    }

    public String creationInfoBulle() {
        // Creation du snippet affichant l'adresse et l'ouverture.
        StringBuilder snippet = new StringBuilder();
        snippet.append("Ouvert uniquement le " + planning.getNomJour() + " " + periode);
        if (adresse.getAdresse() != null) {
            snippet.append(Constantes.RETOUR_CHARIOT + Constantes.RETOUR_CHARIOT + adresse.getAdresse());
        }
        return snippet.toString();
    }
}
