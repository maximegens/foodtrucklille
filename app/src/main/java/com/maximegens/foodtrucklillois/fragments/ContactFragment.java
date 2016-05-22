package com.maximegens.foodtrucklillois.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.maximegens.foodtrucklillois.App;
import com.maximegens.foodtrucklillois.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Classe fragment contact.
 */
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
    private String[] tabChoix;

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
        buttonEnvoyer = (Button) view.findViewById(R.id.button_envoyer);
        adresseMail = (TextView) view.findViewById(R.id.adresse_mail_contact);
        adresseMailDeveloppeur = (TextView) view.findViewById(R.id.adresse_mail_dev);
        contenuMail = (EditText) view.findViewById(R.id.contenu_msg_contact);
        adresseMailTextInput = (TextInputLayout) view.findViewById(R.id.textInputLayout_mail_adresse);
        contenuTextInput = (TextInputLayout) view.findViewById(R.id.textInputLayout_mail_contenu);

        tabChoix = getResources().getStringArray(R.array.objet_mail_array);
        adresseMailDeveloppeur.setText(Html.fromHtml(getString(R.string.adresse_mail_developpeur)));

        adapterObjet = ArrayAdapter.createFromResource(getContext(), R.array.objet_mail_array, R.layout.layout_drop_title_black);
        adapterObjet.setDropDownViewResource(R.layout.layout_drop_list);
        spinnerObjetMail.setAdapter(adapterObjet);

        /**
         * Selection d'une valeur pour l'objet dans le spinner.
         */
        spinnerObjetMail.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = (String) parent.getItemAtPosition(position);

                if(item.equals(tabChoix[0].toString())){
                    ((TextView) parent.getChildAt(0)).setTextColor(ContextCompat.getColor(getContext(), R.color.grey_400));
                }else {
                    ((TextView) parent.getChildAt(0)).setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        /**
         * Clique sur le bouton envoyer.
         */
        buttonEnvoyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isErrorInFormulaire()){
                    Intent email = new Intent(android.content.Intent.ACTION_SEND);
                    email.setType("message/rfc822");
                    email.putExtra(Intent.EXTRA_EMAIL, adresseMail.getText().toString());
                    email.putExtra(Intent.EXTRA_SUBJECT, spinnerObjetMail.getSelectedItem().toString());
                    email.putExtra(Intent.EXTRA_TEXT, contenuMail.getText());
                    startActivity(Intent.createChooser(email,"Envoyer par : "));
                }
            }
        });

        /**
         * Clique sur l'adresse email.
         */
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
        if(spinnerObjetMail.getSelectedItem().toString().equals(tabChoix[0].toString())){
            ((TextView) spinnerObjetMail.getChildAt(0)).setTextColor(ContextCompat.getColor(getContext(), R.color.red_700));
            ok = false;
        }else{
            ((TextView) spinnerObjetMail.getChildAt(0)).setTextColor(ContextCompat.getColor(getContext(), R.color.black));
        }
        if(adresseMail.getText() == null || !isEmailValid(adresseMail.getText().toString())){
            adresseMailTextInput.setErrorEnabled(true);
            adresseMailTextInput.setError(getString(R.string.error_adresse_mail));
            ok = false;
        }else{
            adresseMailTextInput.setError(null);
        }
        if(contenuMail.getText() == null || contenuMail.getText().toString().equals("")){
            contenuTextInput.setErrorEnabled(true);
            contenuTextInput.setError(getString(R.string.error_contenu_mail));
            ok = false;
        }else{
            contenuTextInput.setError(null);
        }

        return ok;
    }

    /**
     * Verifie si il s'agit d'une adresse e-mail valide.
     *
     * @param email l'email a verifier
     * @return boolean vrai si il s'agit d'un mail valide.
     */
    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
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
