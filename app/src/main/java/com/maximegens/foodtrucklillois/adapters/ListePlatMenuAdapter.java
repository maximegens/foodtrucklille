package com.maximegens.foodtrucklillois.adapters;

import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.maximegens.foodtrucklillois.R;
import com.maximegens.foodtrucklillois.beans.menu.Plat;
import com.maximegens.foodtrucklillois.interfaces.RecyclerViewListePlatListener;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Adapter pour l'affichage de la liste des produit des categories des plats.
 */
public class ListePlatMenuAdapter extends RecyclerView.Adapter<ListePlatMenuAdapter.ViewHolder> {

    private Fragment fragment;
    private List<Plat> lesPlats;
    private RecyclerViewListePlatListener callback;

    /**
     * Constructeur prenant en entrée une liste.
     */
    public ListePlatMenuAdapter(List<Plat> lesPlats, Fragment fragment) {
        this.lesPlats = lesPlats;
        this.fragment = fragment;
        this.callback = (RecyclerViewListePlatListener) this.fragment;
    }

    /**
     * Permet de créer les viewHolder et d'indiquer la vue à inflater.
     * @param parent Le parent.
     * @param viewType le viewType.
     * @return L'objet ListeFTHolder.
     */
    public ListePlatMenuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_liste_plat, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    /**
     * Fonction pour remplir la cellule avec les données de chaque plat.
     * @param holder Le holder.
     * @param position La position de l'item.
     */
    public void onBindViewHolder(ViewHolder  holder, final int position) {
        final Plat plat = lesPlats.get(position);

        if(plat != null){
            String url = plat.getUrlPhoto();
            holder.titlePlat.setText(plat.getNomPlat());

            if(url != null && fragment != null){
                Picasso.with(fragment.getContext())
                        .load(url)
                        .placeholder(R.drawable.progress_animation_loader)
                        .error(R.drawable.photonotavailable)
                        .fit().centerInside()
                        .into(holder.imagePlat);
            }

            holder.cardViewPlat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onClickPlat(plat);
                }
            });
        }
    }

    /**
     * Donne le nombre de plat dans la liste.
     * @return Le nombre de plat.
     */
    @Override
    public int getItemCount() {
        return lesPlats.size();
    }

    /**
     * Class view Holder
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titlePlat;
        public CardView cardViewPlat;
        public ImageView imagePlat;
        public ViewHolder(View v) {
            super(v);
            titlePlat = (TextView) v.findViewById(R.id.title_plat_card_view);
            cardViewPlat = (CardView) v.findViewById(R.id.card_view_liste_plat);
            imagePlat = (ImageView) v.findViewById(R.id.image_plat);
        }
    }
}
