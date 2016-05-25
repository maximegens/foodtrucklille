package com.maximegens.foodtrucklillois.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;

import com.google.android.gms.maps.model.LatLng;
import com.maximegens.foodtrucklillois.R;
import com.maximegens.foodtrucklillois.beans.FoodTruck;

/**
 * Classe contenant les AlertDialog.
 */
public class AlertDialogFT {

    private final Context context;

    public AlertDialogFT(Context context){
        this.context = context;
    }

    /**
     * Affichage d'une popup d'information indiquant qu'il a été impossible de récupérer les coordonnées exactes du Food trucks.
     * pour calculer son itinéraire.
     * @param ft Le food truck.
     */
    public void affichePopupErreurLocalisation(FoodTruck ft){
        AlertDialog.Builder builder =
                new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);
        builder.setTitle(ft.getNom());
        builder.setMessage(context.getString(R.string.erreur_localisation_ft));
        builder.setPositiveButton(context.getString(R.string.ok), null);
        builder.show();
    }

    /**
     * Demande a l'utilisateur si il souhaite bien consulter l'itinéraire même si le Food truck est actuellement fermé.
     * @param ft Le food truck.
     * @param coordonnee Les coordonnées du ft.
     */
    public void affichePopupFoodTruckFermer(FoodTruck ft, final LatLng coordonnee){
        AlertDialog.Builder builder =
                new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);
        builder.setTitle(ft.getNom());
        builder.setMessage("Attention le Food truck " + ft.getNom() + " est actuellement fermé"
                + "\nIl sera ouvert à "+ ft.getNextOuvertureToday()
                + "\n\n"
                + "Souhaitez vous quand même voir l'itinéraire ?");
        builder.setPositiveButton(context.getString(R.string.oui_faim), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                lanceItineraire(coordonnee);
            }
        });
        builder.setNegativeButton(context.getString(R.string.non), null);
        builder.show();
    }

    /**
     * Lance l'itineraire vers la position renseigné.
     * @param coordonnee Les coordonnées du ft.
     */
    public void lanceItineraire(LatLng coordonnee){
        Uri gmmIntentUri = Uri.parse("google.navigation:q="+coordonnee.latitude+","+coordonnee.longitude);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        context.startActivity(mapIntent);
    }

}
