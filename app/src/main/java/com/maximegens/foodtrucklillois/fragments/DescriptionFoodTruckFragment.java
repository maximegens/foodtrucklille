package com.maximegens.foodtrucklillois.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.maximegens.foodtrucklillois.FoodTruckActivity;
import com.maximegens.foodtrucklillois.R;
import com.maximegens.foodtrucklillois.beans.FoodTruck;


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


        // On rempli les TextView avec les donnees;
        if(ft != null){
            if(ft.getDescriptionBreve() != null){
                descriptionBreve.setText(ft.getDescriptionBreve());
            }
            //TODO Gestion de l'heure d'ouverture Ouvert Ferme
                ouverture.setText("Aujourd'hui jusque 14h30");
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
        zoneTel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

        zoneSiteWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
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
