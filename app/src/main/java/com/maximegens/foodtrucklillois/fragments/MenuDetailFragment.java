package com.maximegens.foodtrucklillois.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.maximegens.foodtrucklillois.R;
import com.maximegens.foodtrucklillois.adapters.ListePlatMenuAdapter;
import com.maximegens.foodtrucklillois.beans.menu.CategoriePlat;
import com.maximegens.foodtrucklillois.beans.menu.Plat;
import com.maximegens.foodtrucklillois.interfaces.RecyclerViewListePlatListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment gérant l'affichage des produits contenu dans la catégorie sélectionné.
 */
public class MenuDetailFragment extends Fragment {

    private CategoriePlat categoriePlat;
    private RecyclerViewListePlatListener callback;
    private List<Plat> lesPlats = new ArrayList<>();
    public static final String TAG_MENU_DETAIL_FRAGMENT = "MenuDetailFragment";

    /**
     * Creation du Fragment.
     * @return Une instance de MenuDetailFragment.
     */
    public static MenuDetailFragment newInstance(CategoriePlat catPlat) {
        MenuDetailFragment fragment = new MenuDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(MenuCategorieFragment.KEY_CAT_PLAT, catPlat);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu_detail_ft, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerViewPlat = (RecyclerView) view.findViewById(R.id.recycler_view_plat_menu);
        Button buttonRetourMenu = (Button) view.findViewById(R.id.button_retour_menu);
        callback = (RecyclerViewListePlatListener) getParentFragment();

        // Récuperation de la categorie selectionnée.
        if (getArguments() != null) {
            categoriePlat = getArguments().getParcelable(MenuCategorieFragment.KEY_CAT_PLAT);
        }

        // Récupération des plats de la categorie.
        if(categoriePlat != null && categoriePlat.getListePlats() != null){
            lesPlats = categoriePlat.getListePlats();
        }

        if(categoriePlat != null && categoriePlat.getInformations() != null && !categoriePlat.getInformations().isEmpty()){
            TextView info = (TextView) view.findViewById(R.id.information_menu_detail);
            info.setText(categoriePlat.getInformations());
            info.setVisibility(View.VISIBLE);
        }

        // Ajout des plats à la liste.
        recyclerViewPlat.setLayoutManager(new LinearLayoutManager(getContext()));
        ListePlatMenuAdapter adapterPlatMenu = new ListePlatMenuAdapter(lesPlats, getParentFragment());
        recyclerViewPlat.setAdapter(adapterPlatMenu);

        // Clique sur le button retour au menu
        buttonRetourMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(callback != null){
                    callback.retourListeCategorie();
                }
            }
        });
    }
}
