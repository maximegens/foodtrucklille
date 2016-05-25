package com.maximegens.foodtrucklillois.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.maximegens.foodtrucklillois.App;
import com.maximegens.foodtrucklillois.FoodTruckActivity;
import com.maximegens.foodtrucklillois.R;
import com.maximegens.foodtrucklillois.beans.FoodTruck;
import com.maximegens.foodtrucklillois.beans.menu.CategoriePlat;
import com.maximegens.foodtrucklillois.beans.menu.Plat;
import com.maximegens.foodtrucklillois.interfaces.RecyclerViewListeCatePlatListener;
import com.maximegens.foodtrucklillois.interfaces.RecyclerViewListePlatListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Fragment représentant le menu.
 */
public class MenuFoodTruckFragment extends Fragment implements RecyclerViewListeCatePlatListener,RecyclerViewListePlatListener{

    public static final String TITLE = "Menu";
    private FoodTruck ft = null;

    /**
     * Creation du Fragment.
     * @return Une instance de MenuFragment.
     */
    public static MenuFoodTruckFragment newInstance(FoodTruck ft) {
        MenuFoodTruckFragment fragment = new MenuFoodTruckFragment();
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

        App.tracker.setScreenName(getString(R.string.ga_title_menu_food_truck));

        if(getArguments() != null){
            ft = getArguments().getParcelable(FoodTruckActivity.KEY_FOODTRUCK_SELECTIONNER);
        }

        // Chargement du fragment affichant les categories du menu.
        loadMenuCategorieFragment(ft);

    }


    /**
     * Chargement du fragment affichant les categories.
     * @param ft Le ft sélectionnée.
     */
    private void loadMenuCategorieFragment(FoodTruck ft){
        MenuCategorieFragment menuCat = MenuCategorieFragment.newInstance(ft);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.framelayout_menu, menuCat,MenuCategorieFragment.TAG_MENU_DETAIL_FRAGMENT).commit();
    }

    /**
     * Chargement du fragment affichant les differents plats de la categorie..
     * @param catPlat La categorie sélectionnée.
     */
    private void loadMenuDetailFragment(CategoriePlat catPlat){
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
            TextView prixLabel = (TextView) dialogView.findViewById(R.id.prix_label);
            TextView prixValue = (TextView) dialogView.findViewById(R.id.prix_value);
            TextView prixEnMenuLabel = (TextView) dialogView.findViewById(R.id.prix_en_menu_label);
            TextView prixEnMenuValue = (TextView) dialogView.findViewById(R.id.prix_en_menu_value);
            final ProgressBar loader = (ProgressBar) dialogView.findViewById(R.id.loader_plat_detail);

            // Recuperation de la photo
            String url = plat.getUrlPhoto();
            if(url != null ){
                loader.setVisibility(View.VISIBLE);
                Picasso.with(getActivity())
                        .load(url)
                        .error(R.mipmap.photonotavailable)
                        .fit().centerInside()
                        .into(imagePlatDialog, new Callback() {
                            @Override
                            public void onSuccess() {
                                loader.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError() {
                                loader.setVisibility(View.GONE);
                            }
                        });
            }else{
                loader.setVisibility(View.GONE);
                imagePlatDialog.setVisibility(View.GONE);
            }

            // Ajout de la description
            description.setText(plat.getDescriptionPlat());

            // AJout du prix
            if(plat.getPrix() != null && !plat.getPrix().isEmpty()){
                prixValue.setText(plat.getPrix());
            }else{
                prixLabel.setVisibility(View.GONE);
                prixValue.setVisibility(View.GONE);
            }

            // Ajout du prix en menu si il existe
            String prixMenu = plat.getPrixEnMenu();
            if(prixMenu != null && !plat.getPrix().isEmpty()){
                prixEnMenuLabel.setVisibility(View.VISIBLE);
                prixEnMenuValue.setVisibility(View.VISIBLE);
                prixEnMenuValue.setText(prixMenu);
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
