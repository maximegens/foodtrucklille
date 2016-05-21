package com.maximegens.foodtrucklillois;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.maximegens.foodtrucklillois.adapters.ViewPagerAdapter;
import com.maximegens.foodtrucklillois.beans.FoodTruck;
import com.maximegens.foodtrucklillois.fragments.DescriptionFoodTruckFragment;
import com.maximegens.foodtrucklillois.fragments.EmplacementFoodTruckFragment;
import com.maximegens.foodtrucklillois.fragments.MenuFoodTruckFragment;
import com.maximegens.foodtrucklillois.utils.Constantes;
import com.squareup.picasso.Picasso;

public class FoodTruckActivity extends AppCompatActivity{

    public static final String KEY_FOODTRUCK_SELECTIONNER = "FoodTruckSelectionner";
    private FoodTruck ft;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private FloatingActionButton fabFavorite;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private AppBarLayout appBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_truck);

        App.tracker.setScreenName(getString(R.string.ga_title_food_truck));
//        App.tracker.send(new HitBuilders.EventBuilder()
//                .setCategory(getString(R.string.ga_cat_foodtruck))
//                .setAction(getString(R.string.ga_action_clique))
//                .setLabel(ft.getNom())
//                .build());

        // Creation de la toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_food_truck);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);

        // Recuperation du FoodTruck sélectionné.
        ft = getIntent().getExtras().getParcelable(FoodTruck.KEY_FOOD_TRUCK);

        // Recuperation du CollapsingToolBarLayout pour la gestion du visuel de l'entete du Food truck.
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_food_truck);
        collapsingToolbarLayout.setTitle(ft.getNom());
        collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this, android.R.color.transparent));

        // Récuperation du Floating Action Button
        fabFavorite = (FloatingActionButton) findViewById(R.id.fab_favorite);
        updateFabFavorite();
        fabFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                traitementFavori();
            }
        });

        // Ajout de l'image de fond represantant le FoodTruck.
        ImageView fond = (ImageView)findViewById(R.id.backgroundImageView_food_truck);

        Resources res = getResources();
        int resID = 0;
        if(ft.getLogo() != null && res != null) {
            resID = res.getIdentifier(ft.getLogo(), "mipmap", getPackageName());
        }else if(res != null){
            resID = res.getIdentifier(Constantes.PHOTO_NOT_AVAILABLE, "mipmap", getPackageName());
        }
        if(fond != null){
            fond.setColorFilter(Color.RED, PorterDuff.Mode.LIGHTEN);
            fond.setImageDrawable(ContextCompat.getDrawable(this, resID));
        }
        Picasso.with(getBaseContext()).load(resID).fit().centerInside().into(fond);

        // Creation du ViewPager
        tabLayout = (TabLayout) findViewById(R.id.tabs_food_truck);
        viewPager = (ViewPager) findViewById(R.id.viewpager_food_truck);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener (new ViewPager.OnPageChangeListener () {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                if(position == 2){
                    fabFavorite.setVisibility(View.GONE);

                    // On replie l'appBarLayout et on block son dépliage
                    if(appBarLayout != null){
                        appBarLayout.setExpanded(false, true);
                        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams();
                        AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
                        behavior.setDragCallback(new AppBarLayout.Behavior.DragCallback() {
                            @Override
                            public boolean canDrag(@NonNull AppBarLayout appBarLayout) {
                                return false;
                            }
                        });
                    }
                }else{
                    fabFavorite.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }

    /**
     * Ajout des fragments au viewPager.
     * @param viewPager Le viewPager.
     */
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(DescriptionFoodTruckFragment.newInstance(ft), DescriptionFoodTruckFragment.TITLE);
        adapter.addFragment(MenuFoodTruckFragment.newInstance(ft), MenuFoodTruckFragment.TITLE);
        adapter.addFragment(EmplacementFoodTruckFragment.newInstance(ft), EmplacementFoodTruckFragment.TITLE);
        viewPager.setAdapter(adapter);
    }

    /**
     * Mise à jour de l'icone du FAB.
     * Icone jaune si le food truck est en favori.
     * Icone grise si le food truck n'est pas en favori.
     */
    private void updateFabFavorite(){
        SharedPreferences favorites = getSharedPreferences(Constantes.FAVORITE_SHAREPREFERENCE,0);
        if(ft != null && favorites.contains(String.valueOf(ft.getId()))){
            fabFavorite.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_favorite_selected_24dp));
        }else{
            fabFavorite.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_favorite_24dp));
        }
    }

    /**
     * Gestion de l'ajout ou de la suppression du Food Truck aux Favoris.
     */
    private void traitementFavori(){
        SharedPreferences favorites = getSharedPreferences(Constantes.FAVORITE_SHAREPREFERENCE,0);
        SharedPreferences.Editor editor = favorites.edit();
        if(ft != null){
            String KEY_ID = String.valueOf(ft.getId());
            if (favorites.contains(KEY_ID)){
                // Le food truck est déja en favorie , donc on le retire de la liste.
                Snackbar.make(collapsingToolbarLayout,"Retiré des favoris",Snackbar.LENGTH_SHORT).show();
                fabFavorite.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_favorite_24dp));
                editor.remove(KEY_ID).apply();
            }else{
                // Le food truck n'est pas en favorie , on l'ajoute.
                Snackbar.make(collapsingToolbarLayout,"Ajouté aux favoris",Snackbar.LENGTH_SHORT).show();
                fabFavorite.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_favorite_selected_24dp));
                editor.putString(KEY_ID, KEY_ID).apply();
            }
        }
    }
}
