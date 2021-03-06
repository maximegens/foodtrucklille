package com.maximegens.foodtrucklillois.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.maximegens.foodtrucklillois.R;
import com.maximegens.foodtrucklillois.adapters.ListeFTAdapter;
import com.maximegens.foodtrucklillois.beans.FoodTruck;
import com.maximegens.foodtrucklillois.beans.PlanningFoodTruck;
import com.maximegens.foodtrucklillois.utils.Constantes;
import com.maximegens.foodtrucklillois.utils.GestionnaireHoraire;
import com.maximegens.foodtrucklillois.utils.GridLayoutManagerFoodTruck;
import com.maximegens.foodtrucklillois.utils.SortListeFT;
import com.maximegens.foodtrucklillois.utils.Utils;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;


/**
 * Class ListeFoodTruckFragment.
 * Fragment gérant l'affichage de la liste des Food trucks.
 */
public class ListeFoodTruckFragment extends Fragment implements LocationListener {

    private LocationManager locationManager;
    private LinearLayout linearActiveGPS;
    private LinearLayout linearPlusProcheEnCours;
    private RecyclerView recyclerViewListeFT;
    private GridLayoutManagerFoodTruck layoutManagerFT;
    private CoordinatorLayout coordinatorListeFt;

    /**
     * Creation du Fragment.
     * @return Une instance de ListeFoodTruckFragment.
     */
    public static ListeFoodTruckFragment newInstance() {
        ListeFoodTruckFragment fragment = new ListeFoodTruckFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Demande d’inflater la vue à utiliser par ce fragment, ici on inflate la vue contenant la liste des FT.
     * @param inflater L'inflater.
     * @param container Le conteneur de la vue à inflater.
     * @param savedInstanceState savedInstanceState.
     * @return La vue qui a été inflatée.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_liste_ft, container, false);
    }

    /**
     * Le fragment possède enfin une vue, nous pouvons récupérer nos widgets (TextView, etc.) puis les modifier.
     * @param view La view crée.
     * @param savedInstanceState savedInstanceState.
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Activity activity = getActivity();

        activity.setTitle(getString(R.string.title_liste_food_truck));
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        layoutManagerFT = new GridLayoutManagerFoodTruck(getContext());
        recyclerViewListeFT = (RecyclerView) view.findViewById(R.id.recycler_view_liste_ft);
        linearActiveGPS = (LinearLayout) view.findViewById(R.id.linear_gps_desactive);
        Button activateGPS = (Button) view.findViewById(R.id.button_activer_gps);
        linearPlusProcheEnCours = (LinearLayout) view.findViewById(R.id.linear_recherche_ft);
        coordinatorListeFt = (CoordinatorLayout) view.findViewById(R.id.coordinator_liste_ft);
        recyclerViewListeFT.setHasFixedSize(true);

        // Abonnement aux receiver du GPS.
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

        // Affichage du message d'information d'activation du GPS et de la recherche la plus proche
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            linearActiveGPS.setVisibility(View.GONE);
            linearPlusProcheEnCours.setVisibility(View.VISIBLE);
        }else{
            linearActiveGPS.setVisibility(View.VISIBLE);
            linearPlusProcheEnCours.setVisibility(View.GONE);
        }


        // Creation de l'agencement classique des food trucks en attendant la détection du food truck le plus proche si le gps est activé.
        int nombreColonne = Utils.getNbColonneForScreen(getContext());
        recyclerViewListeFT.setLayoutManager(new GridLayoutManager(getContext(), nombreColonne));

        // Ajout des FTs interne dans l'adapters de la liste.
        ListeFTAdapter listeFTAdapter1 = new ListeFTAdapter(Constantes.lesFTs, getContext(), true);
        recyclerViewListeFT.setAdapter(listeFTAdapter1);

        // Mise a jour de la liste avec un affichage classique (true)
        ListeFTAdapter listeFTAdapter = new ListeFTAdapter(Constantes.lesFTs, activity,true);
        recyclerViewListeFT.setLayoutManager(new GridLayoutManager(activity, Utils.getNbColonneForScreen(activity)));
        recyclerViewListeFT.setAdapter(listeFTAdapter);

        // Bouton permettant d'activer le GPS.
        activateGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turnGPSOn();
            }
        });

    }

    /**
     * OnResume.
     */
    @Override
    public void onResume() {
        super.onResume();
        addUpdatesLocation();
    }

    /**
     * OnPause.
     */
    @Override
    public void onPause() {
        super.onPause();
        removeUpdatesLocation();
    }

    /**
     * Gestion du menu.
     * @param menu Le menu.
     * @param inflater L'objet pour inflater.
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_liste_ft, menu);
        super.onCreateOptionsMenu(menu, inflater);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

        // Traitement de la recherche.
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String recherche) {
                updateRechercheFT(recherche);
                return true;
            }
        });

        // Traitement lorsque la recherche est activé ou refermé.
        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                removeUpdatesLocation();
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                addUpdatesLocation();
                return true;
            }
        });

    }

    /**
     * Méthode appelé à chaque mise à jour de la position du GPS.
     * @param location La location récupéré par le GPS.
     */
    @Override
    public void onLocationChanged(Location location) {
        boolean tousFermer = true;

        Calendar cal = GestionnaireHoraire.createCalendarToday();
        boolean isMidiOrBeforeMidi = GestionnaireHoraire.isMidiOrBeforeMidi();
        boolean isSoirOrBeforeSoirButAfterMidi= GestionnaireHoraire.isSoirOrBeforeSoirButAfterMidi();

        //TODO a mettre dans une asyntasck
        // Pour chaque Food Truck on calcul sa distance par rapport à l'utilisateur.
        for (FoodTruck ft : Constantes.lesFTs) {

            //On verifie si le food truck est ouvert aujoud'hui et qu'on ne se trouve pas aprés la fermeture du ft pour la journée..
            if (ft.isOpenToday() && ft.isDateBeforeLastHoraireFermeture(cal)) {

                Location loc = new Location("");
                PlanningFoodTruck planning = ft.getPlaningToday();

                if (planning != null) {
                    // On vérifie si on est dans la tanche horaire du midi.
                    if (isMidiOrBeforeMidi) {
                        if (ft.existPlaningMidiAdresse(planning)) {
                            loc.setLatitude(Double.parseDouble(planning.getMidi().getAdresses().get(0).getLatitude()));
                            loc.setLongitude(Double.parseDouble(planning.getMidi().getAdresses().get(0).getLongitude()));
                        }else if(ft.existPlaningSoirAdresse(planning)) {
                            // Si il n'y a pas de service le midi on verifie si il existe un service le soir
                            loc.setLatitude(Double.parseDouble(planning.getSoir().getAdresses().get(0).getLatitude()));
                            loc.setLongitude(Double.parseDouble(planning.getSoir().getAdresses().get(0).getLongitude()));
                        }
                    } else if (isSoirOrBeforeSoirButAfterMidi && ft.existPlaningSoirAdresse(planning)) {
                    // On vérifie si on est dans la tanche horaire du soir et qu'il existe une adresse avec coordonnées gps pour le soir.
                            loc.setLatitude(Double.parseDouble(planning.getSoir().getAdresses().get(0).getLatitude()));
                            loc.setLongitude(Double.parseDouble(planning.getSoir().getAdresses().get(0).getLongitude()));
                    }

                    // On calcul et on ajout la distance depuis l'utilisateur au Food truck.
                    if(loc.getLongitude() != 0.0 && loc.getLatitude() != 0.0){
                        ft.setDistanceFromUser(location.distanceTo(loc));
                        tousFermer = false;
                    }else{
                        ft.setDistanceFromUser(Constantes.FT_FERMER_DISTANCE);
                    }
                }
            } else {
                ft.setDistanceFromUser(Constantes.FT_FERMER_DISTANCE);
            }
        }

        // On trie la liste des Food trucks par distance.
        Collections.sort(Constantes.lesFTs, new SortListeFT(true));

        // On masque le message de recherche du ft le plus proche en cours.
        linearPlusProcheEnCours.setVisibility(View.GONE);

        // Mise à jour de l'adapter.
        updateAdpaterFT(false,Constantes.lesFTs);

        // Mise à jour de l'affichage.
        updateLayoutRecyclerView(tousFermer);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        if(provider != null){
            Log.v(provider.intern(),String.valueOf(status));
        }
    }

    @Override
    public void onProviderEnabled(String provider) {
        linearPlusProcheEnCours.setVisibility(View.VISIBLE);
        linearActiveGPS.setVisibility(View.GONE);
    }

    @Override
    public void onProviderDisabled(String provider) {
        linearPlusProcheEnCours.setVisibility(View.GONE);
        linearActiveGPS.setVisibility(View.VISIBLE);
    }

    /**
     * Mise à jour de l'agencement de la recyclerView en fonction de l'activation du GPS ou non et de l'orientation.
     * @param tousfermer indique si tous les ft sont fermé pour la journée.
     */
    private void updateLayoutRecyclerView(boolean tousfermer) {
        boolean affiClassique;
        int nombreColonne = Utils.getNbColonneForScreen(getContext());
        boolean gpsActive = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (gpsActive && !tousfermer) {
            linearActiveGPS.setVisibility(View.GONE);
            affiClassique = false;
            if (nombreColonne == 2) {
                recyclerViewListeFT.setLayoutManager(layoutManagerFT.buildGridLayoutPortrait());
            } else {
                recyclerViewListeFT.setLayoutManager(layoutManagerFT.buildGridLayoutLandscape());
            }
        } else {
            if(gpsActive){
                linearActiveGPS.setVisibility(View.GONE);
            }else{
                linearActiveGPS.setVisibility(View.VISIBLE);
            }
            affiClassique = true;
            recyclerViewListeFT.setLayoutManager(new GridLayoutManager(getContext(), nombreColonne));
        }

        // Mise à jour des FT dans l'adpater.
        updateAdpaterFT(affiClassique, Constantes.lesFTs);
    }

    /**
     * Mise à jour de la liste des FoodTrucks pendant la recherche.
     * @param recherche Le contenu de la recherche.
     */
    private void updateRechercheFT(String recherche) {

        boolean tousFermer = true;
        boolean isAffichageClassique;
        boolean vide = recherche.isEmpty();
        Calendar today =GestionnaireHoraire.createCalendarToday();
        int nombreColonne = Utils.getNbColonneForScreen(getContext());
        boolean activeGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        final List<FoodTruck> filteredModelList = vide ? Constantes.lesFTs : FoodTruck.filterListeFTs(Constantes.lesFTs, recherche);

        // Verification si tous les ft sont fermé
        for (FoodTruck ft : Constantes.lesFTs) {
            if(ft.isOpenToday() && ft.isDateBeforeLastHoraireFermeture(today)){
                tousFermer = false;
            }
        }

        // condition : gps activé, le chamsp de recherche est vide et au moins un ft n'est pas encore fermé aujoud'hui.
        if (activeGPS && vide && !tousFermer) {
            isAffichageClassique = false;
            if (nombreColonne == 2 ) {
                recyclerViewListeFT.setLayoutManager(layoutManagerFT.buildGridLayoutPortrait());
            } else {
                recyclerViewListeFT.setLayoutManager(layoutManagerFT.buildGridLayoutLandscape());
            }
        } else {
            isAffichageClassique = true;
            recyclerViewListeFT.setLayoutManager(new GridLayoutManager(getContext(), nombreColonne));
        }

        // Mise à jour des FT dans l'adpater.
        updateAdpaterFT(isAffichageClassique, filteredModelList);
    }

    /**
     * Mise à jour des Ft dans l'adapter.
     * @param isAffichageClassique si il s'agit d'un affichage classique.
     * @param listeFT la liste des fts.
     */
    private void updateAdpaterFT(boolean isAffichageClassique, List<FoodTruck> listeFT) {
        ListeFTAdapter listeFTAdapter = new ListeFTAdapter(listeFT, getContext(),isAffichageClassique);
        recyclerViewListeFT.setAdapter(listeFTAdapter);
        listeFTAdapter.notifyDataSetChanged();
    }

    /**
     * Active le GPS.
     */
    private void turnGPSOn() {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
    }

    /**
     * Supprimes la mise a jour de la postion du GPS.
     */
    private void removeUpdatesLocation() {
        if(locationManager != null){
            locationManager.removeUpdates(this);
        }
    }

    /**
     * Ajoute la mise à jour de la position du GPS.
     */
    private void addUpdatesLocation() {
        boolean gps = false;
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            linearPlusProcheEnCours.setVisibility(View.VISIBLE);
            linearActiveGPS.setVisibility(View.GONE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, Constantes.TIME_BETWEEN_UPDATE_GPS, 0, this);
            gps = true;
        }
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            linearPlusProcheEnCours.setVisibility(View.VISIBLE);
            linearActiveGPS.setVisibility(View.GONE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, Constantes.TIME_BETWEEN_UPDATE_GPS, 0, this);
            gps = true;
        }

        if(!gps){
            linearPlusProcheEnCours.setVisibility(View.GONE);
            linearActiveGPS.setVisibility(View.VISIBLE);
        }
    }

}
