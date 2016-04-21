package com.maximegens.foodtrucklillois.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class EmplacementAllFragment extends Fragment implements OnMapReadyCallback, AdapterView.OnItemSelectedListener {

    private ArrayAdapter<FoodTruck> adapterFT;
    private ArrayAdapter<CharSequence> adapterJour;
    private SupportMapFragment map;
    private TextView noConnexion;
    private int jourSelection = 0;
    private FoodTruck ftSelection = null;
    public static final Set<Target> protectedFromGarbageCollectorTargets = new HashSet<>();

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
        this.setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_emplacement_all, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        (getActivity()).setTitle(R.string.title_map_all);

        FragmentManager fm = getChildFragmentManager();
        map = SupportMapFragment.newInstance();

        // On affiche la map si le device posséde une connexion internet.
        if(Internet.isNetworkAvailable(getContext()) && map != null){
            fm.beginTransaction().replace(R.id.framelayout_map_all, map).commit();
            map.getMapAsync(this);
        }else{
            //TODO ajouter un Broadcast Receiver pour detecter l'apparition d'une connexion et afficher la map
            noConnexion = (TextView) view.findViewById(R.id.no_connexion_map_all);
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
        centreMap(googleMap);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        // Récuperation de la googleMap
        GoogleMap gMapItem = null;
        if(map != null){
            gMapItem = map.getMap();
        }
        // Suppresion des précédents markers.
        gMapItem.clear();

        // Récupération du spinner.
        Spinner spinner = (Spinner) parent;

        // Spinner gérant les jours.
        if(spinner.getId() == R.id.action_spinner_map_fts_jour) {
            gestionSpinnerJour(position, gMapItem);
        }
        // Spinner gérant les FTs.
        else if(spinner.getId() == R.id.action_spinner_map_fts) {
            gestionSpinnerFoodTruck(position, gMapItem);
        }
        // Centre la google Map.
        centreMap(gMapItem);
    }

    /**
     * Gestion de l'affichage de la map en fonction du jour sélectionné.
     * @param position la position du jour sélectionné dans le spinner.
     * @param gMapItem la Google Map.
     */
    private void gestionSpinnerJour(int position, GoogleMap gMapItem) {
        jourSelection = position;
        // Tous les ft
        if(ftSelection != null && ftSelection.getNom().equals(Constantes.TOUS) && gMapItem != null){
            // Tous les jours
            if(jourSelection == 0){
                for (FoodTruck ft : Constantes.lesFTs) {
                    for (PlanningFoodTruck planning : ft.getPlanning()) {
                        traitementByPlanning(planning, gMapItem, ft);
                    }
                }
            }else{ // Un jour de selectionné
                for (FoodTruck ft : Constantes.lesFTs){
                    PlanningFoodTruck planning = ft.getPlaningByJour(jourSelection);
                    traitementByPlanning(planning, gMapItem, ft);
                }
            }

        }else if(ftSelection != null && !ftSelection.getNom().equals(Constantes.TOUS) && gMapItem != null){ // Un ft de selectionné
            // Tous les jours
            if(jourSelection == 0){
                for (PlanningFoodTruck planning : ftSelection.getPlanning()) {
                    traitementByPlanning(planning, gMapItem, ftSelection);
                }
            }else{ // Un jour de selectionné
                PlanningFoodTruck planning = ftSelection.getPlaningByJour(jourSelection);
                traitementByPlanning(planning, gMapItem, ftSelection);
            }
        }
    }

    /**
     * Gestion de l'affichage de la map en fonction du Food Truck sélectionné.
     * @param position la position du Food truck sélectionné dans le spinner.
     * @param gMapItem la Google Map.
     */
    private void gestionSpinnerFoodTruck(int position, GoogleMap gMapItem) {
        ftSelection = adapterFT.getItem(position);

        // Tous les jours
        if(jourSelection == 0 && gMapItem != null){
            // Tous les ft
            if(ftSelection != null && ftSelection.getNom().equals(Constantes.TOUS)){
                for (FoodTruck left : Constantes.lesFTs) {
                    for (PlanningFoodTruck planning : left.getPlanning()) {
                        parcoursAdresses(left, gMapItem, planning);
                    }
                }
            }else{ // Un ft de selectionné
                for (PlanningFoodTruck planning : ftSelection.getPlanning()) {
                    parcoursAdresses(ftSelection, gMapItem, planning);
                }
            }

         // Un jour de selectionné
        }else if(jourSelection != 0 && gMapItem != null){
            // Tous les ft
            if(ftSelection != null && ftSelection.getNom().equals(Constantes.TOUS)){
                for(FoodTruck ft : Constantes.lesFTs) {
                    PlanningFoodTruck planning = ft.getPlaningByJour(jourSelection);
                    parcoursAdresses(ft, gMapItem, planning);
                }
            }else{ // Un ft de selectionné
                PlanningFoodTruck planning = ftSelection.getPlaningByJour(jourSelection);
                parcoursAdresses(ftSelection, gMapItem, planning);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}


    /**
     * Parcours les adresses du food truck pour ajouter un marker sur la map permettant de visualiser l'emplacement du FT.
     * @param ft le food truck.
     * @param gMapItem La googleMap.
     * @param planning Le planning du FT.
     */
    private void parcoursAdresses(FoodTruck ft, GoogleMap gMapItem, PlanningFoodTruck planning) {
        if(planning != null){
            // Parcours des adresses du food truck pour le midi.
            if(planning.getMidi() != null) {
                for (AdresseFoodTruck adresse : planning.getMidi().getAdresses()) {
                    ajouteMarker(gMapItem, ft, planning, adresse, Constantes.MIDI);
                }
            }
            // Parcours des adresses du food truck pour le soir.
            if(planning.getSoir() != null) {
                for (AdresseFoodTruck adresse : planning.getSoir().getAdresses()) {
                    ajouteMarker(gMapItem, ft, planning, adresse, Constantes.SOIR);
                }
            }
        }
    }

    /**
     * Traitement des emplacements en fonction du planning passé en paramétre.
     * @param planning le planning a traiter.
     */
    private void traitementByPlanning(PlanningFoodTruck planning, GoogleMap gMapItem, FoodTruck ft) {
        if(planning != null){
            // Parcours des adresses du food truck pour le midi.
            if (planning.getMidi() != null) {
                for (AdresseFoodTruck adresse : planning.getMidi().getAdresses()) {
                    ajouteMarker(gMapItem, ft, planning, adresse, Constantes.MIDI);
                }
            }
            // Parcours des adresses du food truck pour le soir.
            if (planning.getSoir() != null) {
                for (AdresseFoodTruck adresse : planning.getSoir().getAdresses()) {
                    ajouteMarker(gMapItem, ft, planning, adresse, Constantes.SOIR);
                }
            }
        }
    }

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
                PicassoMarker picassoMarker  = new PicassoMarker(googleMap.addMarker(markerOptions),planning,adresse,periode);
                protectedFromGarbageCollectorTargets.add(picassoMarker);
                Picasso.with(getActivity()).load(resID).resize(100,100).into(picassoMarker);
            }else{
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_truck));
            }

            // Ajout de l'infoBulle.
            googleMap.setInfoWindowAdapter(new InfoWindowMarkerMapAdapter(getContext()));
        }
    }


    /**
     * Centre la Google Map sur Marcq en Baroeul.
     * @param googleMap
     */
    private void centreMap(GoogleMap googleMap) {
        //Ajoute un marker sur Marcq En Baroeul, permet de centrer la vue sur l'agglomeration Lilloise.
        LatLng CENTRE = new LatLng(Double.parseDouble(Constantes.GPS_CENTRE_CARTE_MARC_BAROEUL_LATITUDE),Double.parseDouble(Constantes.GPS_CENTRE_CARTE_MARC_BAROEUL_LONGITUDE));

        // Affiche le bouton permettant de localiser l'utilisateur.
        googleMap.setMyLocationEnabled(true);

        // Centre la google map avec animation de zoom.
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CENTRE, 11));
    }

    /**
     * Gestion du menu.
     * @param menu Le menu.
     * @param inflater L'objet pour inflater.
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_map_all, menu);
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem itemFT = menu.findItem(R.id.action_spinner_map_fts);
        Spinner spinnerMap = (Spinner) MenuItemCompat.getActionView(itemFT);

        // Creation du Spinner pour afficher la liste de Food Trucks.
        List<FoodTruck> lesFts = new ArrayList<>();
        lesFts.add(new FoodTruck(Constantes.TOUS));
        for (FoodTruck ft : Constantes.lesFTs){
            lesFts.add(ft);
        }
        adapterFT = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item,lesFts);
        adapterFT.setDropDownViewResource(R.layout.layout_drop_list);
        spinnerMap.setAdapter(adapterFT);
        spinnerMap.setOnItemSelectedListener(this);

        // Creation du spinner pour la liste des jours.
        MenuItem itemJour = menu.findItem(R.id.action_spinner_map_fts_jour);
        Spinner spinnerMapJour = (Spinner) MenuItemCompat.getActionView(itemJour);
        adapterJour = ArrayAdapter.createFromResource(getContext(), R.array.semaine_array, android.R.layout.simple_spinner_item);
        adapterJour.setDropDownViewResource(R.layout.layout_drop_list);
        spinnerMapJour.setAdapter(adapterJour);
        spinnerMapJour.setOnItemSelectedListener(this);
    }
}
