package com.maximegens.foodtrucklillois;


import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.maximegens.foodtrucklillois.fragments.ListeFoodTruckFragment;
import com.maximegens.foodtrucklillois.utils.Constantes;

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

        //Creation du jeu de test
        if(Constantes.lesFTs.isEmpty()){
            Constantes.ajouterFT();
        }

        //afficher le bouton retour
        //getSupportActionBar().setHomeButtonEnabled(true);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
