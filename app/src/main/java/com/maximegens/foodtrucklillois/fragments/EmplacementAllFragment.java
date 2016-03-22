package com.maximegens.foodtrucklillois.fragments;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.maximegens.foodtrucklillois.R;
import com.maximegens.foodtrucklillois.adapters.InfoWindowMarkerMapAdapter;
import com.maximegens.foodtrucklillois.beans.AdresseFoodTruck;
import com.maximegens.foodtrucklillois.beans.FoodTruck;
import com.maximegens.foodtrucklillois.beans.PlanningFoodTruck;
import com.maximegens.foodtrucklillois.network.Internet;
import com.maximegens.foodtrucklillois.utils.Constantes;
import com.maximegens.foodtrucklillois.utils.PicassoMarker;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;


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
        List<FoodTruck> lesFts = new ArrayList<>();
        lesFts.add(new FoodTruck("Tous"));
        for (FoodTruck ft : Constantes.lesFTs){
            lesFts.add(ft);
        }
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,lesFts);
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

        // Centre la google Map.
        centreMap(googleMap);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        FoodTruck ft = adapter.getItem(position);
        GoogleMap gMapItem = null;

        // Récuperation de la googleMap
        if(map != null){
            gMapItem = map.getMap();
        }

        // Traitement du premier item " Voir tous"
        if(position == 0) {

        }
        else if(ft != null && gMapItem != null){

            // Suppresion des précédents markers.
            gMapItem.clear();

            for(PlanningFoodTruck planning : ft.getPlanning()){

                // Parcours des adresses du food truck pour le midi.
                for (AdresseFoodTruck adresse : planning.getMidi().getAdresses()){
                    ajouteMarker(gMapItem, ft, planning, adresse,Constantes.MIDI);
                }
                // Parcours des adresses du food truck pour le soir.
                for (AdresseFoodTruck adresse : planning.getSoir().getAdresses()){
                    ajouteMarker(gMapItem, ft , planning, adresse,Constantes.SOIR);
                }
            }
            // Centre la google Map.
            centreMap(gMapItem);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    /**
     * Ajout d'un marker sur la google map pour repérer l'emplacement du Food Truck.
     * @param googleMap LA google map.
     * @param planning Le planning.
     * @param adresse L'objet contenant l'adresse du Food Truck.
     * @param periode La periode en cours : midi ou soir.
     */
    private void ajouteMarker(GoogleMap googleMap,FoodTruck ft, PlanningFoodTruck planning, AdresseFoodTruck adresse, String periode) {

        // Verification qu'il existe une latitude et une longitude de renseigné pour pouvoir l'afficher.
        if(adresse.getLatitude() != null && adresse.getLongitude() != null) {

            final MarkerOptions markerOptions = new MarkerOptions();

            Double latitude = new Double(adresse.getLatitude());
            Double longitude = new Double(adresse.getLongitude());

            // Ajout du nom et de la position du Food Truck.
            markerOptions.title(ft.getNom());
            markerOptions.position(new LatLng(latitude, longitude));

            // Creation de l'icone.
            if(ft.getLogo() != null){
                int resID = getResources().getIdentifier(ft.getLogo(), "mipmap", getContext().getPackageName());
                PicassoMarker picassoMarker  = new PicassoMarker(googleMap.addMarker(markerOptions));
                Picasso.with(getActivity()).load(resID).resize(100,100).into(picassoMarker);
            }else{
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_truck));
            }

            // Creation du snippet affichant l'adresse et l'ouverture.
            StringBuilder snippet = new StringBuilder();
            snippet.append("Ouvert uniquement le " + planning.getNomJour() + " " + periode);
            if(adresse.getAdresse() != null){
                snippet.append(Constantes.RETOUR_CHARIOT+Constantes.RETOUR_CHARIOT+adresse.getAdresse());
            }
            markerOptions.snippet(snippet.toString());

            // Ajout de l'infoBulle.
            googleMap.setInfoWindowAdapter(new InfoWindowMarkerMapAdapter(getContext()));
        }
    }



    /**
     * Creation de l'icon du fodd truck pour la google Map.
     * @param ft Le food trucks
     * @param markerOptions le marker.
     */
    private void creationIcon(FoodTruck ft, final MarkerOptions markerOptions) {

        if(ft.getLogo() != null){
            int resID = getResources().getIdentifier(ft.getLogo(), "mipmap", getContext().getPackageName());

                Picasso.with(getContext()).load(resID).resize(48,48).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(bitmap));
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_truck));
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });
        }else{
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_truck));
        }
    }

    /**
     * Centre la Google Map sur Marcq en Baroeul.
     * @param googleMap
     */
    private void centreMap(GoogleMap googleMap) {
        //Ajoute un marker sur Marcq En Baroeul, permet de centrer la vue sur l'agglomeration Lilloise.
        LatLng CENTRE = new LatLng(Double.parseDouble(Constantes.GPS_CENTRE_CARTE_MARC_BAROEUL_LATITUDE),Double.parseDouble(Constantes.GPS_CENTRE_CARTE_MARC_BAROEUL_LONGITUDE));

        // Centre la google map avec animation de zoom.
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CENTRE, 11));
    }
}
