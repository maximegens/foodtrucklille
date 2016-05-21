package com.maximegens.foodtrucklillois.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Browser;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.maximegens.foodtrucklillois.App;
import com.maximegens.foodtrucklillois.FoodTruckActivity;
import com.maximegens.foodtrucklillois.R;
import com.maximegens.foodtrucklillois.beans.FoodTruck;
import com.maximegens.foodtrucklillois.utils.AlertDialogFT;
import com.maximegens.foodtrucklillois.utils.Constantes;
import com.maximegens.foodtrucklillois.utils.GestionnaireHoraire;
import com.maximegens.foodtrucklillois.utils.Utils;


public class DescriptionFoodTruckFragment extends Fragment {

    public static final String TITLE = "Informations";

    private FoodTruck ft = null;
    private TextView descriptionBreve;
    private TextView ouverture;
    private TextView distance;
    private TextView cuisine;

    private TextView gammePrix;
    private TextView moyenPaiement;

    private TextView telephone;
    private TextView email;
    private TextView siteWeb;
    private TextView pageFacebook;

    private TextView specialite;
    private TextView service;
    private TextView dateOuverture;
    private TextView servicePrive;

    private RelativeLayout zoneTel;
    private RelativeLayout zoneMail;
    private RelativeLayout zoneDistance;
    private RelativeLayout zonePageFacebook;

    private RelativeLayout zoneSiteWeb;
    private Button consulterHoraire;
    private Button voirDescriptionLongue;
    private Button goFT;

    /**
     * Creation du Fragment.
     * @return Une instance de DescriptionFoodTruckFragment.
     */
    public static DescriptionFoodTruckFragment newInstance(FoodTruck ft) {
        DescriptionFoodTruckFragment fragment = new DescriptionFoodTruckFragment();
        Bundle args = new Bundle();
        args.putParcelable(FoodTruckActivity.KEY_FOODTRUCK_SELECTIONNER,ft);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_description_ft, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        App.tracker.setScreenName(getString(R.string.ga_title_description_food_truck));

        if(getArguments() != null){
            ft = getArguments().getParcelable(FoodTruckActivity.KEY_FOODTRUCK_SELECTIONNER);
        }

        // Recuperation des informations depuis le xml.
        descriptionBreve = (TextView) view.findViewById(R.id.value_description_breve);
        ouverture = (TextView) view.findViewById(R.id.value_ouvert);
        distance = (TextView) view.findViewById(R.id.value_distance);
        cuisine = (TextView) view.findViewById(R.id.value_cuisine);
        gammePrix = (TextView) view.findViewById(R.id.value_gamme_prix);
        moyenPaiement = (TextView) view.findViewById(R.id.value_moyen_paiement);
        telephone = (TextView) view.findViewById(R.id.value_telephone);
        email = (TextView) view.findViewById(R.id.value_email);
        siteWeb = (TextView) view.findViewById(R.id.value_site_web);
        pageFacebook = (TextView) view.findViewById(R.id.value_page_facebook);
        dateOuverture = (TextView) view.findViewById(R.id.value_date_ouverture);
        specialite = (TextView) view.findViewById(R.id.value_specialite);
        service = (TextView) view.findViewById(R.id.value_service);
        servicePrive = (TextView) view.findViewById(R.id.value_service_prive);

        zoneTel = (RelativeLayout) view.findViewById(R.id.relative_layout_tel);
        zoneMail = (RelativeLayout) view.findViewById(R.id.relative_layout_mail);
        zoneSiteWeb = (RelativeLayout) view.findViewById(R.id.relative_layout_site_web);
        zonePageFacebook = (RelativeLayout) view.findViewById(R.id.relative_layout_page_facebook);
        zoneDistance = (RelativeLayout) view.findViewById(R.id.linear_distance_description);

        consulterHoraire = (Button) view.findViewById(R.id.button_horaire_complet);
        voirDescriptionLongue = (Button) view.findViewById(R.id.button_description_longue);
        goFT = (Button) view.findViewById(R.id.button_ft_go_description);

        // Remplissage des différents textView avec les informations contenu dans l'objet FoodTruck.
        if(ft != null){
            if(ft.getDescriptionBreve() != null){
                descriptionBreve.setText(ft.getDescriptionBreve());
            }
            // affichage de l'ouverture du food truck
            afficheOuvertureDistance();

            if(ft.getCuisine() != null){
                cuisine.setText(ft.getCuisine());
            }
            if(ft.getGammeDePrixprix() != null){
                gammePrix.setText(ft.getGammeDePrixprix());
            }
            if(ft.getMoyensDePaiement() != null){
                moyenPaiement.setText(ft.getMoyensDePaiement());
            }
            if(ft.getTelephone() != null){
                telephone.setText(ft.getTelephone());
            }
            if(ft.getEmail() != null){
                email.setText(ft.getEmail());
            }
            if(ft.getSiteWeb() != null){
                siteWeb.setText(ft.getSiteWeb());
            }
            if(ft.getUrlPageFacebook() != null){
                pageFacebook.setText(ft.getUrlPageFacebook());
            }
            if(ft.getDateOuverture() != null){
                dateOuverture.setText(ft.getDateOuverture());
            }
            if(ft.getSpecialites() != null){
                specialite.setText(ft.getSpecialites());
            }
            if(ft.getServices() != null){
                service.setText(ft.getServices());
            }
            if(ft.getServicePrive() != null){
                servicePrive.setText(ft.getServicePrive());
            }
        }

        // Gestion des clics

        /** Appel le numero de telephone du food truck **/
        zoneTel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_TELEPHONY)){
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + ft.getTelephone()));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });

        /** Envoi d'un email au food truck **/
        zoneMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{ ft.getEmail()});
                email.putExtra(Intent.EXTRA_SUBJECT, "Demande de renseignement");
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Choisissez un client Mail"));
            }
        });

        //TODO a remplacer par des custom chrome tab
        /** Lancement du site web du food truck dans le navigateur **/
        zoneSiteWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ft.getSiteWeb() != null){
                    String url = ft.getSiteWeb();
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    intent.putExtra(Browser.EXTRA_APPLICATION_ID, getActivity().getApplicationContext().getPackageName());
                    startActivity(intent);
                }
            }
        });

        /** Ouverture de la page Facebook du Food Truck **/
        zonePageFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ft.getUrlPageFacebook() != null){
                    String url = ft.getUrlPageFacebook();
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    intent.putExtra(Browser.EXTRA_APPLICATION_ID, getActivity().getApplicationContext().getPackageName());
                    startActivity(intent);
                }
            }
        });

        /** Affiche la description longue du food truck dans une AlertDialog/Popup **/
        voirDescriptionLongue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder =
                        new AlertDialog.Builder(getContext(), R.style.AppCompatAlertDialogStyle);
                builder.setTitle("Description");
                builder.setMessage(ft.getDescriptionLongue());
                builder.setPositiveButton("OK", null);
                builder.show();
            }
        });

        /** Affiche les horaires du food truck dans une AlertDialog/Popup **/
        consulterHoraire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                voirHoraires();
            }
        });

        goFT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goFoodtruckItineraire(getContext());
            }
        });
    }

    /**
     * Renvoi vers Google Map pour afficher l'itineraire.
     * @param context le context
     */
    private void goFoodtruckItineraire(Context context) {

        LatLng coordonnee = ft.getLatLon();

        // Verification que les coordonnées sont différent de null.
        AlertDialogFT alertFT = new AlertDialogFT(context);
        if (coordonnee != null) {
            if (ft.isOpenNow()) {
                alertFT.lanceItineraire(coordonnee);
            } else {
                alertFT.affichePopupFoodTruckFermer(ft, coordonnee);
            }
        } else {
            alertFT.affichePopupErreurLocalisation(ft);
        }
    }

    /**
     * Methode permettant de savoir si le food truc est actuellement ouvert ou fermé.
     */
    private void afficheOuvertureDistance() {

        // Si le Food truck est actuellement ouvert.
        if(ft.isOpenNow()){
            zoneDistance.setVisibility(View.VISIBLE);
            ouverture.setText(getString(R.string.ouvert_jusque) + ft.getOuvertureJusque());
            ouverture.setTextColor(ContextCompat.getColor(getContext(), R.color.colorOuverture));

        // Si le Food truck est fermé mais ouvres aujourd'hui.
        }else if(ft.isDateBeforeLastHoraireFermeture(GestionnaireHoraire.createCalendarToday())) {
            zoneDistance.setVisibility(View.VISIBLE);
            ouverture.setText(getString(R.string.fermer_ouverture_a)+ft.getProchaineOuvertureToday());
            ouverture.setTextColor(ContextCompat.getColor(getContext(), R.color.colorFermeture));

        }else{
            zoneDistance.setVisibility(View.GONE);
            ouverture.setText(getString(R.string.fermer_today));
            ouverture.setTextColor(ContextCompat.getColor(getContext(), R.color.colorFermeture));
        }

        // Affiche la distance.
        if (ft.getDistanceFromUser() != Constantes.FT_FERMER_DISTANCE && ft.isDateBeforeLastHoraireFermeture(GestionnaireHoraire.createCalendarToday())){
            distance.setText(String.valueOf(Utils.metreToKm(ft.getDistanceFromUser())) + " " + Constantes.KM);
        }else{
            distance.setText(getContext().getString(R.string.distance_indisponible));
        }

    }

    /**
     * Affiche les horaires du food truck dans une alert dialog.
     */
    private void voirHoraires(){
        AlertDialog.Builder builder =
                new AlertDialog.Builder(getContext(), R.style.AppCompatAlertDialogStyle);
        builder.setTitle("Horaires");
        builder.setMessage(ft.constructionHoraires());
        builder.setPositiveButton(getString(R.string.ok), null);
        builder.show();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
