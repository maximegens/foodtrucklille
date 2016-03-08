package com.maximegens.foodtrucklillois.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maximegens.foodtrucklillois.FoodTruckActivity;
import com.maximegens.foodtrucklillois.R;
import com.maximegens.foodtrucklillois.beans.FoodTruck;
import com.maximegens.foodtrucklillois.beans.menu.CategoriePlat;
import com.maximegens.foodtrucklillois.beans.menu.Plat;
import com.maximegens.foodtrucklillois.interfaces.RecyclerViewListeCatePlatListener;
import com.maximegens.foodtrucklillois.interfaces.RecyclerViewListePlatListener;
import com.maximegens.foodtrucklillois.utils.Constantes;

/**
 * Fragment représentant le menu.
 */
public class MenuFragment extends Fragment implements RecyclerViewListeCatePlatListener,RecyclerViewListePlatListener{

    public static String TITLE = "Menu";
    private FoodTruck ft = null;

    /**
     * Creation du Fragment.
     * @return Une instance de MenuFragment.
     */
    public static MenuFragment newInstance(FoodTruck ft) {
        MenuFragment fragment = new MenuFragment();
        Bundle args = new Bundle();
        args.putParcelable(FoodTruckActivity.KEY_FOODTRUCK_SELECTIONNER, ft);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu_ft, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(getArguments() != null){
            ft = getArguments().getParcelable(FoodTruckActivity.KEY_FOODTRUCK_SELECTIONNER);
        }

        // Chargement du fragment affichant les categories du menu.
        MenuCategorieFragment menuCat = MenuCategorieFragment.newInstance(ft);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
        transaction.add(R.id.framelayout_menu, menuCat,MenuCategorieFragment.TAG_MENU_DETAIL_FRAGMENT).commit();

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * CallBack appelé lors du clique que une catégorie.
     * @param  catPlat la categorie sélectionnée.
     */
    @Override
    public void onClickCatPlat(CategoriePlat catPlat) {
        loadMenuDetailFragment(catPlat);
    }

    /**
     * Remplacament du fragment sur les categorie du menu par celui sur les produits contenu dans la catégorie sélectionnée.
     * @param catPlat La categorie sélectionnée.
     */
    public void loadMenuDetailFragment(CategoriePlat catPlat){
        MenuDetailFragment menuDetail = MenuDetailFragment.newInstance(catPlat);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.framelayout_menu, menuDetail, MenuDetailFragment.TAG_MENU_DETAIL_FRAGMENT).commit();
    }

    /**
     * CallBack affichant dans une popup le detail du plat choisi.
     * @param plat le plat sélectionné.
     */
    @Override
    public void onClickPlat(Plat plat) {
        if(plat != null){
            AlertDialog.Builder builder =
                    new AlertDialog.Builder(getContext(), R.style.AppCompatAlertDialogStyle);
            builder.setTitle(plat.getNomPlat());
            builder.setMessage(plat.getDescriptionPlat());
            builder.setPositiveButton("Good !", null);
            builder.show();
        }
    }
}
