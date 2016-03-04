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

import com.maximegens.foodtrucklillois.FoodTruckActivity;
import com.maximegens.foodtrucklillois.R;
import com.maximegens.foodtrucklillois.beans.FoodTruck;
import com.maximegens.foodtrucklillois.beans.PlanningFoodTruck;
import com.maximegens.foodtrucklillois.utils.Constantes;
import com.maximegens.foodtrucklillois.utils.GestionnaireHoraire;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;


public class DescriptionFoodTruckFragment extends Fragment {

    public static String TITLE = "Informations";

    private FoodTruck ft = null;
    private TextView descriptionBreve;
    private TextView ouverture;
    private TextView cuisine;

    private TextView gammePrix;
    private TextView moyenPaiement;

    private TextView telephone;
    private TextView email;
    private TextView siteWeb;

    private TextView specialite;
    private TextView service;
    private TextView dateOuverture;

    private RelativeLayout zoneTel;
    private RelativeLayout zoneMail;
    private RelativeLayout zoneSiteWeb;

    private Button consulterHoraire;
    private Button voirDescriptionLongue;

    /**
     * Creation du Fragment.
     * @return Une instance de ListeFoodTruckFragment.
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

        if(getArguments() != null){
            ft = getArguments().getParcelable(FoodTruckActivity.KEY_FOODTRUCK_SELECTIONNER);
        }

        descriptionBreve = (TextView) view.findViewById(R.id.value_description_breve);
        ouverture = (TextView) view.findViewById(R.id.value_ouvert);
        cuisine = (TextView) view.findViewById(R.id.value_cuisine);
        gammePrix = (TextView) view.findViewById(R.id.value_gamme_prix);
        moyenPaiement = (TextView) view.findViewById(R.id.value_moyen_paiement);
        telephone = (TextView) view.findViewById(R.id.value_telephone);
        email = (TextView) view.findViewById(R.id.value_email);
        siteWeb = (TextView) view.findViewById(R.id.value_site_web);
        dateOuverture = (TextView) view.findViewById(R.id.value_date_ouverture);
        specialite = (TextView) view.findViewById(R.id.value_specialite);
        service = (TextView) view.findViewById(R.id.value_service);

        zoneTel = (RelativeLayout) view.findViewById(R.id.relative_layout_tel);
        zoneMail = (RelativeLayout) view.findViewById(R.id.relative_layout_mail);
        zoneSiteWeb = (RelativeLayout) view.findViewById(R.id.relative_layout_site_web);

        consulterHoraire = (Button) view.findViewById(R.id.button_horaire_complet);
        voirDescriptionLongue = (Button) view.findViewById(R.id.button_description_longue);

        // On rempli les TextView avec les donnees;
        if(ft != null){
            if(ft.getDescriptionBreve() != null){
                descriptionBreve.setText(ft.getDescriptionBreve());
            }
            // affichage de l'ouverture du food truck
            afficheOuverture();

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
            if(ft.getDateOuverture() != null){
                dateOuverture.setText(ft.getDateOuverture());
            }
            if(ft.getSpecialites() != null){
                specialite.setText(ft.getSpecialites());
            }
            if(ft.getServices() != null){
                service.setText(ft.getServices());
            }
        }

        // Gestion des clics

        /** Appel le food truck **/
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

        consulterHoraire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                voirHoraires();
            }
        });
    }

    /**
     * Methode permettant de savoir si le food truc est actuellement ouvert ou fermé.
     */
    private void afficheOuverture() {

        // Creation du calendrier
        Calendar calendarToday = GestionnaireHoraire.createCalendarToday();
        // Recuperation du numero du jour
        int numJour = GestionnaireHoraire.getNumeroJourDansLaSemaine(calendarToday);

        //TODO verifier si on est le midi ou le soir
        if(ft.getPlanning() != null && ft.getPlanning().get(numJour) != null) {
            String horaireOuverture = ft.getPlanning().get(numJour).getMidi().getHoraireOuverture();
            String horaireFermeture = ft.getPlanning().get(numJour).getMidi().getHoraireFermeture();

            if(horaireOuverture != null && horaireFermeture != null){
                Calendar calendarOuverture = GestionnaireHoraire.createCalendar(horaireOuverture);
                Calendar calendarFermeture = GestionnaireHoraire.createCalendar(horaireFermeture);

                if(GestionnaireHoraire.isOpen(calendarToday,calendarOuverture,calendarFermeture)){
                    ouverture.setTextColor(ContextCompat.getColor(getContext(), R.color.green));
                    ouverture.setText("Ouvert jusque "+ft.getPlanning().get(numJour).getMidi().getHeureFermetureEnString());
                }else{
                    ouverture.setTextColor(ContextCompat.getColor(getContext(), R.color.red));
                    ouverture.setText(getResources().getText(R.string.fermer));
                }
            }
        }
    }

    /**
     * Affiche les horaires du food truc dans une alert dialog
     */
    private void voirHoraires(){
        AlertDialog.Builder builder =
                new AlertDialog.Builder(getContext(), R.style.AppCompatAlertDialogStyle);
        builder.setTitle("Horaires");
        builder.setMessage(constructionHoraires());
        builder.setPositiveButton("OK", null);
        builder.show();
    }

    /**
     * Construction de l'affichage des horaires du food truck.
     * @return la chaine contenant les horaires des food trucks.
     */
    private String constructionHoraires(){
        StringBuilder horaires = new StringBuilder("");
        String fermer = getResources().getText(R.string.fermer).toString();

        if(ft.getPlanning() != null ){

            // Parcours de la liste des jours
            for(PlanningFoodTruck planning : ft.getPlanning()){
                // Mise en majuscule de la premier lettre.
                String jour = planning.getNomJour().substring(0, 1).toUpperCase() + planning.getNomJour().substring(1);
                horaires.append(jour+" : "+Constantes.RETOUR_CHARIOT);
                if(planning.isFermerToday()){
                    horaires.append(Constantes.TABULATION_DOUBLE+ fermer +Constantes.RETOUR_CHARIOT);
                }else{
                    if(planning.isOpenMidi()) {
                        horaires.append(Constantes.TABULATION_DOUBLE+"Midi : "+planning.getMidi().getHeureOuvertureEnString()+" - "+planning.getMidi().getHeureFermetureEnString()+Constantes.RETOUR_CHARIOT);
                    }else {
                        horaires.append(Constantes.TABULATION_DOUBLE+"Midi : "+ fermer+ Constantes.RETOUR_CHARIOT);
                    }
                    if(planning.isOpenSoir()) {
                        horaires.append(Constantes.TABULATION_DOUBLE+"Soir : "+planning.getSoir().getHeureOuvertureEnString()+" - "+planning.getSoir().getHeureFermetureEnString()+Constantes.RETOUR_CHARIOT);
                    }else {
                        horaires.append(Constantes.TABULATION_DOUBLE+"Soir : "+fermer+Constantes.RETOUR_CHARIOT);
                    }
                }
                horaires.append(Constantes.RETOUR_CHARIOT);
            }
        }
        return horaires.toString();
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
