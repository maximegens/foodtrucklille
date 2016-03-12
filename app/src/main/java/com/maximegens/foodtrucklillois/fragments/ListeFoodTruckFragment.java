package com.maximegens.foodtrucklillois.fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.maximegens.foodtrucklillois.R;
import com.maximegens.foodtrucklillois.adapters.ListeFTAdapter;
import com.maximegens.foodtrucklillois.beans.FoodTruck;
import com.maximegens.foodtrucklillois.interfaces.ListeFoodTruckFragmentCallback;
import com.maximegens.foodtrucklillois.network.Internet;
import com.maximegens.foodtrucklillois.network.RetreiveJSONListeFT;
import com.maximegens.foodtrucklillois.utils.Constantes;
import com.maximegens.foodtrucklillois.utils.GridLayoutManagerFoodTruck;

import java.util.List;


/**
 * Class ListeFoodTruckFragment.
 * Fragment gérant l'affichage de la liste des Food trucks.
 */
public class ListeFoodTruckFragment extends Fragment{

    private ProgressBar loader;
    private TextView indicationChargementFT;
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
        return inflater.inflate(R.layout.fragment_liste_ft,container,false);
    }

    /**
     * Le fragment possède enfin une vue, nous pouvons récupérer nos widgets (TextView, etc.) puis les modifier.
     * @param view La view crée.
     * @param savedInstanceState savedInstanceState.
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        layoutManagerFT = new GridLayoutManagerFoodTruck(getContext());
        loader = (ProgressBar)view.findViewById(R.id.loader_download_ft);
        indicationChargementFT = (TextView) view.findViewById(R.id.indication_chargement_ft);
        recyclerViewListeFT = (RecyclerView) view.findViewById(R.id.recycler_view_liste_ft);
        swipeRefreshLayoutListeFT = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayoutListeFT);
        recyclerViewListeFT.setHasFixedSize(true);

        // Ajout des FTs interne dans l'adapters de la liste.
        listeFTAdapter = new ListeFTAdapter(Constantes.lesFTs, getContext());
        recyclerViewListeFT.setAdapter(listeFTAdapter);

        // Creation de l'agencement des Foods Trucks.
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerViewListeFT.setLayoutManager(layoutManagerFT.buildGridLayoutPortrait());
        }else{
            recyclerViewListeFT.setLayoutManager(layoutManagerFT.buildGridLayoutLandscape());
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

        // Chargement des données des Foods trucks automatiquement.
        loadDataFoodTruck(false);

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ListeFoodTruckFragmentCallback)
            listeFoodTruckFragmentCallback = (ListeFoodTruckFragmentCallback) context;
    }

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
    }

    /**
     * Chargement des données des Food trucks par Internet ou en interne si il n'existe pas de connexion.
     */
    private void loadDataFoodTruck(boolean bySwiperefresh) {
        RetreiveJSONListeFT retreiveJSONListeFT = new RetreiveJSONListeFT(listeFTAdapter,getContext(),bySwiperefresh);
        retreiveJSONListeFT.setProgressBar(loader,indicationChargementFT);
        retreiveJSONListeFT.setswipeRefresh(swipeRefreshLayoutListeFT);
        retreiveJSONListeFT.execute(Internet.isNetworkAvailable(getActivity().getApplicationContext()));
    }

    /**
     * Rafraichie la liste des Foods trucks.
     */
    private void refreshListeFT(){
        loadDataFoodTruck(true);
    }


    /**
     * Mise à jour de la liste des FoodTrucks pendant la recherche.
     * @param recherche Le contenu de la recherche.
     */
    private void updateRechercheFT(String recherche){
        boolean vide = recherche.isEmpty();

        final List<FoodTruck> filteredModelList = vide ? Constantes.lesFTs : FoodTruck.filterListeFTs(Constantes.lesFTs, recherche);
        listeFTAdapter.setFTs(filteredModelList,true);

        // Creation de l'agencement des Foods Trucks.
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            if(vide){
                recyclerViewListeFT.setLayoutManager(layoutManagerFT.buildGridLayoutPortrait());
            }else{
                recyclerViewListeFT.setLayoutManager(layoutManagerFT.buildGridLayoutPortraitAfterRecherche());
            }
        } else {
            if(vide){
                recyclerViewListeFT.setLayoutManager(layoutManagerFT.buildGridLayoutLandscape());
            }else{
                recyclerViewListeFT.setLayoutManager(layoutManagerFT.buildGridLayoutLandscapeAfterRecherche());
            }
        }

        // Mise à jour des FT dans l'adpater.
        listeFTAdapter.setFTs(filteredModelList, !vide);
        listeFTAdapter.notifyDataSetChanged();
    }


}
