package com.maximegens.foodtrucklillois.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.maximegens.foodtrucklillois.FoodTruckActivity;
import com.maximegens.foodtrucklillois.R;
import com.maximegens.foodtrucklillois.beans.AdresseFoodTruck;
import com.maximegens.foodtrucklillois.beans.FoodTruck;
import com.maximegens.foodtrucklillois.beans.PlanningFoodTruck;
import com.maximegens.foodtrucklillois.utils.Constantes;


public class MapFoodTruckFragment extends SupportMapFragment {

    private GoogleMap googleMap;
    private FoodTruck ft = null;

    /**
     * Creation du Fragment.
     * @return Une instance de EmplacementFoodTruckFragment.
     */
    public static MapFoodTruckFragment newInstance(FoodTruck ft) {
        MapFoodTruckFragment fragment = new MapFoodTruckFragment();
        Bundle args = new Bundle();
        args.putParcelable(FoodTruckActivity.KEY_FOODTRUCK_SELECTIONNER,ft);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(getArguments() != null){
            ft = getArguments().getParcelable(FoodTruckActivity.KEY_FOODTRUCK_SELECTIONNER);
        }

        googleMap = getMap();

        getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {

                //ajoute un marker sur Lille
                LatLng LILLE = new LatLng(50.636922, 3.063485);

                for(PlanningFoodTruck planning : ft.getPlanning()){

                    StringBuilder snippet = new StringBuilder();
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.title(ft.getNom());
                    markerOptions.snippet("Uniquement le " + planning.getNomJour());
                    for (AdresseFoodTruck adresse : planning.getMidi().getAdresses()){
                        snippet.append(" midi");
                        snippet.append(Constantes.RETOUR_CHARIOT+adresse.getAdresse());
                        markerOptions.snippet(snippet.toString());
                        LatLng coordonnees = new LatLng(50.636922, 3.063485);
                        markerOptions.position(coordonnees);
                        googleMap.addMarker(markerOptions);
                    }
                }

                // Centre la google map avec animation de zoom
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LILLE, 15));
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(12), 2000, null);

            }
        });

        }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

}
