package com.maximegens.foodtrucklillois.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maximegens.foodtrucklillois.R;
import com.maximegens.foodtrucklillois.beans.FoodTruck;
import com.maximegens.foodtrucklillois.interfaces.RecyclerViewListeFTListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter pour l'affichage de la liste des Foods Trucks.
 */
public class ListeFTAdapter extends RecyclerView.Adapter<ListeFTHolder> {

    private RecyclerViewListeFTListener callback;
    private List<FoodTruck> lesFT;
    private Context context;
    private boolean affichageClassique;

    /**
     * Constructeur prenant en entrée une liste.
     * affichageClassique est à faux lors de la contruction initiale de l'adapter.
     */
    public ListeFTAdapter(List<FoodTruck> lesFT, Context context) {
        this.lesFT = lesFT;
        this.context = context;
        this.affichageClassique = false;
        this.callback = (RecyclerViewListeFTListener) this.context;
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
    public void onBindViewHolder(ListeFTHolder holder, final int position) {
        final FoodTruck ft = lesFT.get(position);
        holder.bind(ft,position,affichageClassique);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onClickFT(ft);
            }
        });
    }

    /**
     * Donne le nombre de FoodTruck dans la liste.
     * @return Le nombre de FoodTruck.
     */
    @Override
    public int getItemCount() {
        return lesFT.size();
    }

    /**
     * Modification des foods trucks dans la liste.
     * @param fts les foods trucks.
     * @param affichageClassique Permet d'indiquer si la liste doit etre afficher de maniére classique ou mise en avant.
     */
    public void setFTs(List<FoodTruck> fts, boolean affichageClassique) {
        lesFT = new ArrayList<>(fts);
        this.affichageClassique = affichageClassique;
        notifyDataSetChanged();
    }
}
