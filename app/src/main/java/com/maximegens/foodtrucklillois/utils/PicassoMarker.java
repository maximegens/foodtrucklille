package com.maximegens.foodtrucklillois.utils;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.maximegens.foodtrucklillois.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Implement de Target pour les Googles Map.
 * Permet de récuperer une image via Picasso et de l'afficher en tant d'icon sans perdre la référence au Marker a cause du garbage collector.
 */
public class PicassoMarker implements Target {

    Marker mMarker;

    public PicassoMarker(Marker marker) {
        mMarker = marker;
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
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {
        mMarker.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_truck));
    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {

    }
}
