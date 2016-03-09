package com.maximegens.foodtrucklillois;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.maximegens.foodtrucklillois.beans.FoodTruck;
import com.maximegens.foodtrucklillois.fragments.ListeFoodTruckFragment;
import com.maximegens.foodtrucklillois.interfaces.ListeFoodTruckFragmentCallback;
import com.maximegens.foodtrucklillois.interfaces.RecyclerViewListeFTListener;

/**
 * Class MainAcitivity.
 */
public class MainActivity extends AppCompatActivity implements RecyclerViewListeFTListener, ListeFoodTruckFragmentCallback{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Recuperation de la toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_list_ft);

        //definir notre toolbar en tant qu'actionBar
        setSupportActionBar(toolbar);

        // Commit du Fragment des listes des FT
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentLayout, ListeFoodTruckFragment.newInstance())
                .commit();
    }


    /**
     * Methode appelé lors du clique sur un Food Truck.
     * Permet d'appeler l'activity qui gére l'affichage des caractéristiques du food truck.
     * @param ft L'objet FoodTruck selectionné.
     */
    @Override
    public void onClickFT(FoodTruck ft) {
        Intent intent = new Intent(MainActivity.this, FoodTruckActivity.class);
        intent.putExtra(FoodTruck.KEY_FOOD_TRUCK, ft);
        startActivity(intent);
    }

    /**
     * Methode appeler depuis le fragment ListFoodTruckFragment pour notifier l'acitivité.
     */
    @Override
    public void notifyActivity() {

    }
}
