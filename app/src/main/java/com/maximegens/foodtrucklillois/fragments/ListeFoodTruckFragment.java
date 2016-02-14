package com.maximegens.foodtrucklillois.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maximegens.foodtrucklillois.R;
import com.maximegens.foodtrucklillois.adapters.ListeFTAdapter;
import com.maximegens.foodtrucklillois.beans.FoodTruck;

import java.util.ArrayList;
import java.util.List;

/**
 * Class ListeFoodTruckFragment.
 */
public class ListeFoodTruckFragment extends Fragment {

    ListeFoodTruckFragmentCallback listeFoodTruckFragmentCallback;
    private RecyclerView recyclerViewListeFT;
    private List<FoodTruck> lesFT = new ArrayList<>();

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

        ajouterFT();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            //définit l'agencement des cellules de façon verticale.
            recyclerViewListeFT.setLayoutManager(new LinearLayoutManager(getContext()));
        }else{
            //définit l'agencement avec 2 cellules par ligne.
            recyclerViewListeFT.setLayoutManager(new GridLayoutManager(getContext(),2));
        }

        recyclerViewListeFT.setAdapter(new ListeFTAdapter(lesFT, getContext()));
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


    private void ajouterFT(){
        lesFT.add(new FoodTruck("El Camion","4 rue barberousse","logo_el_camion"));
        lesFT.add(new FoodTruck("Chez Greg","58 rue de l'impasse","logo_chez_greg"));
        lesFT.add(new FoodTruck("La Marmitte Mobile","58 rue de l'impasse","logo_la_marmitte_mobile"));
        lesFT.add(new FoodTruck("Peko Peko","55 avenue de la République","logo_peko_peko"));
        lesFT.add(new FoodTruck("Le Comptoir Volant","12 place Sébastopol","logo_le_comptoir_volant"));
        lesFT.add(new FoodTruck("Bistro Truck","12 place Sébastopol","logo_bistro_truck"));
    }

}
