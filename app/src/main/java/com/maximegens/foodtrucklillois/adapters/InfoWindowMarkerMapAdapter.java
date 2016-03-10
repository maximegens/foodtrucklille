package com.maximegens.foodtrucklillois.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.maximegens.foodtrucklillois.R;

/**
 * Adapter pour le snippet de la google map.
 */
public class InfoWindowMarkerMapAdapter implements GoogleMap.InfoWindowAdapter {

    private Context ctx;

    public InfoWindowMarkerMapAdapter(Context ctx){
        this.ctx = ctx;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.snippet_custom_marker, null);

        TextView snippetTitle = (TextView)  v.findViewById(R.id.snippet_title);
        TextView snippetAdresse = (TextView)  v.findViewById(R.id.snippet_adresse);
        snippetTitle.setText(marker.getTitle());
        snippetAdresse.setText(marker.getSnippet());
        return v;
    }
}
