package com.maximegens.foodtrucklillois;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.maximegens.foodtrucklillois.beans.FoodTruck;
import com.maximegens.foodtrucklillois.fragments.AProposFragment;
import com.maximegens.foodtrucklillois.fragments.EmplacementAllFragment;
import com.maximegens.foodtrucklillois.fragments.FavorisFragment;
import com.maximegens.foodtrucklillois.fragments.ListeFoodTruckFragment;
import com.maximegens.foodtrucklillois.fragments.PlanningFragment;
import com.maximegens.foodtrucklillois.interfaces.ListeFoodTruckFragmentCallback;
import com.maximegens.foodtrucklillois.interfaces.RecyclerViewListeFTListener;
import com.maximegens.foodtrucklillois.interfaces.RecyclerViewPlanningListener;
import com.maximegens.foodtrucklillois.utils.Constantes;

/**
 * Class MainAcitivity.
 */
public class MainActivity extends AppCompatActivity implements RecyclerViewListeFTListener, ListeFoodTruckFragmentCallback, RecyclerViewPlanningListener {

    private NavigationView nav;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        App.tracker.setScreenName(getString(R.string.ga_title_main_activity));

        // Récupération du context
        context = getApplicationContext();

        // Récupération des données de l'intent.
        if(getIntent() != null){
            Bundle bundle = getIntent().getExtras();
            if(bundle != null && !bundle.getBoolean(SplashScreenActivity.DATA_A_JOUR)){
                View parentLayout = findViewById(R.id.relative_layout_main_activity);
                Snackbar.make(parentLayout,getString(R.string.msg_data_no_update),Snackbar.LENGTH_LONG).show();
            }
        }

        // Recuperation de la toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar_list_ft);

        // Récuperation du DrawerLayout
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        // Récuperation du NavigationView
        nav = (NavigationView) findViewById(R.id.nav_view);

        // Definition de la toolbar en tant qu'actionBar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        // Définition du ActionBarDrawerToggle.
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                // On passe l'item cliqué en mode checked, sinon on le retire.
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                // Fermeture du drawer aprés le clique.
                drawerLayout.closeDrawers();

                // A appel le bon fragment en fonction de l'item cliqué.
                switch (menuItem.getItemId()) {
                    case R.id.navigation_item_liste_ft:
                        // Commit du Fragment des listes des FT
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragmentLayout, ListeFoodTruckFragment.newInstance())
                                .commit();
                        return true;
                    case R.id.navigation_item_planning:
                        // Commit du Fragment des listes des FT
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragmentLayout, PlanningFragment.newInstance())
                                .commit();
                        return true;
                    case R.id.navigation_item_emplacement:
                        // Commit du Fragment des listes des FT
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragmentLayout, EmplacementAllFragment.newInstance())
                                .commit();
                        return true;
                    case R.id.navigation_item_favori:
                        // Commit du Fragment des listes des FT
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragmentLayout, FavorisFragment.newInstance())
                                .commit();
                        return true;
                    case R.id.navigation_item_a_propos:
                        // Commit du Fragment des listes des FT
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragmentLayout, AProposFragment.newInstance())
                                .commit();
                        return true;
                    default:
                        return true;
                }
            }
        });

        // Chargement de base de la liste des Food Trucks.
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

    /**
     * CallBack du clique sur un foodtruck depuis la page du planning.
     * @param  foodtruck le food truck sélectionne.
     */
    @Override
    public void onClickFtPlanning(FoodTruck foodtruck) {
        Intent intent = new Intent(MainActivity.this, FoodTruckActivity.class);
        intent.putExtra(FoodTruck.KEY_FOOD_TRUCK, foodtruck);
        startActivity(intent);
    }

    @Override
    public void onBackPressed(){
        AlertDialog.Builder builder =
                new AlertDialog.Builder(MainActivity.this, R.style.AppCompatAlertDialogStyle);
        builder.setTitle(context.getString(R.string.exit_app));
        builder.setMessage(context.getString(R.string.msg_exit));
        builder.setPositiveButton(context.getString(R.string.oui), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity.this.finish();
            }
        });
        builder.setNegativeButton(context.getString(R.string.non), null);
        builder.show();
    }
}
