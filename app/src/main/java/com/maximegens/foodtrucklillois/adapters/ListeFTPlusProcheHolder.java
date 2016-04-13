package com.maximegens.foodtrucklillois.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.maximegens.foodtrucklillois.R;
import com.maximegens.foodtrucklillois.beans.FoodTruck;
import com.maximegens.foodtrucklillois.beans.PlanningFoodTruck;
import com.maximegens.foodtrucklillois.utils.AlertDialogFT;
import com.maximegens.foodtrucklillois.utils.Constantes;
import com.maximegens.foodtrucklillois.utils.GestionnaireHoraire;
import com.maximegens.foodtrucklillois.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

/**
 * Holder pour l'affichage de la liste des Foods Trucks.
 */
public class ListeFTPlusProcheHolder extends RecyclerView.ViewHolder {

    private Context context;
    private TextView textViewNom;
    private Button go;
    private TextView ouverture;
    private TextView distance;
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
        ouverture = (TextView) itemView.findViewById(R.id.ouverture_ft_plus_proche_card_view);
        distance = (TextView) itemView.findViewById(R.id.ft_distance_plus_proche_tv);
        go = (Button) itemView.findViewById(R.id.button_ft_go);
    }

    /**
     * Fonction pour remplir la cellule en fonction d'un FoodTruck.
     */
    public void bind(final FoodTruck ft,int position){
        textViewNom.setText(ft.getNom());
        Resources res = context.getResources();
        boolean isBeforeLastOuverture = ft.isDateBeforeLastHoraireFermeture(GestionnaireHoraire.createCalendarToday());

        // Affichage du logo.
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

        // Affichage de la distance entre l'utilisateur et le food truck le plsu proche.
        if(ft.getDistanceFromUser() == Constantes.FT_FERMER_DISTANCE && isBeforeLastOuverture){
            distance.setText(context.getString(R.string.calcul_distance_en_cours));
        }else if (ft.getDistanceFromUser() != Constantes.FT_FERMER_DISTANCE && isBeforeLastOuverture){
            distance.setText(String.valueOf(Utils.metreToKm(ft.getDistanceFromUser()))+ Constantes.KM);
        }else{
            distance.setText("");
        }

        // Affichage de l'ouverture du Food Truck.
        if(ft.isOpenNow()){
            ouverture.setText(context.getString(R.string.ouvert));
            ouverture.setTextColor(ContextCompat.getColor(context, R.color.colorOuverture));
        }else{
            // Affichage du bouton 'go' si le Food Truck est ouvert ou va ouvrir aujoud'hui
            if(isBeforeLastOuverture){
                go.setVisibility(View.VISIBLE);
            }else{
                go.setVisibility(View.INVISIBLE);
            }
            ouverture.setText(context.getString(R.string.fermer));
            ouverture.setTextColor(ContextCompat.getColor(context, R.color.colorFermeture));
        }

        // Clique sur le boutton 'GO' permettant d'afficher l'itinéaire vers le food truck".
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Récupération des coordonnées
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
        });
    }
}
