package com.maximegens.foodtrucklillois.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.maximegens.foodtrucklillois.R;
import com.maximegens.foodtrucklillois.beans.FoodTruck;
import com.maximegens.foodtrucklillois.beans.PlanningFoodTruck;
import com.maximegens.foodtrucklillois.utils.GestionnaireHoraire;
import com.squareup.picasso.Picasso;

/**
 * Holder pour l'affichage de la liste des Foods Trucks.
 */
public class ListeFTPlusProcheHolder extends RecyclerView.ViewHolder {

    private Context context;
    private TextView textViewNom;
    private Button go;

    private ImageView imageView;

    /**
     * Le constructeur.
     * @param itemView L'item de la vue.
     * @param context Le context.
     */
    public ListeFTPlusProcheHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
        textViewNom = (TextView) itemView.findViewById(R.id.nom_ft_plus_proche_card_view);
        imageView = (ImageView) itemView.findViewById(R.id.logo_ft_plus_proche_card_view);
        go = (Button) itemView.findViewById(R.id.button_ft_go);
    }

    /**
     * Fonction pour remplir la cellule en fonction d'un FoodTruck.
     */
    public void bind(final FoodTruck ft,int position){
        textViewNom.setText(ft.getNom());
        Resources res = context.getResources();

        if(ft.getLogo() != null){
            int resID = res.getIdentifier(ft.getLogo() , "mipmap", context.getPackageName());
            Picasso.with(context)
                    .load(resID)
                    .placeholder(R.drawable.progress_animation_loader)
                    .error(R.mipmap.photonotavailable)
                    .fit()
                    .centerInside()
                    .into(imageView);
        }

        // Clique sur le boutton 'GO' permettant d'afficher l'itinéaire vers le food truck".
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String latitude = null;
                String longitude = null;
                PlanningFoodTruck planning = ft.getPlanning().get(GestionnaireHoraire.getNumeroJourDansLaSemaine() - 1);

                if(GestionnaireHoraire.isBeforeMidi() || GestionnaireHoraire.isMidi()) {
                    //TODO remplacer par l'adresse la plus proche.
                    if(planning != null && planning.getMidi() != null && planning.getMidi().getAdresses() != null){
                        latitude = planning.getMidi().getAdresses().get(0).getLatitude();
                        longitude = planning.getMidi().getAdresses().get(0).getLongitude();
                    }
                }if(GestionnaireHoraire.isBeforeSoirButAfterMidi() || GestionnaireHoraire.isSoir()){
                    if(planning != null && planning.getSoir() != null && planning.getSoir().getAdresses() != null){
                        latitude = planning.getSoir().getAdresses().get(0).getLatitude();
                        longitude = planning.getSoir().getAdresses().get(0).getLongitude();
                    }
                }

                if(latitude != null && longitude != null){
                    Uri gmmIntentUri = Uri.parse("google.navigation:q="+latitude+","+longitude);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    context.startActivity(mapIntent);
                }
            }
        });

    }

}
