package com.maximegens.foodtrucklillois.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maximegens.foodtrucklillois.R;
import com.maximegens.foodtrucklillois.beans.FoodTruck;
import com.maximegens.foodtrucklillois.beans.menu.CategoriePlat;
import com.maximegens.foodtrucklillois.interfaces.RecyclerViewListeCatePlatListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter pour l'affichage de la liste des catégories des plats.
 */
public class ListeCatPlatAdapter extends RecyclerView.Adapter<ListeCatPlatAdapter.ViewHolder> {

    private RecyclerViewListeCatePlatListener callback;
    private List<CategoriePlat> lesCategories;
    private Fragment context;

    /**
     * Constructeur prenant en entrée une liste.
     */
    public ListeCatPlatAdapter(List<CategoriePlat> lesCategories, Fragment context) {
        this.lesCategories = lesCategories;
        this.context = context;
        this.callback = (RecyclerViewListeCatePlatListener) this.context;
    }

    /**
     * Permet de créer les viewHolder et d'indiquer la vue à inflater.
     * @param parent Le parent.
     * @param viewType le viewType.
     * @return L'objet ListeFTHolder.
     */
    public ListeCatPlatAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_liste_categorie_plat, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    /**
     * Fonction pour remplir la cellule avec les données de chaque categorie.
     * @param holder Le holder.
     * @param position La position de l'item.
     */
    public void onBindViewHolder(ViewHolder  holder, final int position) {
        if(lesCategories != null){
            holder.titleCat.setText(lesCategories.get(position).getNomCategoriePlat());
            holder.cardViewCatPlat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onClickCatPlat(lesCategories.get(position));
                }
            });
        }
    }

    /**
     * Donne le nombre de FoodTruck dans la liste.
     * @return Le nombre de FoodTruck.
     */
    @Override
    public int getItemCount() {
        return lesCategories != null ? lesCategories.size() : 0;
    }


    /**
     * Class view Holder
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleCat;
        public CardView cardViewCatPlat;
        public ViewHolder(View v) {
            super(v);
            titleCat = (TextView) v.findViewById(R.id.title_cat_card_view);
            cardViewCatPlat = (CardView) v.findViewById(R.id.card_view_liste_cat_plat);
        }
    }

}
