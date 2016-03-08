package com.maximegens.foodtrucklillois.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.maximegens.foodtrucklillois.FoodTruckActivity;
import com.maximegens.foodtrucklillois.R;
import com.maximegens.foodtrucklillois.beans.FoodTruck;
import com.maximegens.foodtrucklillois.beans.menu.CategoriePlat;
import com.maximegens.foodtrucklillois.beans.menu.Plat;
import com.maximegens.foodtrucklillois.interfaces.RecyclerViewListeCatePlatListener;
import com.maximegens.foodtrucklillois.interfaces.RecyclerViewListePlatListener;
import com.maximegens.foodtrucklillois.utils.Constantes;
import com.squareup.picasso.Picasso;

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
        loadMenuCategorieFragment(ft);

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
     * Chargement du fragment affichant les categories.
     * @param ft Le ft sélectionnée.
     */
    public void loadMenuCategorieFragment(FoodTruck ft){
        MenuCategorieFragment menuCat = MenuCategorieFragment.newInstance(ft);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.framelayout_menu, menuCat,MenuCategorieFragment.TAG_MENU_DETAIL_FRAGMENT).commit();
    }

    /**
     * Chargement du fragment affichant les differents plats de la categorie..
     * @param catPlat La categorie sélectionnée.
     */
    public void loadMenuDetailFragment(CategoriePlat catPlat){
        MenuDetailFragment menuDetail = MenuDetailFragment.newInstance(catPlat);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.framelayout_menu, menuDetail, MenuDetailFragment.TAG_MENU_DETAIL_FRAGMENT).commit();
    }


    // ********************* Les CallBacks ********************* //

    /**
     * CallBack appelé lors du clique que une catégorie.
     * @param  catPlat la categorie sélectionnée.
     */
    @Override
    public void onClickCatPlat(CategoriePlat catPlat) {
        loadMenuDetailFragment(catPlat);
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

            LayoutInflater inflater = getActivity().getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_detail_plat, null);
            builder.setView(dialogView);

            ImageView imagePlatDialog = (ImageView) dialogView.findViewById(R.id.image_plat_dialog);
            TextView description = (TextView) dialogView.findViewById(R.id.description_plat);
            TextView prix = (TextView) dialogView.findViewById(R.id.prix_value);
            TextView prixEnMenuLabel = (TextView) dialogView.findViewById(R.id.prix_en_menu_label);
            TextView prixEnMenuValue = (TextView) dialogView.findViewById(R.id.prix_en_menu_value);

            // Recuperation de la photo
            String url = plat.getUrlPhoto();
            if(url != null ){
                Picasso.with(getActivity())
                        .load(url)
                        .placeholder(R.drawable.progress_animation_loader)
                        .error(R.drawable.photonotavailable)
                        .fit().centerInside()
                        .into(imagePlatDialog);
            }

            // Ajout de la description et du prix
            description.setText(plat.getDescriptionPlat());
            prix.setText(String.valueOf(plat.getPrix()));

            // Ajout du prix en menu si il existe
            Float prixMenu = new Float(plat.getPrixEnMenu());
            if(prixMenu != null && prixMenu != 0){
                prixEnMenuLabel.setVisibility(View.VISIBLE);
                prixEnMenuValue.setVisibility(View.VISIBLE);
                prixEnMenuValue.setText(String.valueOf(prixMenu));

            }

            builder.setTitle(plat.getNomPlat());
            builder.setPositiveButton("Good !", null);
            builder.show();
        }
    }

    /**
     * CallBack permettant le retour du fragment sur les produit/plats vers celui sur les categories.
     */
    @Override
    public void retourListeCategorie() {
        loadMenuCategorieFragment(ft);
    }
}
