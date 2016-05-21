package com.maximegens.foodtrucklillois;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
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

    public static final String DATA_A_JOUR = "dataAjour";
    private ProgressBar loader;
    private TextView indicationLoarder;
    private Context context;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        App.tracker.setScreenName(getString(R.string.ga_title_splashscreen));

        context = getApplicationContext();

        // Téléchargement et affichage de la publicité
        //TODO reactiver pub
        //loadPub(context);

        loader = (ProgressBar) findViewById(R.id.loader_splash_screen);
        indicationLoarder = (TextView) findViewById(R.id.text_view_chargement_splashscreen);

        // Changement de la couleur du loader
        loader.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(context,R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);

        // Lancement d'une asynstack pour récupérer les Foods trucks.
        RetreiveJSONListeFTSplash retreiveJSONListeFTSplash = new RetreiveJSONListeFTSplash(this, loader, indicationLoarder);
        retreiveJSONListeFTSplash.execute(Internet.isNetworkAvailable(context));

    }

    /**
     * Téléchargement et affichage de l'intersticiel pour la publicité.
     * @param context Le context.
     */
    private void loadPub(Context context) {
        // Traitement de l'intersticiel pour le chargement et l'affichage de la publicité
        mAdView = (AdView) findViewById(R.id.adView);
        mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId(getString(R.string.id_interstitiel));
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                mInterstitialAd.show();
            }
        });
        AdRequest adRequest = new AdRequest.Builder().build();
        mInterstitialAd.loadAd(adRequest);
    }

    @Override
    public void processFinish(List<FoodTruck> listeFt, boolean isAJour) {
        if(listeFt != null){
            Constantes.lesFTs = listeFt;
            Collections.sort(Constantes.lesFTs, new SortListeFT(false));
        }else{
            Constantes.lesFTs = new ArrayList<>();
        }

        // On lance le main activity
        Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
        intent.putExtra(SplashScreenActivity.DATA_A_JOUR, isAJour);
        startActivity(intent);
        finish();
    }

    // Quand on quitte notre activity
    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    // Quand on retourne sur notre activity
    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    // Avant que notre activity ne soit détruite
    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }
}
