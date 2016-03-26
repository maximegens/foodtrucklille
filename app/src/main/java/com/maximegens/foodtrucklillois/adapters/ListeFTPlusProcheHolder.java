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
import com.squareup.picasso.Picasso;

/**
 * Holder pour l'affichage de la liste des Foods Trucks.
 */
public class ListeFTPlusProcheHolder extends RecyclerView.ViewHolder {

    private Context context;
    private TextView textViewNom;
    private TextView textViewPlusProche;
    private TextView textViewDistancePlusProche;
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

    }

    /**
     * Fonction pour remplir la cellule en fonction d'un FoodTruck.
     */
    public void bind(FoodTruck ft,int position,boolean rechercheEnCours){
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

    }

}
