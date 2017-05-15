package com.maximegens.foodtrucklillois.fragments;


import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Browser;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.maximegens.foodtrucklillois.App;
import com.maximegens.foodtrucklillois.FoodTruckActivity;
import com.maximegens.foodtrucklillois.R;
import com.maximegens.foodtrucklillois.adapters.InfoWindowMarkerMapAdapter;
import com.maximegens.foodtrucklillois.beans.AdresseFoodTruck;
import com.maximegens.foodtrucklillois.beans.FoodTruck;
import com.maximegens.foodtrucklillois.beans.PlanningFoodTruck;
import com.maximegens.foodtrucklillois.broadcastReceiver.NetworkBroadcast;
import com.maximegens.foodtrucklillois.network.Internet;
import com.maximegens.foodtrucklillois.utils.Constantes;

import java.util.Calendar;


public class EmplacementFoodTruckFragment extends Fragment implements OnMapReadyCallback, AdapterView.OnItemSelectedListener {

    public static final String TITLE = "Map";
    private TextView noConnexion;
    private RelativeLayout lienPageFacebook;
    private FoodTruck ft = null;
    private SupportMapFragment mapFragment;
    private static View view;
    private BroadcastReceiver broadcastReceiverInt;

    /**
     * Creation du Fragment.
     *
     * @return Une instance de EmplacementFoodTruckFragment.
     */
    public static EmplacementFoodTruckFragment newInstance(FoodTruck ft) {
        EmplacementFoodTruckFragment fragment = new EmplacementFoodTruckFragment();
        Bundle args = new Bundle();
        args.putParcelable(FoodTruckActivity.KEY_FOODTRUCK_SELECTIONNER, ft);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.setHasOptionsMenu(true);

        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null) {
                parent.removeView(view);
            }
        }
        try {
            view = inflater.inflate(R.layout.fragment_emplacement_ft, container, false);
        } catch (InflateException e) {
         /* map is already there, just return view as it is */
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        App.tracker.setScreenName(getString(R.string.ga_title_map_food_truck));

        if (getArguments() != null) {
            ft = getArguments().getParcelable(FoodTruckActivity.KEY_FOODTRUCK_SELECTIONNER);
        }

        noConnexion = (TextView) view.findViewById(R.id.no_connexion_map);
        lienPageFacebook = (RelativeLayout) view.findViewById(R.id.lien_page_facebook);
        Button buttonPageFacebook = (Button) view.findViewById(R.id.button_page_facebook);
        mapFragment = SupportMapFragment.newInstance();

        // On affiche la map si le device posséde une connexion internet.
        if (Internet.isNetworkAvailable(getContext())) {
            if (ft.isAucuneAdresse()) {
                affichePageFacebook();
            } else {
                afficheMap();
            }
        } else {
            masqueMap();
        }

        // Creation du BroadcastReceiver pour vérifier la connexion internet.
        broadcastReceiverInt = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (Internet.isNetworkAvailable(context)) {
                    if (ft.isAucuneAdresse()) {
                        affichePageFacebook();
                    } else {
                        afficheMap();
                    }
                } else {
                    masqueMap();
                }
            }
        };
        getActivity().registerReceiver(broadcastReceiverInt, new IntentFilter(NetworkBroadcast.INTERNET_DETECTED));

        /** Ouverture de la page Facebook du Food Truck **/
        buttonPageFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ft.getUrlPageFacebook() != null) {
                    String url = ft.getUrlPageFacebook();
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    intent.putExtra(Browser.EXTRA_APPLICATION_ID, getActivity().getApplicationContext().getPackageName());
                    startActivity(intent);
                }
            }
        });
    }

    /**
     * Masque la map dans le fragment.
     */
    private void masqueMap() {
        if (mapFragment != null && mapFragment.getView() != null) {
            mapFragment.getView().setVisibility(View.GONE);
        }
        noConnexion.setVisibility(View.VISIBLE);
        lienPageFacebook.setVisibility(View.GONE);
    }

    /**
     * Affiche une information comme quoi le food truck ne posséde pas d'adresse précise et qu'il faut consulter la page facebook
     */
    private void affichePageFacebook() {
        if (mapFragment != null && mapFragment.getView() != null) {
            mapFragment.getView().setVisibility(View.GONE);
        }
        noConnexion.setVisibility(View.GONE);
        lienPageFacebook.setVisibility(View.VISIBLE);
    }

    /**
     * Affichage la map dans le fragment.
     */
    private void afficheMap() {
        if (mapFragment != null && mapFragment.getView() != null) {
            mapFragment.getView().setVisibility(View.VISIBLE);
        } else {

            final FragmentManager fm = getChildFragmentManager();
            fm.beginTransaction().replace(R.id.framelayout_map_ft, mapFragment).commit();

        }
        noConnexion.setVisibility(View.GONE);
        lienPageFacebook.setVisibility(View.GONE);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (broadcastReceiverInt != null) {
            getActivity().unregisterReceiver(broadcastReceiverInt);
            broadcastReceiverInt = null;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //Ajoute un marker sur Marcq En Baroeul, permet de centrer la vue sur l'agglomeration Lilloise.
        LatLng CENTRE = new LatLng(Double.parseDouble(Constantes.GPS_CENTRE_CARTE_MARC_BAROEUL_LATITUDE), Double.parseDouble(Constantes.GPS_CENTRE_CARTE_MARC_BAROEUL_LONGITUDE));

        googleMap.setMyLocationEnabled(true);

        // Centre la google map avec animation de zoom.
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CENTRE, 11));
    }

    /**
     * Ajout d'un marker sur la google map pour repérer l'emplacement du Food Truck.
     *
     * @param googleMap LA google map.
     * @param planning  Le planning.
     * @param adresse   L'objet contenant l'adresse du Food Truck.
     * @param periode   La periode en cours : midi ou soir.
     */
    private void ajouteMarker(GoogleMap googleMap, PlanningFoodTruck planning, AdresseFoodTruck adresse, String periode) {

        // Verification qu'il existe une latitude et une longitude de renseigné pour pouvoir l'afficher.
        if (adresse.getLatitude() != null && adresse.getLongitude() != null) {

            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.title(ft.getNom());

            Double latitude = Double.valueOf(adresse.getLatitude());
            Double longitude = Double.valueOf(adresse.getLongitude());

            markerOptions.title(ft.getNom());
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_truck));
            markerOptions.position(new LatLng(latitude, longitude));

            StringBuilder snippet = new StringBuilder();
            snippet.append("Ouvert uniquement le ").append(planning.getNomJour()).append(" ").append(periode);
            // code spécial pour effet gourmet et ses semaines impaires.
            if(ft != null && ft.getId() == 113 && (planning.getNumJour() == 2 || planning.getNumJour() == 5)){
                snippet.append(" et les semaines impaires");
            }
            if (adresse.getAdresse() != null) {
                snippet.append(Constantes.RETOUR_CHARIOT).append(Constantes.RETOUR_CHARIOT).append(adresse.getAdresse());
            }
            markerOptions.snippet(snippet.toString());
            googleMap.setInfoWindowAdapter(new InfoWindowMarkerMapAdapter(getContext()));
            googleMap.addMarker(markerOptions);
        }
    }

    /**
     * Traitement des emplacements en fonction du planning passé en paramétre.
     *
     * @param planning le planning a traiter.
     */
    private void traitementByPlanning(PlanningFoodTruck planning, GoogleMap gMapItem) {
        // Parcours des adresses du food truck pour le midi.
        if (planning.getMidi() != null) {
            for (AdresseFoodTruck adresse : planning.getMidi().getAdresses()) {
                ajouteMarker(gMapItem, planning, adresse, Constantes.MIDI);
            }
        }
        // Parcours des adresses du food truck pour le soir.
        if (planning.getSoir() != null) {
            for (AdresseFoodTruck adresse : planning.getSoir().getAdresses()) {
                ajouteMarker(gMapItem, planning, adresse, Constantes.SOIR);
            }
        }
    }

    /**
     * Gestion du menu.
     *
     * @param menu     Le menu.
     * @param inflater L'objet pour inflater.
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_map, menu);
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem item = menu.findItem(R.id.action_spinner_map_ft);
        Spinner spinnerMap = (Spinner) MenuItemCompat.getActionView(item);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.semaine_array_jour, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.layout_drop_list);
        spinnerMap.setAdapter(adapter);
        spinnerMap.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        GoogleMap googleMap = null;

        if(mapFragment != null){
            googleMap = mapFragment.getMap();
        }

        if (googleMap != null) {

            // Suppresion des précédents markers.
            googleMap.clear();

            if (position == 0) {
                for (PlanningFoodTruck planning : ft.getPlanning()) {
                    traitementByPlanning(planning, googleMap);
                }
            } else {
                PlanningFoodTruck planning = ft.getPlanningByJour(position);
                traitementByPlanning(planning, googleMap);
            }
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
