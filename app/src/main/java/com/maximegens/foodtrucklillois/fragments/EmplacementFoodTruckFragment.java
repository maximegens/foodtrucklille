package com.maximegens.foodtrucklillois.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.maximegens.foodtrucklillois.FoodTruckActivity;
import com.maximegens.foodtrucklillois.R;
import com.maximegens.foodtrucklillois.adapters.InfoWindowMarkerMapAdapter;
import com.maximegens.foodtrucklillois.beans.AdresseFoodTruck;
import com.maximegens.foodtrucklillois.beans.FoodTruck;
import com.maximegens.foodtrucklillois.beans.PlanningFoodTruck;
import com.maximegens.foodtrucklillois.network.Internet;
import com.maximegens.foodtrucklillois.utils.Constantes;

import java.util.ArrayList;
import java.util.List;


public class EmplacementFoodTruckFragment extends Fragment implements OnMapReadyCallback, AdapterView.OnItemSelectedListener{

    public static String TITLE = "Map";
    private TextView noConnexion;
    private FoodTruck ft = null;
    private SupportMapFragment fragmentMap;
    private ArrayAdapter<String> adapter;
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
        this.setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_emplacement_ft, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(getArguments() != null){
            ft = getArguments().getParcelable(FoodTruckActivity.KEY_FOODTRUCK_SELECTIONNER);
        }

        // On affiche la map si le device posséde une connexion internet.
        if(Internet.isNetworkAvailable(getContext())){
            SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
            googleMap = mapFragment.getMap();
        }else{
            //TODO ajouter un Broadcast Receiver pour detecter l'apparition d'une connexion et afficher la map
            noConnexion = (TextView) view.findViewById(R.id.no_connexion_map);
            noConnexion.setVisibility(View.VISIBLE);
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
        LatLng CENTRE = new LatLng(Double.parseDouble(Constantes.GPS_CENTRE_CARTE_MARC_BAROEUL_LATITUDE), Double.parseDouble(Constantes.GPS_CENTRE_CARTE_MARC_BAROEUL_LONGITUDE));
        // Centre la google map avec animation de zoom.
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CENTRE, 8));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(11), 2000, null);
    }

    /**
     * Ajout d'un marker sur la google map pour repérer l'emplacement du Food Truck.
     * @param googleMap LA google map.
     * @param planning Le planning.
     * @param adresse L'objet contenant l'adresse du Food Truck.
     * @param periode La periode en cours : midi ou soir.
     */
    private void ajouteMarker(GoogleMap googleMap, PlanningFoodTruck planning, AdresseFoodTruck adresse, String periode) {

        // Verification qu'il existe une latitude et une longitude de renseigné pour pouvoir l'afficher.
        if(adresse.getLatitude() != null && adresse.getLongitude() != null) {

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.title(ft.getNom());

            Double latitude = new Double(adresse.getLatitude());
            Double longitude = new Double(adresse.getLongitude());

            markerOptions.title(ft.getNom());
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_truck));
            markerOptions.position(new LatLng(latitude, longitude));

            StringBuilder snippet = new StringBuilder();
            snippet.append("Ouvert uniquement le "+planning.getNomJour() + " "+periode);
            if(adresse.getAdresse() != null){
                snippet.append(Constantes.RETOUR_CHARIOT+Constantes.RETOUR_CHARIOT+adresse.getAdresse());
            }
            markerOptions.snippet(snippet.toString());
            googleMap.setInfoWindowAdapter(new InfoWindowMarkerMapAdapter(getContext()));
            googleMap.addMarker(markerOptions);
        }
    }

    /**
     * Traitement des emplacements en fonction du planning passé en paramétre.
     * @param planning le planning a traiter.
     */
    private void traitementByPlanning(PlanningFoodTruck planning) {
        // Parcours des adresses du food truck pour le midi.
        if (planning.getMidi() != null) {
            for (AdresseFoodTruck adresse : planning.getMidi().getAdresses()) {
                ajouteMarker(googleMap, planning, adresse, Constantes.MIDI);
            }
        }
        // Parcours des adresses du food truck pour le soir.
        if (planning.getSoir() != null) {
            for (AdresseFoodTruck adresse : planning.getSoir().getAdresses()) {
                ajouteMarker(googleMap, planning, adresse, Constantes.SOIR);
            }
        }
    }

    /**
     * Gestion du menu.
     * @param menu Le menu.
     * @param inflater L'objet pour inflater.
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_map, menu);
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem item = menu.findItem(R.id.action_spinner_map_ft);
        Spinner spinnerMap = (Spinner) MenuItemCompat.getActionView(item);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.semaine_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.layout_drop_list);
        spinnerMap.setAdapter(adapter);
        spinnerMap.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(googleMap != null){

            // Suppresion des précédents markers.
            googleMap.clear();

            if(position == 0){
                for (PlanningFoodTruck planning : ft.getPlanning()) {
                    traitementByPlanning(planning);
                }
            }else{
                PlanningFoodTruck planning = ft.getPlaningByJour(position);
                traitementByPlanning(planning);
            }
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
