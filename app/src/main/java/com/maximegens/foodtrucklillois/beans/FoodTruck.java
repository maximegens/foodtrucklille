package com.maximegens.foodtrucklillois.beans;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.maximegens.foodtrucklillois.R;
import com.maximegens.foodtrucklillois.beans.menu.Menu;
import com.maximegens.foodtrucklillois.utils.Constantes;
import com.maximegens.foodtrucklillois.utils.GestionnaireHoraire;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Bean représantant un Food Truck.
 */
public class FoodTruck implements Parcelable{

    public static String KEY_FOOD_TRUCK = "foodtruck";
    private int id;
    private String nom;
    private String siteWeb;
    private String urlPageFacebook;
    private String descriptionBreve;
    private String descriptionLongue;
    private String gammeDePrixprix;
    private String tenueVestimentaire;
    private String moyensDePaiement;
    private String services;
    private String specialites;
    private String cuisine;
    private String telephone;
    private String email;
    private String dateOuverture;
    private String logo;
    private String urlLogo;
    private Menu menu;
    private List<PlanningFoodTruck> planning;

    private float distanceFromUser;

    /**
     * Constructeur.
     * @param nom
     */
    public FoodTruck(String nom) {
        this.nom = nom;
    }

    /**
     * Construceur.
     * @param nom Le nom.
     * @param logo Le nom de l'image en interne représentant le logo.
     */
    public FoodTruck(String nom, String logo) {
        this.nom = nom;
        this.logo = logo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getSiteWeb() {
        return siteWeb;
    }

    public void setSiteWeb(String siteWeb) {
        this.siteWeb = siteWeb;
    }

    public String getUrlPageFacebook() {
        return urlPageFacebook;
    }

    public void setUrlPageFacebook(String urlPageFacebook) {
        this.urlPageFacebook = urlPageFacebook;
    }

    public String getDescriptionBreve() {
        return descriptionBreve;
    }

    public void setDescriptionBreve(String descriptionBreve) {
        this.descriptionBreve = descriptionBreve;
    }

    public String getDescriptionLongue() {
        return descriptionLongue;
    }

    public void setDescriptionLongue(String description) {
        this.descriptionLongue = descriptionLongue;
    }

    public String getGammeDePrixprix() {
        return gammeDePrixprix;
    }

    public void setGammeDePrixprix(String gammeDePrixprix) {
        this.gammeDePrixprix = gammeDePrixprix;
    }

    public String getTenueVestimentaire() {
        return tenueVestimentaire;
    }

    public void setTenueVestimentaire(String tenueVestimentaire) {
        this.tenueVestimentaire = tenueVestimentaire;
    }

    public String getMoyensDePaiement() {
        return moyensDePaiement;
    }

    public void setMoyensDePaiement(String moyensDePaiement) {
        this.moyensDePaiement = moyensDePaiement;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getSpecialites() {
        return specialites;
    }

    public void setSpecialites(String specialites) {
        this.specialites = specialites;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDateOuverture() {
        return dateOuverture;
    }

    public void setDateOuverture(String dateOuverture) {
        this.dateOuverture = dateOuverture;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getUrlLogo() {
        return urlLogo;
    }

    public void setUrlLogo(String urlLogo) {
        this.urlLogo = urlLogo;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public List<PlanningFoodTruck> getPlanning() {
        return planning;
    }

    public void setPlanning(List<PlanningFoodTruck> planning) {
        this.planning = planning;
    }

    public float getDistanceFromUser() {
        return distanceFromUser;
    }

    public void setDistanceFromUser(float distanceFromUser) {
        this.distanceFromUser = distanceFromUser;
    }

    @Override
    public String toString() {
        return nom;
    }

    /**
     * Methode de tri de la liste des Foods Trucks en fonction de la recherche de l'utilisateur.
     * @param lesFTs La liste des FTS à triée.
     * @param recherche La recherche de l'utilisateur.
     * @return La liste des Foods Trucks Triée.
     */
    public static List<FoodTruck> filterListeFTs(List<FoodTruck> lesFTs, String recherche) {
        recherche = recherche.toLowerCase();

        final List<FoodTruck> filteredFTList = new ArrayList<>();
        for (FoodTruck model : lesFTs) {
            final String text = model.getNom().toLowerCase();
            if (text.startsWith(recherche)) {
                filteredFTList.add(model);
            }
        }
        return filteredFTList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nom);
        dest.writeString(siteWeb);
        dest.writeString(urlPageFacebook);
        dest.writeString(descriptionBreve);
        dest.writeString(descriptionLongue);
        dest.writeString(gammeDePrixprix);
        dest.writeString(tenueVestimentaire);
        dest.writeString(moyensDePaiement);
        dest.writeString(services);
        dest.writeString(specialites);
        dest.writeString(cuisine);
        dest.writeString(telephone);
        dest.writeString(email);
        dest.writeString(dateOuverture);
        dest.writeString(logo);
        dest.writeString(urlLogo);
        dest.writeParcelable(menu, flags);
        dest.writeTypedList(planning);
        dest.writeFloat(distanceFromUser);

    }

    public static final Parcelable.Creator<FoodTruck> CREATOR = new Parcelable.Creator<FoodTruck>()
    {
        @Override
        public FoodTruck createFromParcel(Parcel source)
        {
            return new FoodTruck(source);
        }

        @Override
        public FoodTruck[] newArray(int size)
        {
            return new FoodTruck[size];
        }
    };

    public FoodTruck(Parcel in) {

        this.id = in.readInt();
        this.nom = in.readString();
        this.siteWeb = in.readString();
        this.urlPageFacebook = in.readString();
        this.descriptionBreve = in.readString();
        this.descriptionLongue = in.readString();
        this.gammeDePrixprix = in.readString();
        this.tenueVestimentaire = in.readString();
        this.moyensDePaiement = in.readString();
        this.services = in.readString();
        this.specialites = in.readString();
        this.cuisine = in.readString();
        this.telephone = in.readString();
        this.email = in.readString();
        this.dateOuverture = in.readString();
        this.logo = in.readString();
        this.urlLogo = in.readString();
        this.menu = (Menu)in.readParcelable(Menu.class.getClassLoader());
        this.planning = new ArrayList<PlanningFoodTruck>();
        in.readTypedList(planning,PlanningFoodTruck.CREATOR);
        this.distanceFromUser = in.readFloat();
    }

    /**
     * Construction de l'affichage des horaires du food truck.
     * @return la chaine contenant les horaires des food trucks.
     */
    public String constructionHoraires(){
        StringBuilder horaires = new StringBuilder("");
        String fermer = Constantes.FERMER;

        if(getPlanning() != null ){

            // Parcours de la liste des jours
            for(PlanningFoodTruck planning : getPlanning()){
                // Mise en majuscule de la premier lettre.
                String jour = planning.getNomJour().substring(0, 1).toUpperCase() + planning.getNomJour().substring(1);
                horaires.append(jour+" : "+ Constantes.RETOUR_CHARIOT);
                if(planning.isFermerToday()){
                    horaires.append(Constantes.TABULATION_DOUBLE+ fermer +Constantes.RETOUR_CHARIOT);
                }else{
                    if(planning.isOpenMidi()) {
                        horaires.append(Constantes.TABULATION_DOUBLE+"Midi : "+planning.getMidi().getHeureOuvertureEnString()+" - "+planning.getMidi().getHeureFermetureEnString()+Constantes.RETOUR_CHARIOT);
                    }else {
                        horaires.append(Constantes.TABULATION_DOUBLE+"Midi : "+ fermer+ Constantes.RETOUR_CHARIOT);
                    }
                    if(planning.isOpenSoir()) {
                        horaires.append(Constantes.TABULATION_DOUBLE+"Soir : "+planning.getSoir().getHeureOuvertureEnString()+" - "+planning.getSoir().getHeureFermetureEnString()+Constantes.RETOUR_CHARIOT);
                    }else {
                        horaires.append(Constantes.TABULATION_DOUBLE+"Soir : "+fermer+Constantes.RETOUR_CHARIOT);
                    }
                }
                horaires.append(Constantes.RETOUR_CHARIOT);
            }
        }
        return horaires.toString();
    }

    /**
     * Methode permettant de savoir si le food truc est actuellement ouvert ou fermé.
     */
    public boolean isOpenNow() {

        PlanningFoodTruck planning = getPlaningToday();
        String horaireOuverture = null;
        String horaireFermeture = null;

        if (planning != null) {

            // On verifie si on se trouve le midi ou le soir afin de récupérer les horaires d"ouverture correspondant.
            if (planning.getMidi() != null && GestionnaireHoraire.isMidiOrBeforeMidi()) {
                horaireOuverture = planning.getMidi().getHoraireOuverture();
                horaireFermeture = planning.getMidi().getHoraireFermeture();
            } else if (planning.getSoir() != null && GestionnaireHoraire.isSoirOrBeforeSoirButAfterMidi()) {
                horaireOuverture = planning.getSoir().getHoraireOuverture();
                horaireFermeture = planning.getSoir().getHoraireFermeture();
            }

            // On verifie si le food truck est actutellement ouvert entre les deux horaires récupérés.
            if (horaireOuverture != null && horaireFermeture != null) {
                Calendar calendarOuverture = GestionnaireHoraire.createCalendar(horaireOuverture);
                Calendar calendarFermeture = GestionnaireHoraire.createCalendar(horaireFermeture);
                return GestionnaireHoraire.isOpenBetween(GestionnaireHoraire.createCalendarToday(), calendarOuverture, calendarFermeture);
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    /**
     * Methode permettant de savoir si le food truc est ouvert aujoud'hui.
     */
    public boolean isOpenToday() {

        PlanningFoodTruck planning = getPlaningToday();

        // On verifie si il existe un planning pour le midi ou pour le soir, dans ce cas le FT est ou a bien été ouvert aujourd'hui.
        if (planning != null) {
            return planning.getMidi() != null || planning.getSoir() != null ? true : false;
        } else {
            return false;
        }
    }

    /**
     * Indique si l'heure passé en paramétre se trouve avant la derniéré heure de fermeture du food truc poour la journée.
     * @param cal le calendrier de la date a tester.
     * @return vrai si l'heure se situe avant la fermeture.
     */
    public boolean isDateBeforeLastHoraireFermeture(Calendar cal){

        PlanningFoodTruck planning = getPlaningToday();
        String horaireFermeture = "";
        boolean ok = false;

        if (planning != null) {
            // On récupere de base le derniere horaire, celui du soir
            if (planning.getSoir() != null) {
                horaireFermeture = planning.getSoir().getHoraireFermeture();
            }
            // sinon on prend celui du midi
            else if (planning.getMidi() != null) {
                horaireFermeture = planning.getMidi().getHoraireFermeture();
            }else{
                ok =  false;
            }

            // Si on trouve un horaire de fermeture alors on verifie si l'heure est avant celui passé en parametre.
            if(horaireFermeture != null && !horaireFermeture.isEmpty()){
                Calendar calFt = GestionnaireHoraire.createCalendar(horaireFermeture);
                return GestionnaireHoraire.isBefore(cal,calFt);
            }
        }
        return ok;
    }

    /**
     * Donne le planing du jour en cours.
     * @return
     */
    public PlanningFoodTruck getPlaningToday(){

        // Creation du calendrier
        Calendar calendarToday = GestionnaireHoraire.createCalendarToday();
        // Recuperation du numero du jour
        int numJourTab = GestionnaireHoraire.getNumeroJourDansLaSemaine(calendarToday) - 1;

        if (getPlanning() != null && getPlanning().get(numJourTab) != null){
            return getPlanning().get(numJourTab);
        }else{
            return null;
        }
    }

    /**
     * Donne la prochaine horaire d'ouverture du food truck pour aujourd'hui.
     * @return l'heure d'ouverture prochaien du Food truck.
     */
    public String getNextOuvertureToday(){

        // Creation du calendrier
        Calendar calendarToday = GestionnaireHoraire.createCalendarToday();
        String horaireOuverture = "";

        // test de l'ouverture actuelle ou prochaine sur la journee du ft.
        if(isOpenToday() && isDateBeforeLastHoraireFermeture(calendarToday)){
            PlanningFoodTruck planning = getPlaningToday();

            if (planning != null) {
                // On verifie si on se trouve le midi ou le soir afin de récupérer les horaires d"ouverture correspondant.
                if (planning.getMidi() != null && GestionnaireHoraire.isMidiOrBeforeMidi()) {
                    horaireOuverture = planning.getMidi().getHeureOuvertureEnString();
                } else if (planning.getSoir() != null && GestionnaireHoraire.isSoirOrBeforeSoirButAfterMidi()) {
                    horaireOuverture = planning.getSoir().getHeureOuvertureEnString();
                }
            }
        }
        return horaireOuverture;
    }

    /**
     * Indique si il existe une adresse pour le food truck le midi en fonction du planning passé en paramétre.
     * @param planning le planning demandé.
     * @return true si le food truck posséde une adresse avec coordonnées GPS pour le midi.
     */
    public boolean existPlaningMidiAdresse(PlanningFoodTruck planning){
        return planning.getMidi() != null && planning.getMidi().getAdresses() != null && planning.getMidi().getAdresses().get(0) != null
                && planning.getMidi().getAdresses().get(0).getLatitude() != null && planning.getMidi().getAdresses().get(0).getLongitude() != null;
    }

    /**
     * Indique si il existe une adresse pour le food truck le soir en fonction du planning passé en paramétre.
     * @param planning le planning demandé.
     * @return true si le food truck posséde une adresse avec coordonnées GPS pour le soir.
     */
    public boolean existPlaningSoirAdresse(PlanningFoodTruck planning){
        return planning.getSoir() != null && planning.getSoir().getAdresses() != null && planning.getSoir().getAdresses().get(0) != null
                && planning.getSoir().getAdresses().get(0).getLatitude() != null && planning.getSoir().getAdresses().get(0).getLongitude() != null;
    }

    /**
     * Indique jusque quand est ouvert le FT.
     * @return un string indique jusque qaund el ft est ouvert.
     */
    public String getOuvertureJusque() {

        PlanningFoodTruck planning = getPlaningToday();
        if (isOpenNow() && planning != null) {
            if (GestionnaireHoraire.isMidiOrBeforeMidi() && planning.getMidi() != null) {
                return planning.getMidi().getHeureFermetureEnString();
            } else if (GestionnaireHoraire.isSoirOrBeforeSoirButAfterMidi() && planning.getSoir() != null) {
                return planning.getSoir().getHeureFermetureEnString();
            }
        }

        return "";
    }

    /**
     * Indique jusque quand est ouvert le FT.
     * @return un string indique jusque qaund el ft est ouvert.
     */
    public String getProchaineOuvertureToday() {

        PlanningFoodTruck planning = getPlaningToday();
        if(planning != null){
            if (GestionnaireHoraire.isMidiOrBeforeMidi() && planning.getMidi()!= null && GestionnaireHoraire.isTodayBeforeDate(planning.getMidi().getHoraireOuverture())) {
                return planning.getMidi().getHeureOuvertureEnString();
            } else if (GestionnaireHoraire.isSoirOrBeforeSoirButAfterMidi() && planning.getSoir() != null && GestionnaireHoraire.isTodayBeforeDate(planning.getSoir().getHoraireOuverture())) {
                return planning.getSoir().getHeureOuvertureEnString();
            }
        }
        return "";
    }

    /**
     * Retourne les coordonnées GPS du Food truck pour la journée.
     * @return
     */
    public LatLng getLatLon(){

        String latitude = null;
        String longitude = null;
        PlanningFoodTruck planning = getPlaningToday();

        // Recuperation de la latitude et de la longitude.
        if (GestionnaireHoraire.isMidiOrBeforeMidi()) {
            //TODO remplacer par l'adresse la plus proche, pour l'instant parmis toutes les adresses ont prends la premiere (.get(0)).
            if (planning != null && planning.getMidi() != null && planning.getMidi().getAdresses() != null) {
                latitude = planning.getMidi().getAdresses().get(0).getLatitude();
                longitude = planning.getMidi().getAdresses().get(0).getLongitude();
            }
        }
        if (GestionnaireHoraire.isSoirOrBeforeSoirButAfterMidi()) {
            if (planning != null && planning.getSoir() != null && planning.getSoir().getAdresses() != null) {
                latitude = planning.getSoir().getAdresses().get(0).getLatitude();
                longitude = planning.getSoir().getAdresses().get(0).getLongitude();
            }
        }
        return latitude != null && longitude != null ? new LatLng(Double.parseDouble(latitude),Double.parseDouble(longitude)) : null;
    }

}
