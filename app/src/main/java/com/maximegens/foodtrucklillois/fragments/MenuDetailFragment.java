package com.maximegens.foodtrucklillois.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maximegens.foodtrucklillois.R;
import com.maximegens.foodtrucklillois.beans.menu.CategoriePlat;

/**
 * Fragment gérant l'affichage des produits contenu dans la catégorie sélectionné.
 */
public class MenuDetailFragment extends Fragment {

    private CategoriePlat categoriePlat;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_menu_detail_ft, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            categoriePlat = getArguments().getParcelable(MenuCategorieFragment.KEY_CAT_PLAT);
        }
    }

}
