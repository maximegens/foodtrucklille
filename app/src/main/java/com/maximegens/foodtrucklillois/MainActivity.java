package com.maximegens.foodtrucklillois;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.maximegens.foodtrucklillois.fragments.ListeFoodTruckFragment;

/**
 * Class MainAcitivity.
 */
public class MainActivity extends AppCompatActivity implements ListeFoodTruckFragment.ListeFoodTruckFragmentCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        //definir notre toolbar en tant qu'actionBar
        setSupportActionBar(toolbar);

        //afficher le bouton retour
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Commit du Fragment des listes des FT
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentLayout, ListeFoodTruckFragment.newInstance())
                .commit();
    }


    /**
     * Methode appelé lors de la selection d'un Fodd truck.
     */
    @Override
    public void onFoodTruckClicked() {
        Toast.makeText(this, "Tu as cliqué !", Toast.LENGTH_SHORT).show();
    }
}
