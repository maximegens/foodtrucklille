package com.maximegens.foodtrucklillois.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maximegens.foodtrucklillois.FoodTruckActivity;
import com.maximegens.foodtrucklillois.R;
import com.maximegens.foodtrucklillois.beans.FoodTruck;
import com.maximegens.foodtrucklillois.beans.menu.CategoriePlat;
import com.maximegens.foodtrucklillois.interfaces.RecyclerViewListeCatePlatListener;

/**
 * Created by a627520 on 07/03/2016.
 */
public class MenuFragment extends Fragment implements RecyclerViewListeCatePlatListener{

    public static String TITLE = "Menu";
    private FoodTruck ft = null;

    /**
     * Creation du Fragment.
     * @return Une instance de ListeFoodTruckFragment.
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

        MenuCategorieFragment menuCat = MenuCategorieFragment.newInstance(ft);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.framelayout_menu, menuCat).commit();

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClickCatPlat(CategoriePlat catPlat) {
        loadMenuDetailFragment(catPlat);
    }

    public void loadMenuDetailFragment(CategoriePlat catPlat){
        MenuDetailFragment menuDetail = MenuDetailFragment.newInstance(catPlat);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.framelayout_menu, menuDetail).commit();
    }
}
