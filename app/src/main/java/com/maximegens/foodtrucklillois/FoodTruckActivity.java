package com.maximegens.foodtrucklillois;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.maximegens.foodtrucklillois.adapters.ViewPagerAdapter;
import com.maximegens.foodtrucklillois.beans.FoodTruck;
import com.maximegens.foodtrucklillois.fragments.DescriptionFoodTruckFragment;
import com.maximegens.foodtrucklillois.fragments.EmplacementFoodTruckFragment;
import com.maximegens.foodtrucklillois.fragments.MenuFoodTruckFragment;

public class FoodTruckActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_truck);

        // Creation de la toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_food_truck);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Recuperation du FoodTruck sélectionné.
        FoodTruck ft = getIntent().getExtras().getParcelable(FoodTruck.KEY_FOOD_TRUCK);
        setTitle(ft.getNom());

        // Ajout de l'image de fond represantant le FoodTruck
        ImageView fond = (ImageView)findViewById(R.id.backgroundImageView_food_truck);
        Resources res = getResources();
        int resID = res.getIdentifier(ft.getLogo() , "drawable", getPackageName());
        fond.setImageDrawable(ContextCompat.getDrawable(this, resID));

        // Creation du ViewPager
        ViewPager viewPager;
        TabLayout tabLayout;
        viewPager = (ViewPager) findViewById(R.id.viewpager_food_truck);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs_food_truck);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(DescriptionFoodTruckFragment.newInstance(), DescriptionFoodTruckFragment.TITLE);
        adapter.addFragment(MenuFoodTruckFragment.newInstance(), MenuFoodTruckFragment.TITLE);
        adapter.addFragment(EmplacementFoodTruckFragment.newInstance(), EmplacementFoodTruckFragment.TITLE);
        viewPager.setAdapter(adapter);
    }

}
