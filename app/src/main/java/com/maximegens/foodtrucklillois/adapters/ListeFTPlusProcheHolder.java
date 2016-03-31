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

import com.maximegens.foodtrucklillois.R;
import com.maximegens.foodtrucklillois.beans.FoodTruck;
import com.maximegens.foodtrucklillois.beans.PlanningFoodTruck;
import com.maximegens.foodtrucklillois.utils.Constantes;
import com.maximegens.foodtrucklillois.utils.GestionnaireHoraire;
import com.maximegens.foodtrucklillois.utils.Utils;
import com.squareup.picasso.Picasso;

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
        if(ft.getDistanceFromUser() == Constantes.FT_FERMER_DISTANCE && ft.isOpenToday()){
            distance.setText(context.getString(R.string.calcul_distance_en_cours));
        }else if (ft.getDistanceFromUser() != Constantes.FT_FERMER_DISTANCE && ft.isOpenToday()){
            distance.setText(String.valueOf(Utils.metreToKm(ft.getDistanceFromUser()))+ Constantes.KM);
        }else{
            distance.setText("");
        }

        // Affichage de l'ouverture du Food Truck.
        if(ft.isOpenNow()){
            ouverture.setText(context.getString(R.string.ouvert));
            ouverture.setTextColor(ContextCompat.getColor(context, R.color.colorOuverture));
        }else{
            ouverture.setText(context.getString(R.string.fermer));
            ouverture.setTextColor(ContextCompat.getColor(context, R.color.colorFermeture));
        }

        // Clique sur le boutton 'GO' permettant d'afficher l'itinéaire vers le food truck".
        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String latitude = null;
                String longitude = null;
                PlanningFoodTruck planning = ft.getPlanning().get(GestionnaireHoraire.getNumeroJourDansLaSemaine() - 1);

                // Recuperation de la latitude et de la longitude.
                if(GestionnaireHoraire.isMidiOrBeforeMidi()) {
                    //TODO remplacer par l'adresse la plus proche, pour l'instant parmis toutes les adresses ont prends la premiere (.get(0)).
                    if(planning != null && planning.getMidi() != null && planning.getMidi().getAdresses() != null){
                        latitude = planning.getMidi().getAdresses().get(0).getLatitude();
                        longitude = planning.getMidi().getAdresses().get(0).getLongitude();
                    }
                }if(GestionnaireHoraire.isSoirOrBeforeSoirButAfterMidi()){
                    if(planning != null && planning.getSoir() != null && planning.getSoir().getAdresses() != null){
                        latitude = planning.getSoir().getAdresses().get(0).getLatitude();
                        longitude = planning.getSoir().getAdresses().get(0).getLongitude();
                    }
                }

                // Verification que la latitude et la longitude sont différent de null.
                if(latitude != null && longitude != null){
                    if(ft.isOpenNow()){
                        lanceItineraire(latitude,longitude);
                    }else{
                        affichePopupFoodTruckFermer(ft, latitude , longitude);
                    }
                }else{
                    affichePopupErreurLocalisation(ft);
                }
            }
        });

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
     * @param latitude La latitude de la position.
     * @param longitude La longitude de la position.
     */
    public void affichePopupFoodTruckFermer(FoodTruck ft, final String latitude, final String longitude){
        AlertDialog.Builder builder =
                new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);
        builder.setTitle(ft.getNom());
        builder.setMessage("Attention le Food truck " + ft.getNom() + " est actuellement fermé\n\nSouhaitez vous quand même voir l'itinéraire ?");
        builder.setPositiveButton(context.getString(R.string.oui_faim), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                lanceItineraire(latitude, longitude);
            }
        });
        builder.setNegativeButton(context.getString(R.string.non), null);
        builder.show();
    }

    /**
     * Lance l'itineraire vers la position renseigné.
     * @param latitude La latitude de la position.
     * @param longitude La longitude de la position.
     */
    public void lanceItineraire(String latitude,String longitude){
        Uri gmmIntentUri = Uri.parse("google.navigation:q="+latitude+","+longitude);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        context.startActivity(mapIntent);
    }

}
