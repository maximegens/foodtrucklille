package com.maximegens.foodtrucklillois.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.maximegens.foodtrucklillois.FoodTruckActivity;
import com.maximegens.foodtrucklillois.R;
import com.maximegens.foodtrucklillois.beans.FoodTruck;


public class EmplacementFoodTruckFragment extends Fragment {

    public static String TITLE = "Map";
    private FoodTruck ft = null;
    private SupportMapFragment fragmentMap;
    private GoogleMap googleMap;

    /**
     * Creation du Fragment.
     * @return Une instance de EmplacementFoodTruckFragment.
     */
    public static EmplacementFoodTruckFragment newInstance(FoodTruck ft) {
        EmplacementFoodTruckFragment fragment = new EmplacementFoodTruckFragment();
        Bundle args = new Bundle();
        args.putParcelable(FoodTruckActivity.KEY_FOODTRUCK_SELECTIONNER,ft);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_emplacement_ft, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(getArguments() != null){
            ft = getArguments().getParcelable(FoodTruckActivity.KEY_FOODTRUCK_SELECTIONNER);
        }

        // Utilisation du Nested Fragment pour afficher le fragment Map dans le fragment Emplacement.
        FragmentManager fm = getChildFragmentManager();
        fragmentMap = MapFoodTruckFragment.newInstance(ft);
        fm.beginTransaction().replace(R.id.framelayout_map, fragmentMap).commit();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
