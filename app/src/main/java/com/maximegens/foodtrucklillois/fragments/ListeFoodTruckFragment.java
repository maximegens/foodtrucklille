package com.maximegens.foodtrucklillois.fragments;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.maximegens.foodtrucklillois.R;
import com.maximegens.foodtrucklillois.adapters.ListeFTAdapter;
import com.maximegens.foodtrucklillois.beans.FoodTruck;
import com.maximegens.foodtrucklillois.beans.PlanningFoodTruck;
import com.maximegens.foodtrucklillois.interfaces.ListeFoodTruckFragmentCallback;
import com.maximegens.foodtrucklillois.network.Internet;
import com.maximegens.foodtrucklillois.network.RetreiveJSONListeFT;
import com.maximegens.foodtrucklillois.utils.Constantes;
import com.maximegens.foodtrucklillois.utils.GestionnaireHoraire;
import com.maximegens.foodtrucklillois.utils.GridLayoutManagerFoodTruck;
import com.maximegens.foodtrucklillois.utils.SortByDistanceFT;

import java.util.Collections;
import java.util.List;


/**
 * Class ListeFoodTruckFragment.
 * Fragment gérant l'affichage de la liste des Food trucks.
 */
public class ListeFoodTruckFragment extends Fragment implements LocationListener {

    private LocationManager locationManager;
    private ProgressBar loader;
    private LinearLayout linearActiveGPS;
    private Button activateGPS;
    private boolean isAffichageClassique;
    private TextView indicationChargementFT;
    private TextView indicationListeFTVide;
    private ListeFTAdapter listeFTAdapter;
    private RecyclerView recyclerViewListeFT;
    private GridLayoutManagerFoodTruck layoutManagerFT;
    private SwipeRefreshLayout swipeRefreshLayoutListeFT;
    private ListeFoodTruckFragmentCallback listeFoodTruckFragmentCallback;

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

        getActivity().setTitle(getString(R.string.title_liste_food_truck));
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        layoutManagerFT = new GridLayoutManagerFoodTruck(getContext());
        loader = (ProgressBar) view.findViewById(R.id.loader_download_ft);
        indicationChargementFT = (TextView) view.findViewById(R.id.indication_chargement_ft);
        indicationListeFTVide = (TextView) view.findViewById(R.id.indication_liste_ft_vide);
        recyclerViewListeFT = (RecyclerView) view.findViewById(R.id.recycler_view_liste_ft);
        swipeRefreshLayoutListeFT = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayoutListeFT);
        linearActiveGPS = (LinearLayout) view.findViewById(R.id.linear_gps_desactive);
        activateGPS = (Button) view.findViewById(R.id.button_activer_gps);
        recyclerViewListeFT.setHasFixedSize(true);

        // Creation de l'agencement des Foods Trucks en fonction de l'activation du GPS.
        updateLayoutRecyclerView();

        // Ajout des FTs interne dans l'adapters de la liste.
        listeFTAdapter = new ListeFTAdapter(Constantes.lesFTs, getContext(), isAffichageClassique);
        recyclerViewListeFT.setAdapter(listeFTAdapter);

        // Chargement des données des Foods trucks automatiquement (soit Online soit en local).
        if (Constantes.lesFTs == null || Constantes.lesFTs.isEmpty()) {
            loadDataFoodTruck(false);
        }

        /**
         * Implementation de la fonction du SwipeRefresh.
         */
        swipeRefreshLayoutListeFT.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh la liste des foods trucks
                refreshListeFT();
            }
        });

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
     * OnAttach.
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ListeFoodTruckFragmentCallback)
            listeFoodTruckFragmentCallback = (ListeFoodTruckFragmentCallback) context;
    }

    /**
     * OnDetach.
     */
    @Override
    public void onDetach() {
        super.onDetach();
        listeFoodTruckFragmentCallback = null;
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
     * Mise à jour de la liste des FoodTrucks pendant la recherche.
     * @param recherche Le contenu de la recherche.
     */
    private void updateRechercheFT(String recherche) {
        boolean vide = recherche.isEmpty();
        final List<FoodTruck> filteredModelList = vide ? Constantes.lesFTs : FoodTruck.filterListeFTs(Constantes.lesFTs, recherche);

        // Creation de l'agencement des Foods Trucks en fonction de l'orientation ( Portrait : par 2 - Paysage : par 3)
        int nombreColonne = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? 2 : 3;
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && vide) {
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
        listeFTAdapter.setFTs(filteredModelList, isAffichageClassique);
        listeFTAdapter.notifyDataSetChanged();
    }

    /**
     * Chargement des données des Food trucks par Internet ou en interne si il n'existe pas de connexion.
     */
    private void loadDataFoodTruck(boolean bySwiperefresh) {
        RetreiveJSONListeFT retreiveJSONListeFT = new RetreiveJSONListeFT(listeFTAdapter, getContext(), bySwiperefresh, indicationListeFTVide, getContext());
        retreiveJSONListeFT.setProgressBar(loader, indicationChargementFT);
        retreiveJSONListeFT.setswipeRefresh(swipeRefreshLayoutListeFT);
        retreiveJSONListeFT.execute(Internet.isNetworkAvailable(getActivity().getApplicationContext()));
    }

    /**
     * Rafraichie la liste des Foods trucks.
     */
    private void refreshListeFT() {
        loadDataFoodTruck(true);
    }

    /**
     * Supprimes la mise a jour de la postion du GPS.
     */
    public void removeUpdatesLocation() {
        if(locationManager != null){
            // Demande de permission pour Android 6.0 (API 23).
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.removeUpdates(this);
        }
    }

    /**
     * Ajoute la mise à jour de la position du GPS.
     */
    public void addUpdatesLocation() {
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            // Demande de permission pour Android 6.0 (API 23).
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, Constantes.TIME_BETWEEN_UPDATE_GPS, 0, this);
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, Constantes.TIME_BETWEEN_UPDATE_GPS, 0, this);
    }



    @Override
    public void onLocationChanged(Location location) {

        // Demande de permission pour Android 6.0 (API 23).
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        // Mise à jour de l'affichage.
        updateLayoutRecyclerView();

        //TODO a mettre dans une asyntasck
        // Pour chaque Food Truck on calcul sa distance par rapport à l'utilisateur.
        for (FoodTruck ft : Constantes.lesFTs) {

            //On verifie si le food truck est ouvert aujoud'hui.
            if (ft.isOpenToday()) {
                Location loc = new Location("");
                PlanningFoodTruck planning = ft.getPlaningToday();
                if (planning != null) {
                    if (GestionnaireHoraire.isMidiOrBeforeMidi()) {
                        if (planning.getMidi() != null && planning.getMidi().getAdresses() != null && planning.getMidi().getAdresses().get(0) != null
                                && planning.getMidi().getAdresses().get(0).getLatitude() != null && planning.getMidi().getAdresses().get(0).getLongitude() != null) {
                            loc.setLatitude(Double.parseDouble(planning.getMidi().getAdresses().get(0).getLatitude()));
                            loc.setLongitude(Double.parseDouble(planning.getMidi().getAdresses().get(0).getLongitude()));
                        }
                    } else if (GestionnaireHoraire.isSoirOrBeforeSoirButAfterMidi()) {
                        if (planning.getSoir() != null && planning.getSoir().getAdresses() != null && planning.getSoir().getAdresses().get(0) != null
                                && planning.getSoir().getAdresses().get(0).getLatitude() != null && planning.getSoir().getAdresses().get(0).getLongitude() != null) {
                            loc.setLatitude(Double.parseDouble(planning.getSoir().getAdresses().get(0).getLatitude()));
                            loc.setLongitude(Double.parseDouble(planning.getSoir().getAdresses().get(0).getLongitude()));
                        }
                    }
                    // On calcul et on ajout la distance depuis l'utilisateur au Food truck.
                    ft.setDistanceFromUser(location.distanceTo(loc));
                }
            } else {
                ft.setDistanceFromUser(Constantes.FT_FERMER_DISTANCE);
            }
        }

        // On trie la liste des Food trucks par distance.
        Collections.sort(Constantes.lesFTs, new SortByDistanceFT());
        listeFTAdapter.setFTs(Constantes.lesFTs, false);
        listeFTAdapter.notifyDataSetChanged();

    }

    /**
     * Mise à jour de l'agencement de la recyclerView en fonction de l'activation du GPS ou non et de l'orientation.
     */
    private void updateLayoutRecyclerView() {
        int nombreColonne = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? 2 : 3;
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            linearActiveGPS.setVisibility(View.GONE);
            isAffichageClassique = false;
            if (nombreColonne == 2) {
                recyclerViewListeFT.setLayoutManager(layoutManagerFT.buildGridLayoutPortrait());
            } else {
                recyclerViewListeFT.setLayoutManager(layoutManagerFT.buildGridLayoutLandscape());
            }
        } else {
            linearActiveGPS.setVisibility(View.VISIBLE);
            isAffichageClassique = true;
            recyclerViewListeFT.setLayoutManager(new GridLayoutManager(getContext(), nombreColonne));
        }
    }

    /**
     * Active le GPS.
     */
    public void turnGPSOn() {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
    }


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        if(provider != null){
            Log.v(provider.intern(),String.valueOf(status));
        }
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }
}
