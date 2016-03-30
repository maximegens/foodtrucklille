package com.maximegens.foodtrucklillois;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.maximegens.foodtrucklillois.beans.FoodTruck;
import com.maximegens.foodtrucklillois.fragments.AProposFragment;
import com.maximegens.foodtrucklillois.fragments.EmplacementAllFragment;
import com.maximegens.foodtrucklillois.fragments.FavorisFragment;
import com.maximegens.foodtrucklillois.fragments.ListeFoodTruckFragment;
import com.maximegens.foodtrucklillois.interfaces.ListeFoodTruckFragmentCallback;
import com.maximegens.foodtrucklillois.interfaces.RecyclerViewListeFTListener;
import com.maximegens.foodtrucklillois.utils.Constantes;

/**
 * Class MainAcitivity.
 */
public class MainActivity extends AppCompatActivity implements RecyclerViewListeFTListener, ListeFoodTruckFragmentCallback, LocationListener {

    private LocationManager locationManager;
    private NavigationView nav;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

                //C On passe l'item cliqué en mode checked, sinon on le retire.
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
                                .addToBackStack(null)
                                .replace(R.id.fragmentLayout, ListeFoodTruckFragment.newInstance())
                                .commit();
                        return true;
                    case R.id.navigation_item_favori:
                        // Commit du Fragment des listes des FT
                        getSupportFragmentManager().beginTransaction()
                                .addToBackStack(null)
                                .replace(R.id.fragmentLayout, FavorisFragment.newInstance())
                                .commit();
                        return true;
                    case R.id.navigation_item_emplacement:
                        // Commit du Fragment des listes des FT
                        getSupportFragmentManager().beginTransaction()
                                .addToBackStack(null)
                                .replace(R.id.fragmentLayout, EmplacementAllFragment.newInstance())
                                .commit();
                        return true;
                    case R.id.navigation_item_a_propos:
                        // Commit du Fragment des listes des FT
                        getSupportFragmentManager().beginTransaction()
                                .addToBackStack(null)
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

    @Override
    public void onResume() {
        super.onResume();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if ( !locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ){
            Toast.makeText(this, "GPS is disabled!", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "GPS is enabled!", Toast.LENGTH_LONG).show();
        }
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, Constantes.TIME_BETWEEN_UPDATE_GPS, 0, this);
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, Constantes.TIME_BETWEEN_UPDATE_GPS, 0, this);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.removeUpdates(this);
    }


    @Override
    public void onLocationChanged(Location location) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }


    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
