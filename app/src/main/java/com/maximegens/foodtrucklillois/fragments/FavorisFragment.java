package com.maximegens.foodtrucklillois.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
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
import com.maximegens.foodtrucklillois.beans.FoodTruckApp;
import com.maximegens.foodtrucklillois.utils.Constantes;
import com.maximegens.foodtrucklillois.utils.GestionJsonAPI;
import com.maximegens.foodtrucklillois.utils.GridLayoutManagerFoodTruck;
import com.maximegens.foodtrucklillois.utils.SortListeFT;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FavorisFragment extends Fragment {

    private ListeFTAdapter listeFTAdapter;
    private RecyclerView recyclerViewFavoris;
    private TextView aucunFavoris;

    /**
     * Creation du Fragment.
     * @return Une instance de FavorisFoodTruckFragment.
     */
    public static FavorisFragment newInstance() {
        FavorisFragment fragment = new FavorisFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favoris_ft, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle(getString(R.string.title_favoris));

        recyclerViewFavoris = (RecyclerView) view.findViewById(R.id.recycler_view_liste_ft_favorite);
        aucunFavoris = (TextView) view.findViewById(R.id.aucun_favoris);
        recyclerViewFavoris.setHasFixedSize(true);

        // Creation de l'adapter.
        listeFTAdapter = new ListeFTAdapter(null, getContext(),true);
        recyclerViewFavoris.setAdapter(listeFTAdapter);

        // Récupération des Foods Trucks mis en favoris.
        SharedPreferences favorites = getContext().getSharedPreferences(Constantes.FAVORITE_SHAREPREFERENCE, 0);
        List<FoodTruck> lesFTFavoris = new ArrayList<>();
        for(Map.Entry<String,?> entry : favorites.getAll().entrySet()){
            for(FoodTruck ft : Constantes.lesFTs){
                int id = Integer.valueOf(entry.getKey());
                if(ft.getId() == id){
                    lesFTFavoris.add(ft);
                }
            }
        }

        //Tri des Foods trucks par ouverture et par nom
        Collections.sort(Constantes.lesFTs, new SortListeFT(false));

        // Charge les favoris dans l'adapter et demande un affichage classique.
        listeFTAdapter.setIsAffichageClassique(true);
        listeFTAdapter.setFTs(lesFTFavoris);

        // Creation de l'agencement des Foods Trucks en fonction de l'orientation ( Portrait : par 2 - Paysage : par 3)
        int nombreColonne = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? 2 : 3;
        recyclerViewFavoris.setLayoutManager(new GridLayoutManager(getContext(), nombreColonne));

        // On indique a l'utilisateur qu'il n'y a pas de Favoris si la liste est vide.
        if(lesFTFavoris.isEmpty()){
            aucunFavoris.setVisibility(View.VISIBLE);
            recyclerViewFavoris.setVisibility(View.GONE);
        }else{
            aucunFavoris.setVisibility(View.GONE);
            recyclerViewFavoris.setVisibility(View.VISIBLE);
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

}
