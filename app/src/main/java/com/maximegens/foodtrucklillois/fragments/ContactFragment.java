package com.maximegens.foodtrucklillois.fragments;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.maximegens.foodtrucklillois.App;
import com.maximegens.foodtrucklillois.R;


public class ContactFragment extends Fragment {

    public static String TITLE = "Contact";
    private Spinner spinnerObjetMail;
    private ArrayAdapter<CharSequence> adapterObjet;
    private TextView adresseMail;
    private TextView adresseMailDeveloppeur;
    private EditText contenuMail;
    private Button buttonEnvoyer;
    private TextInputLayout adresseMailTextInput;
    private TextInputLayout contenuTextInput;

    /**
     * Creation du Fragment.
     * @return Une instance de ContactFragment.
     */
    public static ContactFragment newInstance() {
        ContactFragment fragment = new ContactFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contact, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        App.tracker.setScreenName(getString(R.string.ga_title_contact));

        getActivity().setTitle(getString(R.string.title_contact));

        spinnerObjetMail = (Spinner) view.findViewById(R.id.spinner_contact_objet);
        adapterObjet = ArrayAdapter.createFromResource(getContext(), R.array.objet_mail_array, R.layout.layout_drop_title_black);
        adapterObjet.setDropDownViewResource(R.layout.layout_drop_list);
        spinnerObjetMail.setAdapter(adapterObjet);

        buttonEnvoyer = (Button) view.findViewById(R.id.button_envoyer);
        adresseMail = (TextView) view.findViewById(R.id.adresse_mail_contact);
        adresseMailDeveloppeur = (TextView) view.findViewById(R.id.adresse_mail_dev);
        contenuMail = (EditText) view.findViewById(R.id.contenu_msg_contact);
        adresseMailTextInput = (TextInputLayout) view.findViewById(R.id.textInputLayout_mail_adresse);
        contenuTextInput = (TextInputLayout) view.findViewById(R.id.textInputLayout_mail_contenu);
        adresseMailDeveloppeur.setText(Html.fromHtml(getString(R.string.adresse_mail_developpeur)));
        buttonEnvoyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isErrorInFormulaire()){
                    Intent email = new Intent(android.content.Intent.ACTION_SEND);
                    email.setType("message/rfc822");
                    email.putExtra(Intent.EXTRA_EMAIL, new String[] { getString(R.string.adresse_mail_developpeur) });
                    email.putExtra(Intent.EXTRA_SUBJECT, spinnerObjetMail.getSelectedItem().toString());
                    email.putExtra(Intent.EXTRA_TEXT, contenuMail.getText());
                    startActivity(Intent.createChooser(email,"Envoyer par : "));
                }
            }
        });

        adresseMailDeveloppeur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent email = new Intent(android.content.Intent.ACTION_SEND);
                email.setType("message/rfc822");
                email.putExtra(Intent.EXTRA_EMAIL, new String[] { getString(R.string.adresse_mail_developpeur) });
                startActivity(Intent.createChooser(email,"Envoyer par : "));
            }
        });
    }

    /**
     * Indique si les champs remplis pour le formulaire sont corrects ou non.
     * @return vrai si les valeurs du formulaires sont correct.
     */
    private boolean isErrorInFormulaire(){
        boolean ok = true;
        if(adresseMail.getText() == null || adresseMail.getText().toString().equals("")){
            adresseMailTextInput.setErrorEnabled(true);
            adresseMailTextInput.setError(getString(R.string.error_adresse_mail));
            ok = false;
        }
        if(contenuMail.getText() == null || contenuMail.getText().toString().equals("")){
            contenuTextInput.setErrorEnabled(true);
            contenuTextInput.setError(getString(R.string.error_contenu_mail));
            ok = false;
        }

        return ok;
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
