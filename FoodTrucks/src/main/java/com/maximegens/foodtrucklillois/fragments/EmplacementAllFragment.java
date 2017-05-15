package com.maximegens.foodtrucklillois.fragments;


import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.maximegens.foodtrucklillois.R;
import com.maximegens.foodtrucklillois.adapters.InfoWindowMarkerMapAdapter;
import com.maximegens.foodtrucklillois.beans.AdresseFoodTruck;
import com.maximegens.foodtrucklillois.beans.FoodTruck;
import com.maximegens.foodtrucklillois.beans.PlanningFoodTruck;
import com.maximegens.foodtrucklillois.broadcastReceiver.NetworkBroadcast;
import com.maximegens.foodtrucklillois.network.Internet;
import com.maximegens.foodtrucklillois.utils.Constantes;
import com.maximegens.foodtrucklillois.utils.GestionnaireHoraire;
import com.maximegens.foodtrucklillois.utils.PicassoMarker;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class EmplacementAllFragment extends Fragment implements OnMapReadyCallback, AdapterView.OnItemSelectedListener {

    private ArrayAdapter<FoodTruck> adapterFT;
    private SupportMapFragment map;
    private TextView noConnexion;
    private int jourSelection;
    private int jourActuel;
    private FoodTruck ftSelection = null;
    public static final Set<Target> protectedFromGarbageCollectorTargets = new HashSet<>();
    private Spinner spinnerMap;
    private Spinner spinnerMapJour;
    private BroadcastReceiver broadcastReceiver;

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
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        (getActivity()).setTitle(getContext().getString(R.string.title_map_all));

        map = SupportMapFragment.newInstance();

        // Creation du Spinner pour afficher la liste de Food Trucks.
        spinnerMap = (Spinner) view.findViewById(R.id.spinner_map_fts);
        List<FoodTruck> lesFts = new ArrayList<>();
        lesFts.add(new FoodTruck(Constantes.TOUS_FT));
        for (FoodTruck ft : Constantes.lesFTs) {
            lesFts.add(ft);
        }
        adapterFT = new ArrayAdapter<>(getContext(), R.layout.layout_drop_title_black, lesFts);
        adapterFT.setDropDownViewResource(R.layout.layout_drop_list);
        noConnexion = (TextView) view.findViewById(R.id.no_connexion_map_all);
        spinnerMap.setAdapter(adapterFT);

        // Creation du spinner pour la liste des jours.
        spinnerMapJour = (Spinner) view.findViewById(R.id.spinner_map_fts_jour);
        ArrayAdapter<CharSequence> adapterJour = ArrayAdapter.createFromResource(getContext(), R.array.semaine_array_jour, R.layout.layout_drop_title_black);
        adapterJour.setDropDownViewResource(R.layout.layout_drop_list);
        spinnerMapJour.setAdapter(adapterJour);
        jourActuel = GestionnaireHoraire.getNumeroJourDansLaSemaine();


        // On affiche la map si le device posséde une connexion internet.
        if (Internet.isNetworkAvailable(getContext()) && map != null) {
            afficheMap();
        } else {
            masqueMap();
        }

        // Creation du BroadcastReceiver pour vérifier la connexion internet.
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (Internet.isNetworkAvailable(context)) {
                    afficheMap();
                } else {
                    masqueMap();
                }
            }
        };
        getActivity().registerReceiver(broadcastReceiver, new IntentFilter(NetworkBroadcast.INTERNET_DETECTED));

    }

    /**
     * Masque la map dans le fragment.
     */
    private void masqueMap() {
        if (map != null && map.getView() != null) {
            map.getView().setVisibility(View.GONE);
        }
        noConnexion.setVisibility(View.VISIBLE);
        spinnerMap.setVisibility(View.GONE);
        spinnerMapJour.setVisibility(View.GONE);
    }

    /**
     * Affichage la map dans le fragment.
     */
    private void afficheMap() {
        if (map != null && map.getView() != null) {
            map.getView().setVisibility(View.VISIBLE);
        } else {
            final FragmentManager fm = getChildFragmentManager();
            fm.beginTransaction().replace(R.id.framelayout_map_all, map).commit();
        }
        noConnexion.setVisibility(View.GONE);
        spinnerMap.setVisibility(View.VISIBLE);
        spinnerMapJour.setVisibility(View.VISIBLE);
        map.getMapAsync(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (broadcastReceiver != null) {
            getActivity().unregisterReceiver(broadcastReceiver);
            broadcastReceiver = null;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        centreMap(googleMap);
        spinnerMapJour.setSelection(jourActuel);
        gestionSpinnerFoodTruck(0, googleMap);
        gestionSpinnerJour(jourActuel, googleMap);
        spinnerMapJour.setOnItemSelectedListener(this);
        spinnerMap.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        // Récuperation de la googleMap
        GoogleMap gMapItem = null;
        if (map != null) {
            gMapItem = map.getMap();
        }
        // Suppresion des précédents markers.
        if (gMapItem != null) {
            gMapItem.clear();
        }

        // Récupération du spinner.
        Spinner spinner = (Spinner) parent;

        // Spinner gérant les jours.
        if (spinner.getId() == R.id.spinner_map_fts) {
            gestionSpinnerFoodTruck(position, gMapItem);
        }
        // Spinner gérant les FTs.
        else if (spinner.getId() == R.id.spinner_map_fts_jour) {
            gestionSpinnerJour(position, gMapItem);
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
        if (ftSelection != null && ftSelection.getNom().equals(Constantes.TOUS_FT) && gMapItem != null) {
            // Tous les jours
            if (jourSelection == 0) {
                for (FoodTruck ft : Constantes.lesFTs) {
                    if (!ft.isAucuneAdresse()) {
                        for (PlanningFoodTruck planning : ft.getPlanning()) {
                            traitementByPlanning(planning, gMapItem, ft);
                        }
                    }
                }
            } else { // Un jour de selectionné
                for (FoodTruck ft : Constantes.lesFTs) {
                    if (!ft.isAucuneAdresse()) {
                        PlanningFoodTruck planning = ft.getPlanningByJour(jourSelection);
                        traitementByPlanning(planning, gMapItem, ft);
                    }
                }
            }

        } else if (ftSelection != null && !ftSelection.getNom().equals(Constantes.TOUS_FT) && gMapItem != null) { // Un ft de selectionné
            // Tous les jours
            if (jourSelection == 0) {
                for (PlanningFoodTruck planning : ftSelection.getPlanning()) {
                    traitementByPlanning(planning, gMapItem, ftSelection);
                }
            } else { // Un jour de selectionné
                PlanningFoodTruck planning = ftSelection.getPlanningByJour(jourSelection);
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
        if (jourSelection == 0 && gMapItem != null) {
            // Tous les ft
            if (ftSelection != null && ftSelection.getNom().equals(Constantes.TOUS_FT)) {
                for (FoodTruck left : Constantes.lesFTs) {
                    if (!left.isAucuneAdresse()) {
                        for (PlanningFoodTruck planning : left.getPlanning()) {
                            parcoursAdresses(left, gMapItem, planning);
                        }
                    }
                }
            } else if(ftSelection != null) { // Un ft de selectionné
                for (PlanningFoodTruck planning : ftSelection.getPlanning()) {
                    parcoursAdresses(ftSelection, gMapItem, planning);
                }
            }

            // Un jour de selectionné
        } else if (jourSelection != 0 && gMapItem != null) {
            // Tous les ft
            if (ftSelection != null && ftSelection.getNom().equals(Constantes.TOUS_FT)) {
                for (FoodTruck ft : Constantes.lesFTs) {
                    if (!ft.isAucuneAdresse()) {
                        PlanningFoodTruck planning = ft.getPlanningByJour(jourSelection);
                        parcoursAdresses(ft, gMapItem, planning);
                    }
                }
            } else if(ftSelection != null){ // Un ft de selectionné
                PlanningFoodTruck planning = ftSelection.getPlanningByJour(jourSelection);
                parcoursAdresses(ftSelection, gMapItem, planning);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }


    /**
     * Parcours les adresses du food truck pour ajouter un marker sur la map permettant de visualiser l'emplacement du FT.
     * @param ft le food truck.
     * @param gMapItem La googleMap.
     * @param planning Le planning du FT.
     */
    private void parcoursAdresses(FoodTruck ft, GoogleMap gMapItem, PlanningFoodTruck planning) {
        if (planning != null) {
            // Parcours des adresses du food truck pour le midi.
            if (planning.getMidi() != null && planning.getMidi().getAdresses() != null) {
                for (AdresseFoodTruck adresse : planning.getMidi().getAdresses()) {
                    ajouteMarker(gMapItem, ft, planning, adresse, Constantes.MIDI);
                }
            }
            // Parcours des adresses du food truck pour le soir.
            if (planning.getSoir() != null && planning.getSoir().getAdresses() != null) {
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
        if (planning != null) {
            // Parcours des adresses du food truck pour le midi.
            if (planning.getMidi() != null && planning.getMidi().getAdresses() != null) {
                for (AdresseFoodTruck adresse : planning.getMidi().getAdresses()) {
                    ajouteMarker(gMapItem, ft, planning, adresse, Constantes.MIDI);
                }
            }
            // Parcours des adresses du food truck pour le soir.
            if (planning.getSoir() != null && planning.getSoir().getAdresses() != null) {
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
    private void ajouteMarker(GoogleMap googleMap, FoodTruck ft, PlanningFoodTruck planning, AdresseFoodTruck adresse, String periode) {

        // Verification qu'il existe une latitude et une longitude de renseigné pour pouvoir l'afficher.
        if (adresse.getLatitude() != null && adresse.getLongitude() != null) {

            final MarkerOptions markerOptions = new MarkerOptions();

            Double latitude = Double.valueOf(adresse.getLatitude());
            Double longitude = Double.valueOf(adresse.getLongitude());

            // Ajout du nom et de la position du Food Truck.
            markerOptions.title(ft.getNom());
            markerOptions.position(new LatLng(latitude, longitude));

            // Creation de l'icone.
            if (ft.getLogo() != null && getResources().getIdentifier(ft.getLogo(), "mipmap", getContext().getPackageName()) != 0) {

                int resID = getResources().getIdentifier(ft.getLogo(), "mipmap", getContext().getPackageName());

                Marker marker = googleMap.addMarker(markerOptions);
                if (marker != null) {
                    PicassoMarker picassoMarker = new PicassoMarker(marker, ft, planning, adresse, periode);
                    protectedFromGarbageCollectorTargets.add(picassoMarker);

                    ImageView image = new ImageView(getActivity());
                    image.setImageResource(resID);

                    DisplayMetrics displaymetrics = new DisplayMetrics();
                    getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
                    int newHeight = displaymetrics.heightPixels /20;
                    int orgWidth = image.getDrawable().getIntrinsicWidth();
                    int orgHeight = image.getDrawable().getIntrinsicHeight();
                    Double taille = Math.floor((orgWidth * newHeight) / orgHeight);
                    int newWidth = taille.intValue();

                    Picasso.with(getActivity()).load(resID).resize(newWidth, newHeight).centerCrop().into(picassoMarker);
                }
            } else {
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_truck));
            }

            // Ajout de l'infoBulle.
            googleMap.setInfoWindowAdapter(new InfoWindowMarkerMapAdapter(getContext()));
        }
    }


    /**
     * Centre la Google Map sur Marcq en Baroeul.
     * @param googleMap la google map
     */
    private void centreMap(GoogleMap googleMap) {
        //Ajoute un marker sur Marcq En Baroeul, permet de centrer la vue sur l'agglomeration Lilloise.
        LatLng CENTRE = new LatLng(Double.parseDouble(Constantes.GPS_CENTRE_CARTE_MARC_BAROEUL_LATITUDE), Double.parseDouble(Constantes.GPS_CENTRE_CARTE_MARC_BAROEUL_LONGITUDE));

        // Affiche le bouton permettant de localiser l'utilisateur.
        googleMap.setMyLocationEnabled(true);

        // Centre la google map avec animation de zoom.
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CENTRE, 11));
    }
}
