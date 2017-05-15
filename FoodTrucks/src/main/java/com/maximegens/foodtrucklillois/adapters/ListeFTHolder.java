package com.maximegens.foodtrucklillois.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.maximegens.foodtrucklillois.R;
import com.maximegens.foodtrucklillois.beans.FoodTruck;
import com.maximegens.foodtrucklillois.network.Internet;
import com.maximegens.foodtrucklillois.utils.Constantes;
import com.maximegens.foodtrucklillois.utils.Utils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Holder pour l'affichage de la liste des Foods Trucks.
 */
class ListeFTHolder extends RecyclerView.ViewHolder {

    private final Context context;
    private final TextView textViewOuverture;
    private final TextView textHoraire;
    private final TextView textNom;
    private final ImageView imageView;
    private final TextView distance;
    private final ProgressBar loader;

    /**
     * Le constructeur.
     * @param itemView L'item de la vue.
     * @param context Le context.
     */
    public ListeFTHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
        textViewOuverture = (TextView) itemView.findViewById(R.id.ouverture_ft_card_view);
        textHoraire = (TextView) itemView.findViewById(R.id.horaireouverture_ft_card_view);
        textNom = (TextView) itemView.findViewById(R.id.nom_ft_card_view);
        imageView = (ImageView) itemView.findViewById(R.id.logo_ft_card_view);
        distance = (TextView) itemView.findViewById(R.id.ft_distance_tv);
        loader = (ProgressBar) itemView.findViewById(R.id.loader_ft);

    }

    /**
     * Fonction pour remplir la cellule en fonction d'un FoodTruck.
     */
    public void bind(FoodTruck ft){

        Resources res = context.getResources();

        loader.setVisibility(View.VISIBLE);
        textNom.setVisibility(View.GONE);

        //Affichage de l'ouverture
        if(ft.isOpenNow()){
            textViewOuverture.setText(context.getString(R.string.ouvert));
            textViewOuverture.setTextColor(ContextCompat.getColor(context, R.color.colorOuverture));
        }else{
            textViewOuverture.setText(context.getString(R.string.fermer));
            textViewOuverture.setTextColor(ContextCompat.getColor(context, R.color.colorFermeture));

            if(ft.getTrancheHoraire().isEmpty()) {
                textHoraire.setVisibility(View.GONE);
            }else{
                textHoraire.setText(ft.getTrancheHoraire());
            }
        }

        // Affichage de la distance si elle existe.
        if(ft.getDistanceFromUser() != Constantes.FT_FERMER_DISTANCE){
            distance.setText(String.valueOf(Utils.metreToKm(ft.getDistanceFromUser()))+ Constantes.KM);
        }

        if(ft.getLogo() != null){
            int resID = res.getIdentifier(ft.getLogo() , "mipmap", context.getPackageName());
            if(resID != 0){
                Picasso.with(context)
                        .load(resID)
                        .error(R.mipmap.photonotavailable)
                        .fit()
                        .centerCrop()
                        .into(imageView, new Callback() {
                            @Override
                            public void onSuccess() {
                                loader.setVisibility(View.GONE);
                            }
                            @Override
                            public void onError() {
                                loader.setVisibility(View.GONE);
                            }
                        });
            }else if(ft.getUrlLogo() != null && !ft.getUrlLogo().isEmpty() && Internet.isNetworkAvailable(context)){
                // Récupératation en ligne
                Picasso.with(context)
                        .load(ft.getUrlLogo())
                        .error(R.mipmap.photonotavailable)
                        .fit()
                        .centerCrop()
                        .into(imageView, new Callback() {
                            @Override
                            public void onSuccess() {
                                loader.setVisibility(View.GONE);
                            }
                            @Override
                            public void onError() {
                                loader.setVisibility(View.GONE);
                            }
                        });
            }else{
                int photoNonAvailable = res.getIdentifier("photonotavailable" , "mipmap", context.getPackageName());
                imageView.setImageResource(photoNonAvailable);
                textNom.setVisibility(View.VISIBLE);
                textNom.setText(ft.getNom());
                loader.setVisibility(View.GONE);
            }
        }

    }

}
