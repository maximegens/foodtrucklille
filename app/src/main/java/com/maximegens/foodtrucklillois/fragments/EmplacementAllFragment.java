package com.maximegens.foodtrucklillois.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.maximegens.foodtrucklillois.R;
import com.maximegens.foodtrucklillois.beans.FoodTruck;
import com.maximegens.foodtrucklillois.network.Internet;
import com.maximegens.foodtrucklillois.utils.Constantes;


public class EmplacementAllFragment extends Fragment implements OnMapReadyCallback, AdapterView.OnItemSelectedListener {

    public static String TITLE = "EmplacementAll";
    private  ArrayAdapter<FoodTruck> adapter;
    private Spinner spinnerMap;
    private GoogleMap googleMap;
    private SupportMapFragment map;
    private TextView noConnexion;
    private Button plusProche;

    /**
     * Creation du Fragment.
     * @return Une instance de EmplacementAllFragment.
     */
    public static EmplacementAllFragment newInstance() {
        EmplacementAllFragment fragment = new EmplacementAllFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_emplacement_all, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        plusProche = (Button) view.findViewById(R.id.button_map_plus_proche);

        // On recupere et on masque le titre de la toolbar.
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar_list_ft);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Creation du Spinner pour afficher la liste de Food Trucks.
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,Constantes.lesFTs);
        adapter.setDropDownViewResource(R.layout.layout_drop_list);
        spinnerMap = new Spinner(((AppCompatActivity)getActivity()).getSupportActionBar().getThemedContext());
        spinnerMap.setAdapter(adapter);
        spinnerMap.setOnItemSelectedListener(this);
        toolbar.addView(spinnerMap);

        FragmentManager fm = getChildFragmentManager();
        map = SupportMapFragment.newInstance();

        // On affiche la map si le device posséde une connexion internet.
        if(Internet.isNetworkAvailable(getContext()) && map != null){
            fm.beginTransaction().replace(R.id.framelayout_map_all, map).commit();
            googleMap = map.getMap();
            map.getMapAsync(this);

        }else{
            //TODO ajouter un Broadcast Receiver pour detecter l'apparition d'une connexion et afficher la map
            noConnexion = (TextView) view.findViewById(R.id.no_connexion_map_all);
            noConnexion.setVisibility(View.VISIBLE);
            plusProche.setVisibility(View.GONE);
        }

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
    public void onMapReady(GoogleMap googleMap) {
        //Ajoute un marker sur Marcq En Baroeul, permet de centrer la vue sur l'agglomeration Lilloise.
        LatLng CENTRE = new LatLng(Double.parseDouble(Constantes.GPS_CENTRE_CARTE_MARC_BAROEUL_LATITUDE),Double.parseDouble(Constantes.GPS_CENTRE_CARTE_MARC_BAROEUL_LONGITUDE));

        // Centre la google map avec animation de zoom.
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CENTRE, 11));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        FoodTruck ft = adapter.getItem(position);
        if(ft != null){
            //TODO mise à jour de la map.
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}
}
