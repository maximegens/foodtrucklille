package com.maximegens.foodtrucklillois.fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.maximegens.foodtrucklillois.R;
import com.maximegens.foodtrucklillois.adapters.ListeFTAdapter;
import com.maximegens.foodtrucklillois.beans.FoodTruck;
import com.maximegens.foodtrucklillois.utils.Constantes;

import java.util.ArrayList;
import java.util.List;

/**
 * Class ListeFoodTruckFragment.
 */
public class ListeFoodTruckFragment extends Fragment {

    private ListeFoodTruckFragmentCallback listeFoodTruckFragmentCallback;
    private RecyclerView recyclerViewListeFT;
    private ListeFTAdapter listeFTAdapter;

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
        recyclerViewListeFT = (RecyclerView) view.findViewById(R.id.recyclerView);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            //définit l'agencement des cellules de façon verticale.
            recyclerViewListeFT.setLayoutManager(new LinearLayoutManager(getContext()));
        }else{
            //définit l'agencement avec 2 cellules par ligne.
            recyclerViewListeFT.setLayoutManager(new GridLayoutManager(getContext(),2));
        }
        listeFTAdapter = new ListeFTAdapter(Constantes.lesFTs, getContext());
        recyclerViewListeFT.setAdapter(listeFTAdapter);
    }


    /**
     * Interface permettant de communiquer avec l'activity.
     */
    public interface ListeFoodTruckFragmentCallback{

        /**
         * Clique sur un Fodd Truck
         */
        void onFoodTruckClicked();
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_liste_ft, menu);
        super.onCreateOptionsMenu(menu, inflater);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.onActionViewCollapsed();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                final List<FoodTruck> filteredModelList = filter(Constantes.lesFTs, s);
                listeFTAdapter.setModels(filteredModelList);
                listeFTAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    private List<FoodTruck> filter(List<FoodTruck> models, String query) {
        query = query.toLowerCase();

        final List<FoodTruck> filteredModelList = new ArrayList<>();
        for (FoodTruck model : models) {
            final String text = model.getNom().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

}
