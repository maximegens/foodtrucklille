package com.maximegens.foodtrucklillois.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maximegens.foodtrucklillois.R;
import com.maximegens.foodtrucklillois.beans.FoodTruck;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter pour l'affichage de la liste des Foods Trucks.
 */
public class ListeFTAdapter extends RecyclerView.Adapter<ListeFTHolder> {

    private List<FoodTruck> lesFT;
    private Context context;

    /**
     * Constructeur prenant en entrée une liste.
     */
    public ListeFTAdapter(List<FoodTruck> lesFT, Context context) {
        this.lesFT = lesFT;
        this.context = context;
    }

    /**
     * Permet de créer les viewHolder et d'indiquer la vue à inflater.
     * @param parent Le parent.
     * @param viewType le viewType.
     * @return L'objet ListeFTHolder.
     */
    @Override
    public ListeFTHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_liste_ft,parent,false);
        return new ListeFTHolder(view,context);
    }

    /**
     * Fonction pour remplir la cellule avec les données de chaque FoodTruck.
     * @param holder Le holder.
     * @param position La position de l'item.
     */
    @Override
    public void onBindViewHolder(ListeFTHolder holder, int position) {
        FoodTruck ft = lesFT.get(position);
        holder.bind(ft);
    }

    /**
     * Donne le nombre de FoodTruck dans la liste.
     * @return Le nombre de FoodTruck.
     */
    @Override
    public int getItemCount() {
        return lesFT.size();
    }

    public void setModels(List<FoodTruck> fts) {
        lesFT = new ArrayList<>(fts);
    }
}
