package com.maximegens.foodtrucklillois.interfaces;

import com.maximegens.foodtrucklillois.beans.menu.CategoriePlat;

/**
 * Interface définisant les actions de l'interface de la recyclerView des categories du menu.
 */
public interface RecyclerViewListeCatePlatListener {


    /**
     * Methode appelé lors du clique sur une categorie du menu.
     * @param  catPlat la categorie sélectionnée.
     */
    void onClickCatPlat(CategoriePlat catPlat);

}
