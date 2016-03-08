package com.maximegens.foodtrucklillois;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.maximegens.foodtrucklillois.adapters.ViewPagerAdapter;
import com.maximegens.foodtrucklillois.beans.FoodTruck;
import com.maximegens.foodtrucklillois.beans.menu.Menu;
import com.maximegens.foodtrucklillois.fragments.DescriptionFoodTruckFragment;
import com.maximegens.foodtrucklillois.fragments.EmplacementFoodTruckFragment;
import com.maximegens.foodtrucklillois.fragments.MenuFragment;
import com.maximegens.foodtrucklillois.utils.Constantes;
import com.squareup.picasso.Picasso;

public class FoodTruckActivity extends AppCompatActivity{

    public static String KEY_FOODTRUCK_SELECTIONNER = "FoodTruckSelectionner";
    private FoodTruck ft;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_truck);

        // Creation de la toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_food_truck);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Recuperation du FoodTruck sélectionné.
        ft = getIntent().getExtras().getParcelable(FoodTruck.KEY_FOOD_TRUCK);

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_food_truck);
        collapsingToolbarLayout.setTitle(ft.getNom());
        collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this,android.R.color.transparent));


        // Ajout de l'image de fond represantant le FoodTruck
        ImageView fond = (ImageView)findViewById(R.id.backgroundImageView_food_truck);
        Resources res = getResources();
        int resID;
        if(ft.getLogo() != null) {
            resID = res.getIdentifier(ft.getLogo(), "drawable", getPackageName());
        }else{
            resID = res.getIdentifier(Constantes.PHOTO_NOT_AVAILABLE, "drawable", getPackageName());
        }
        fond.setImageDrawable(ContextCompat.getDrawable(this, resID));
        Picasso.with(getBaseContext()).load(resID).fit().centerInside().into(fond);

        // Creation du ViewPager
        tabLayout = (TabLayout) findViewById(R.id.tabs_food_truck);
        viewPager = (ViewPager) findViewById(R.id.viewpager_food_truck);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

    }

    /**
     * Ajout des fragments au viewPager.
     * @param viewPager Le viewPager.
     */
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(DescriptionFoodTruckFragment.newInstance(ft), DescriptionFoodTruckFragment.TITLE);
        adapter.addFragment(MenuFragment.newInstance(ft), MenuFragment.TITLE);
        adapter.addFragment(EmplacementFoodTruckFragment.newInstance(ft), EmplacementFoodTruckFragment.TITLE);
        viewPager.setAdapter(adapter);
    }

}
