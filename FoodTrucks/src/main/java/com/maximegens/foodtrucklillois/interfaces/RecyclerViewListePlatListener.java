package com.maximegens.foodtrucklillois.interfaces;

import com.maximegens.foodtrucklillois.beans.menu.Plat;

/**
 * Interface définisant les actions de l'interface de la recyclerView des plats.
 */
public interface RecyclerViewListePlatListener {

    /**
     * Methode appelé lors du clique sur un produit (plat) du menu.
     * @param plat le plat sélectionné.
     */
    void onClickPlat(Plat plat);

    /**
     * Methode permettant de revenir à la liste des catégories.
     */
    void retourListeCategorie();
}
