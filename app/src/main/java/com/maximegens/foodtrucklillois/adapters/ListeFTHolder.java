package com.maximegens.foodtrucklillois.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.maximegens.foodtrucklillois.R;
import com.maximegens.foodtrucklillois.beans.FoodTruck;
import com.maximegens.foodtrucklillois.interfaces.RecyclerViewListener;

/**
 * Holder pour l'affichage de la liste des Foods Trucks.
 */
public class ListeFTHolder extends RecyclerView.ViewHolder {

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
    public ListeFTHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
        textViewNom = (TextView) itemView.findViewById(R.id.nom_ft_card_view);
        imageView = (ImageView) itemView.findViewById(R.id.logo_ft_card_view);
        textViewPlusProche = (TextView) itemView.findViewById(R.id.ft_le_plus_proche_tv);
        textViewDistancePlusProche = (TextView) itemView.findViewById(R.id.ft_distance_plus_proche_tv);

    }

    /**
     * Fonction pour remplir la cellule en fonction d'un FoodTruck.
     */
    public void bind(FoodTruck ft,int position){
        textViewNom.setText(ft.getNom());
        imageView.setImageDrawable(constructionImage(ft));
        // Affichage specifique de l'item indiquant le FT le plus proche
        if(position == 0){
            textViewPlusProche.setVisibility(View.VISIBLE);
            textViewDistancePlusProche.setVisibility(View.VISIBLE);
        }

    }

    /**
     * Construction de l'image.
     * @param ft Le Food Truck associé.
     * @return L'image construite.
     */
    private Drawable constructionImage(FoodTruck ft) {
        Resources res = context.getResources();
        int resID = res.getIdentifier(ft.getLogo() , "drawable", context.getPackageName());
        return ContextCompat.getDrawable(context, resID);
    }
}
