package com.maximegens.foodtrucklillois.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maximegens.foodtrucklillois.R;
import com.maximegens.foodtrucklillois.beans.AdresseFoodTruck;
import com.maximegens.foodtrucklillois.beans.FoodTruck;

import java.util.List;

/**
 * Adapter pour l'affichage de la liste des adresses.
 */
public class PlanningAdresseAdapter extends RecyclerView.Adapter<PlanningAdresseAdapter.ViewHolder> {

    private final List<AdresseFoodTruck> lesAdresses;
    private final Context context;
    private final FoodTruck ft;

    /**
     * Constructeur prenant en entrée une liste.
     */
    public PlanningAdresseAdapter(List<AdresseFoodTruck> lesAdresses, Context context, FoodTruck ft) {
        this.lesAdresses = lesAdresses;
        this.context = context;
        this.ft = ft;
    }

    /**
     * Permet de créer les viewHolder et d'indiquer la vue à inflater.
     * @param parent Le parent.
     * @param viewType le viewType.
     * @return L'objet ListeFTHolder.
     */
    public PlanningAdresseAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_adresses_planning, parent, false);
        return new ViewHolder(v);
    }

    /**
     * Fonction pour remplir la cellule avec les données de chaque adresse.
     * @param holder Le holder.
     * @param position La position de l'item.
     */
    public void onBindViewHolder(ViewHolder holder, int position) {
        final AdresseFoodTruck adresse = lesAdresses.get(position);
        holder.adresse.setText(adresse.getAdresse());

        if(adresse.getLatitude() != null && adresse.getLongitude() != null){
            holder.cardViewAdresse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri gmmIntentUri = Uri.parse("geo:0,0?q="+adresse.getLatitude()+","+adresse.getLongitude()+"("+ft.getNom()+")");
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    context.startActivity(mapIntent);

                }
            });
        }
    }

    /**
     * Donne le nombre d'adresse dans la liste.
     * @return Le nombre d'adresse.
     */
    @Override
    public int getItemCount() {
        return lesAdresses != null ? lesAdresses.size() : 0;
    }


    /**
     * Class view Holder
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView adresse;
        public final CardView cardViewAdresse;

        public ViewHolder(View v) {
            super(v);
            cardViewAdresse = (CardView) v.findViewById(R.id.cad_view_adresse_item);
            adresse = (TextView) v.findViewById(R.id.adresse_planning_item);
        }
    }
}
