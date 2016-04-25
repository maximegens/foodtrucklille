package com.maximegens.foodtrucklillois.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.maximegens.foodtrucklillois.R;
import com.maximegens.foodtrucklillois.adapters.PlanningAdapter;
import com.maximegens.foodtrucklillois.beans.FoodTruck;
import com.maximegens.foodtrucklillois.beans.PlanningFoodTruck;
import com.maximegens.foodtrucklillois.utils.Constantes;
import com.maximegens.foodtrucklillois.utils.GestionnaireHoraire;
import com.maximegens.foodtrucklillois.utils.SortFtByNom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class PlanningFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    private Spinner spinnerJour;
    private RecyclerView recyclerViewPlanning;
    private ArrayAdapter<CharSequence> adapterJourPlanning;
    private PlanningAdapter adapterPlanning;

    /**
     * Creation du Fragment.
     * @return Une instance de PlanningFragment.
     */
    public static PlanningFragment newInstance() {
        PlanningFragment fragment = new PlanningFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_planning, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle(getString(R.string.title_planning));

        spinnerJour = (Spinner) view.findViewById(R.id.spinner_planning);
        recyclerViewPlanning = (RecyclerView) view.findViewById(R.id.recycler_view_planning);

        adapterJourPlanning = ArrayAdapter.createFromResource(getContext(), R.array.semaine_array_jour_today, R.layout.spinner_item_planning);
        adapterJourPlanning.setDropDownViewResource(R.layout.layout_drop_list);

        adapterPlanning = new PlanningAdapter(new ArrayList<FoodTruck>(), getContext(), 0);
        recyclerViewPlanning.setAdapter(adapterPlanning);
        recyclerViewPlanning.setLayoutManager(new GridLayoutManager(getContext(), 1));

        spinnerJour.setAdapter(adapterJourPlanning);
        spinnerJour.setOnItemSelectedListener(this);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        List<FoodTruck> listeByJour = new ArrayList<>();
        int numJour = (position == 0 ? GestionnaireHoraire.getNumeroJourDansLaSemaine() : position);

        // Récupération des Foods Trucks correspondant au jour sélectionné.
        for (FoodTruck ft : Constantes.lesFTs) {
            PlanningFoodTruck planning = ft.getPlanningByJour(numJour);
            if(planning != null && (planning.getMidi() != null || planning.getSoir() != null)){
                listeByJour.add(ft);
            }
        }

        // tri par ordre alphabétique.
        Collections.sort(listeByJour, new SortFtByNom());

        adapterPlanning = new PlanningAdapter(listeByJour, getContext(), position);
        recyclerViewPlanning.setAdapter(adapterPlanning);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
