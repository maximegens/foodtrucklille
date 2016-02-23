package com.maximegens.foodtrucklillois.adapters;

import android.content.Context;
import android.content.res.Configuration;
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
import com.squareup.picasso.Picasso;

/**
 * Holder pour l'affichage de la liste des Foods Trucks.
 */
public class ListeFTHolder extends RecyclerView.ViewHolder {

    private Context context;
    private TextView textViewNom;
    private TextView textViewPlusProche;
    private TextView textViewDistancePlusProche;
    private ImageView imageView;
    private boolean rechercheEnCours;

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
    public void bind(FoodTruck ft,int position,boolean rechercheEnCours){
        textViewNom.setText(ft.getNom());
        Resources res = context.getResources();
        int resID = res.getIdentifier(ft.getLogo() , "drawable", context.getPackageName());
        Picasso.with(context).load(resID).fit().centerInside().into(imageView);
        // Affichage specifique des premiers et seconds items.
        if(isFirstItem(position, rechercheEnCours)){
            textViewPlusProche.setVisibility(View.VISIBLE);
        }
        if(isSecondItemLandScape(position, rechercheEnCours)){
            textViewPlusProche.setVisibility(View.INVISIBLE);
        }

    }

    /**
     * Permet de savoir si il s'agit du 1er item de la liste représentant le ft le plus proche et donc d'afficher correctement le TextView Explicatif.
     * @param position La position de l'item.
     * @param rechercheEnCours Savoir si il s'agit d'une recherche ou pas.
     * @return vrai si il s'agit du bon item.
     */
    private boolean isFirstItem(int position, boolean rechercheEnCours) {
        return position == 0 && !rechercheEnCours;
    }

    /**
     * Permet de savoir si il s'agit du second item de la liste uniquement si on est en mode paysage.
     * Code permettant d'avoir un bon alignement entre le 1er item (avec le textview FT le plus proche) et le second item sur la meme ligne mais sans le textView.
     * @param position La position de l'item.
     * @param rechercheEnCours Savoir si il s'agit d'une recherche ou pas.
     * @return vrai si il s'agit du bon item.
     */
    private boolean isSecondItemLandScape(int position, boolean rechercheEnCours) {
        return position == 1 && !rechercheEnCours && context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

}
