package com.maximegens.foodtrucklillois.fragments;


import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maximegens.foodtrucklillois.App;
import com.maximegens.foodtrucklillois.R;


public class AProposFragment extends Fragment {

    private TextView version;

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
        View vue = inflater.inflate(R.layout.fragment_a_propos, container, false);
        version = (TextView) vue.findViewById(R.id.version_apropos);
        return vue;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        App.tracker.setScreenName(getString(R.string.ga_title_a_propos));

        try {
            PackageInfo pInfo = null;
            pInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
            version.setText(pInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            Log.d("VersionName","Impossible de récupérer le versionName de l'application");
        }

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


}
