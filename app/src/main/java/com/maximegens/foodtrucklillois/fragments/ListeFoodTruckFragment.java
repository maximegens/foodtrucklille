package com.maximegens.foodtrucklillois.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maximegens.foodtrucklillois.R;

/**
 * Class ListeFoodTruckFragment.
 */
public class ListeFoodTruckFragment extends Fragment {

    ListeFoodTruckFragmentCallback listeFoodTruckFragmentCallback;
    TextView title;

    /**
     * Creation du Fragment
     * @return
     */
    public static ListeFoodTruckFragment newInstance() {
        ListeFoodTruckFragment fragment = new ListeFoodTruckFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Demande d’inflater la vue à utiliser par ce fragment, ici on inflate la vue contenant la liste des FT
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_liste_ft,container,false);
    }

    /**
     * Le fragment possède enfin une vue, nous pouvons récupérer nos widgets (TextView, etc.) puis les modifier
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        title = (TextView) view.findViewById(R.id.title);
        title.setText("Je suis un fragment :P");

        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listeFoodTruckFragmentCallback != null){
                    listeFoodTruckFragmentCallback.onFoodTruckClicked();
                }
            }
        });
    }


    /**
     * Interface permettant de communiquer avec l'activity
     */
    public interface ListeFoodTruckFragmentCallback{

        /**
         * Clique sur un Fodd Truck
         */
        void onFoodTruckClicked();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ListeFoodTruckFragmentCallback)
            listeFoodTruckFragmentCallback = (ListeFoodTruckFragmentCallback) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listeFoodTruckFragmentCallback = null;
    }

}
