package com.maximegens.foodtrucklillois.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.maximegens.foodtrucklillois.R;
import com.maximegens.foodtrucklillois.beans.FoodTruck;
import com.maximegens.foodtrucklillois.utils.Constantes;
import com.maximegens.foodtrucklillois.utils.Utils;
import com.squareup.picasso.Picasso;

/**
 * Holder pour l'affichage de la liste des Foods Trucks.
 */
public class ListeFTHolder extends RecyclerView.ViewHolder {

    private Context context;
    private TextView textViewNom;
    private ImageView imageView;
    private TextView distance;

    /**
     * Le constructeur.
     * @param itemView L'item de la vue.
     * @param context Le context.
     */
    public ListeFTHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
        textViewNom = (TextView) itemView.findViewById(R.id.nom_ft_card_view);
        imageView = (ImageView) itemView.findViewById(R.id.logo_ft_card_view);
        distance = (TextView) itemView.findViewById(R.id.ft_distance_tv);

    }

    /**
     * Fonction pour remplir la cellule en fonction d'un FoodTruck.
     */
    public void bind(FoodTruck ft,int position,boolean rechercheEnCours){
        textViewNom.setText(ft.getNom());
        Resources res = context.getResources();

        distance.setText(String.valueOf(Utils.metreToKm(ft.getDistanceFromUser()))+ Constantes.KM);

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

    }

}
