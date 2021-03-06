package com.maximegens.foodtrucklillois.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.maximegens.foodtrucklillois.R;
import com.maximegens.foodtrucklillois.beans.AdresseFoodTruck;
import com.maximegens.foodtrucklillois.beans.FoodTruck;
import com.maximegens.foodtrucklillois.beans.PlanningFoodTruck;
import com.maximegens.foodtrucklillois.interfaces.RecyclerViewPlanningListener;
import com.maximegens.foodtrucklillois.network.Internet;
import com.maximegens.foodtrucklillois.utils.GestionnaireHoraire;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter pour l'affichage de la liste des ft du planning.
 */
public class PlanningAdapter extends RecyclerView.Adapter<PlanningAdapter.ViewHolder> {

    private final RecyclerViewPlanningListener callback;
    private List<FoodTruck> lesFts;
    private final Context context;
    private int numJour;

    /**
     * Constructeur prenant en entrée une liste.
     */
    public PlanningAdapter(List<FoodTruck> lesFts, Context context, int numJour) {
        this.lesFts = lesFts;
        this.context = context;
        this.numJour = numJour;
        this.callback = (RecyclerViewPlanningListener) this.context;
    }

    /**
     * Permet de créer les viewHolder et d'indiquer la vue à inflater.
     * @param parent Le parent.
     * @param viewType le viewType.
     * @return L'objet ListeFTHolder.
     */
    public PlanningAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_planning, parent, false);
        return new ViewHolder(v);
    }

    /**
     * Fonction pour remplir la cellule avec les données de chaque food truck.
     * @param holder Le holder.
     * @param position La position de l'item.
     */
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if(lesFts != null){

            final FoodTruck ft = lesFts.get(position);
            List<AdresseFoodTruck> listeAdresses = new ArrayList<>();

            holder.loader.setVisibility(View.VISIBLE);

            // Selection du bon numéro du jour si il l'utilisateur a choisi "aujourd'hui"
            if(numJour == 0){
                numJour = GestionnaireHoraire.getNumeroJourDansLaSemaine();
            }

            holder.titleFT.setText(ft.getNom());

            // Récupération des adresses.
            PlanningFoodTruck planning = ft.getPlanningByJour(numJour);
            if(planning != null && planning.getMidi() != null && planning.getMidi().getAdresses() != null){
                listeAdresses.addAll(planning.getMidi().getAdresses());
            }
            if(planning != null && planning.getSoir() != null && planning.getSoir().getAdresses() != null){
                listeAdresses.addAll(planning.getSoir().getAdresses());
            }
            PlanningAdresseAdapter adapterAdresse = new PlanningAdresseAdapter(listeAdresses, context, ft);
            holder.listeAdressePlanning.setAdapter(adapterAdresse);

            // Récupération de la tranche horaire.
            if(numJour != 0){
                holder.ouverture1.setText(ft.getTrancheHoraireByDay(numJour));
            }else{
                holder.ouverture1.setText(ft.getTrancheHoraire());
            }

            // Récupération du logo.
            if(ft.getLogo() != null){
                int resID = context.getResources().getIdentifier(ft.getLogo() , "mipmap", context.getPackageName());
                if(resID != 0){
                    Picasso.with(context)
                            .load(resID)
                            .error(R.mipmap.photonotavailable)
                            .fit()
                            .centerCrop()
                            .into(holder.logoFt,new Callback() {
                                @Override
                                public void onSuccess() {
                                    holder.loader.setVisibility(View.GONE);
                                }
                                @Override
                                public void onError() {
                                    holder.loader.setVisibility(View.GONE);
                                }
                            });
                }else if(ft.getUrlLogo() != null && !ft.getUrlLogo().isEmpty() && Internet.isNetworkAvailable(context)){
                    // Récupératation en ligne
                    Picasso.with(context)
                            .load(ft.getUrlLogo())
                            .error(R.mipmap.photonotavailable)
                            .fit()
                            .centerCrop()
                            .into(holder.logoFt,new Callback() {
                                @Override
                                public void onSuccess() {
                                    holder.loader.setVisibility(View.GONE);
                                }
                                @Override
                                public void onError() {
                                    holder.loader.setVisibility(View.GONE);
                                }
                            });
                }else{
                    int photoNonAvailable = context.getResources().getIdentifier("photonotavailable" , "mipmap", context.getPackageName());
                    holder.logoFt.setImageResource(photoNonAvailable);
                }
            }

            holder.cardViewPlanning.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onClickFtPlanning(ft);
                }
            });
        }
    }

    /**
     * Donne le nombre de ft dans la liste.
     * @return Le nombre de ft.
     */
    @Override
    public int getItemCount() {
        return lesFts != null ? lesFts.size() : 0;
    }


    /**
     * Class view Holder
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView titleFT;
        public final CardView cardViewPlanning;
        public final TextView ouverture1;
        public final TextView adresse;
        public final ImageView logoFt;
        public final RecyclerView listeAdressePlanning;
        public final ProgressBar loader;
        public ViewHolder(View v) {
            super(v);
            titleFT = (TextView) v.findViewById(R.id.nom_ft_planning_card_view);
            ouverture1 = (TextView) v.findViewById(R.id.planning_horaire_ouveture_1);
            adresse = (TextView) v.findViewById(R.id.planning_adresse);
            logoFt = (ImageView) v.findViewById(R.id.logo_ft_planning_card_view);
            listeAdressePlanning = (RecyclerView) v.findViewById(R.id.liste_adresse_planning);
            listeAdressePlanning.setLayoutManager(new LinearLayoutManager(context));
            cardViewPlanning = (CardView) v.findViewById(R.id.card_view_planning_ft);
            loader = (ProgressBar) v.findViewById(R.id.loader_ft_planning);
        }
    }

}
