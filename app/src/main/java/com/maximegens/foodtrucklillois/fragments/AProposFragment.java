package com.maximegens.foodtrucklillois.fragments;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maximegens.foodtrucklillois.App;
import com.maximegens.foodtrucklillois.R;


public class AProposFragment extends Fragment {

    public static String TITLE = "APropos";

    /**
     * Creation du Fragment.
     * @return Une instance de EmplacementAllFragment.
     */
    public static AProposFragment newInstance() {
        AProposFragment fragment = new AProposFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_a_propos, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        App.tracker.setScreenName(getString(R.string.ga_title_a_propos));

        TextView lienEServices = (TextView) view.findViewById(R.id.lien_e_service);
        final String url = "http://portail.fil.univ-lille1.fr/portail/index.php?dipl=MInfo&sem=ESERVICE&ue=ACCUEIL&label=Pr%C3%A9sentation";
        String lien = "<a href="+url+">Présentation Master E-Services</a>";
        lienEServices.setText(Html.fromHtml(lien));
        lienEServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        getActivity().setTitle(getString(R.string.title_a_propos));

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
