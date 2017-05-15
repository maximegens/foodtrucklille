package com.maximegens.foodtrucklillois.utils;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.maximegens.foodtrucklillois.R;
import com.maximegens.foodtrucklillois.beans.AdresseFoodTruck;
import com.maximegens.foodtrucklillois.beans.FoodTruck;
import com.maximegens.foodtrucklillois.beans.PlanningFoodTruck;
import com.maximegens.foodtrucklillois.fragments.EmplacementAllFragment;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Implement de Target pour les Googles Map.
 * Permet de récuperer une image via Picasso et de l'afficher en tant d'icon sans perdre la référence au Marker a cause du garbage collector.
 */
public class PicassoMarker implements Target {

    private final Marker mMarker;
    private final PlanningFoodTruck planning;
    private final AdresseFoodTruck adresse;
    private final String periode;
    private final FoodTruck ft;

    public PicassoMarker(Marker marker, FoodTruck ft, PlanningFoodTruck planning, AdresseFoodTruck adresse, String periode) {
        mMarker = marker;
        this.planning = planning;
        this.ft = ft;
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
        try {
            mMarker.setIcon(BitmapDescriptorFactory.fromBitmap(bitmap));
            EmplacementAllFragment.protectedFromGarbageCollectorTargets.remove(this);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {
        mMarker.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_truck));
        EmplacementAllFragment.protectedFromGarbageCollectorTargets.remove(this);
    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {

    }

    private String creationInfoBulle() {
        // Creation du snippet affichant l'adresse et l'ouverture.
        StringBuilder snippet = new StringBuilder();
        snippet.append("Ouvert uniquement le ").append(planning.getNomJour()).append(" ").append(periode);
        // code spécial pour effet gourmet et ses semaines impaires.
        if(ft != null && ft.getId() == 113 && (planning.getNumJour() == 2 || planning.getNumJour() == 5)){
            snippet.append(" et les semaines impaires");
        }
        if (adresse.getAdresse() != null) {
            snippet.append(Constantes.RETOUR_CHARIOT).append(Constantes.RETOUR_CHARIOT).append(adresse.getAdresse());
        }
        return snippet.toString();
    }
}
