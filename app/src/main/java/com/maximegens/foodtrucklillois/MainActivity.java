package com.maximegens.foodtrucklillois;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.maximegens.foodtrucklillois.beans.FoodTruck;
import com.maximegens.foodtrucklillois.fragments.AProposFragment;
import com.maximegens.foodtrucklillois.fragments.ContactFragment;
import com.maximegens.foodtrucklillois.fragments.EmplacementAllFragment;
import com.maximegens.foodtrucklillois.fragments.FavorisFragment;
import com.maximegens.foodtrucklillois.fragments.ListeFoodTruckFragment;
import com.maximegens.foodtrucklillois.fragments.PlanningFragment;
import com.maximegens.foodtrucklillois.interfaces.RecyclerViewListeFTListener;
import com.maximegens.foodtrucklillois.interfaces.RecyclerViewPlanningListener;
import com.maximegens.foodtrucklillois.utils.Constantes;

/**
 * Class MainAcitivity.
 */
public class MainActivity extends AppCompatActivity implements RecyclerViewListeFTListener, RecyclerViewPlanningListener {

    private NavigationView nav;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private Context context;
    private int referenceFragment;


    @Override
    public void onResume(){
        super.onResume();
        // Si la liste dés food trucs a été perdue, on relance le splashscreen pour re télécharger les données.
        if(Constantes.lesFTs == null || Constantes.lesFTs.isEmpty()){
            Intent intent = new Intent(MainActivity.this, SplashScreenActivity.class);
            startActivity(intent);
            finish();
        }
    }

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
            View parentLayout = findViewById(R.id.relative_layout_main_activity);
            if(bundle != null && !bundle.getBoolean(SplashScreenActivity.DATA_A_JOUR) && parentLayout != null){
                Snackbar.make(parentLayout,getString(R.string.msg_data_no_update),Snackbar.LENGTH_LONG).show();
            }
        }

        // Recuperation de la toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar_list_ft);

        // Récuperation du NavigationView
        nav = (NavigationView) findViewById(R.id.nav_view);

        // Récuperation du DrawerLayout
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        View hView =  nav.getHeaderView(0);
        TextView headerSubTitle = (TextView) hView.findViewById(R.id.header_sub_title);
        headerSubTitle.setText("Version " + getString(R.string.app_version));

        // Definition de la toolbar en tant qu'actionBar
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

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

        if (savedInstanceState != null) {
            // Restore last state for checked position.
            referenceFragment = savedInstanceState.getInt(Constantes.RESTORE_FRAGMENT, R.id.navigation_item_liste_ft);
            openFragment(referenceFragment);
        }else{
            // Chargement de base de la liste des Food Trucks.
            // Commit du Fragment des listes des FT
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentLayout, ListeFoodTruckFragment.newInstance())
                    .commit();
        }

        // Déclaration des clics sur le drawer
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

                // Récupére la référence à l'item du menu sélectionné.
                referenceFragment = menuItem.getItemId();

                // A appel le bon fragment en fonction de l'item cliqué.
                return openFragment(referenceFragment);

            }
        });


    }

    /**
     * Appel le bon fragment en fonction du choix de l'utilisateur depuis le menu
     * @param ref le menu sélectionné.
     * @return le bon fragment.
     */
    private boolean openFragment(int ref) {
        switch (ref) {
            case R.id.navigation_item_liste_ft:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentLayout, ListeFoodTruckFragment.newInstance())
                        .commit();
                return true;
            case R.id.navigation_item_planning:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentLayout, PlanningFragment.newInstance())
                        .commit();
                return true;
            case R.id.navigation_item_emplacement:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentLayout, EmplacementAllFragment.newInstance())
                        .commit();
                return true;
            case R.id.navigation_item_favori:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentLayout, FavorisFragment.newInstance())
                        .commit();
                return true;
            case R.id.navigation_item_contact:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentLayout, ContactFragment.newInstance())
                        .commit();
                return true;
            case R.id.navigation_item_a_propos:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentLayout, AProposFragment.newInstance())
                        .commit();
                return true;
            default:
                return true;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Constantes.RESTORE_FRAGMENT, referenceFragment);
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
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
}
