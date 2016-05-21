package com.maximegens.foodtrucklillois.fragments;


import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maximegens.foodtrucklillois.FoodTruckActivity;
import com.maximegens.foodtrucklillois.R;
import com.maximegens.foodtrucklillois.adapters.ListeCatPlatAdapter;
import com.maximegens.foodtrucklillois.beans.FoodTruck;
import com.maximegens.foodtrucklillois.beans.menu.CategoriePlat;
import com.maximegens.foodtrucklillois.utils.GridLayoutManagerFoodTruck;

import java.util.List;

/**
 * Fragment gérant l'affichage des categories du Menu.
 */
public class MenuCategorieFragment extends Fragment {

    public static final String KEY_CAT_PLAT = "CategoriePlat";
    private TextView informationMenu;
    private TextView descriptionMenu;
    private RecyclerView recyclerViewMenu;
    private ListeCatPlatAdapter adapterListeCat;
    private FoodTruck ft = null;
    public static final String TAG_MENU_DETAIL_FRAGMENT = "MenuCategorieFragment";

    /**
     * Creation du Fragment.
     * @return Une instance de MenuCategorieFragment.
     */
    public static MenuCategorieFragment newInstance(FoodTruck ft) {
        MenuCategorieFragment fragment = new MenuCategorieFragment();
        Bundle args = new Bundle();
        args.putParcelable(FoodTruckActivity.KEY_FOODTRUCK_SELECTIONNER, ft);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu_cat_ft, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerViewMenu = (RecyclerView) view.findViewById(R.id.recyclerView_menu);
        informationMenu = (TextView) view.findViewById(R.id.information_menu);
        descriptionMenu = (TextView) view.findViewById(R.id.description_menu);
        recyclerViewMenu.setHasFixedSize(true);

        if(getArguments() != null){
            ft = getArguments().getParcelable(FoodTruckActivity.KEY_FOODTRUCK_SELECTIONNER);
        }

        // On affiche l'information du menu si il y en a une.
        if(existInformationMenu()){
            informationMenu.setText(ft.getMenu().getInformation());
            informationMenu.setVisibility(View.VISIBLE);
        }
        // On affiche la description du menu si il y en a une.
        if(existDescriptionMenu()){
            descriptionMenu.setText(ft.getMenu().getDescriptionMenu());
            descriptionMenu.setVisibility(View.VISIBLE);
        }

        // Recuperation de la liste des categories
        List<CategoriePlat> listCatPlat = null;
        if(ft != null && ft.getMenu() != null){
            listCatPlat = ft.getMenu().getLesCategorieDePlat();
        }

        // Creation du LinearLayoutManager
        GridLayoutManagerFoodTruck layoutManagerFT = new GridLayoutManagerFoodTruck(getContext());
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerViewMenu.setLayoutManager(layoutManagerFT.buildGridLayoutPortraitForCatMenu());
        }else{
            recyclerViewMenu.setLayoutManager(layoutManagerFT.buildGridLayoutLandscapeForCatMenu());
        }

        // Ajout des categories à la liste.
        adapterListeCat = new ListeCatPlatAdapter(listCatPlat,getParentFragment());
        recyclerViewMenu.setAdapter(adapterListeCat);

    }

    /**
     * Indique si il existe une description du menu.
     * @return true si il existe une description du menu.
     */
    private boolean existDescriptionMenu() {
        return ft.getMenu() != null && ft.getMenu().getDescriptionMenu() != null && !ft.getMenu().getDescriptionMenu().isEmpty();
    }

    /**
     * Indique si il existe des informations sur menu.
     * @return true si il existe des informations sur menu..
     */
    private boolean existInformationMenu() {
        return ft.getMenu() != null && ft.getMenu().getInformation() != null && !ft.getMenu().getInformation().isEmpty();
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
