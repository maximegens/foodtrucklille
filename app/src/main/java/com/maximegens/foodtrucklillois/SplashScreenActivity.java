package com.maximegens.foodtrucklillois;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.maximegens.foodtrucklillois.beans.FoodTruck;
import com.maximegens.foodtrucklillois.interfaces.AsyncResponseSplashScreen;
import com.maximegens.foodtrucklillois.network.Internet;
import com.maximegens.foodtrucklillois.network.RetreiveJSONListeFTSplash;
import com.maximegens.foodtrucklillois.utils.Constantes;
import com.maximegens.foodtrucklillois.utils.SortListeFT;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class SplashScreenActivity.
 * Permet de vérifier la connexion internet, de récupérer les données des Foods trucks.
 */
public class SplashScreenActivity extends AppCompatActivity implements AsyncResponseSplashScreen{

    private ProgressBar loader;
    private TextView indicationLoarder;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        loader = (ProgressBar) findViewById(R.id.loader_splash_screen);
        indicationLoarder = (TextView) findViewById(R.id.text_view_chargement_splashscreen);

        context = getApplicationContext();

        // Lancement d'une asynstack pour récupérer les Foods trucks.
        RetreiveJSONListeFTSplash retreiveJSONListeFTSplash = new RetreiveJSONListeFTSplash(this, loader, indicationLoarder);
        retreiveJSONListeFTSplash.execute(Internet.isNetworkAvailable(context));

    }

    @Override
    public void processFinish(List<FoodTruck> listeFt) {
        if(listeFt != null){
            Constantes.lesFTs = listeFt;
            Collections.sort(Constantes.lesFTs, new SortListeFT(false));
        }else{
            Constantes.lesFTs = new ArrayList<>();
        }

        // On lance le main activity
        Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
