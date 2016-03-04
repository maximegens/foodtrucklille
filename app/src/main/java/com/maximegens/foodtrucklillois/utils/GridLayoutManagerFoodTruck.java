package com.maximegens.foodtrucklillois.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v7.widget.GridLayoutManager;

/**
 * Classe de creation des LayoutManager.
 */
public class GridLayoutManagerFoodTruck {

    Context ctx;

    /**
     * Constructeur.
     * @param ctx le context.
     */
    public GridLayoutManagerFoodTruck(Context ctx){
        this.ctx = ctx;
    }

    /**
     * Creation d'un gridLayoutManager pour le mode Portrait.
     * @return un gridLayoutManager pour le mode Portrait.
     */
    public GridLayoutManager buildGridLayoutPortrait() {
        // Definit l'agencement pour l'affichage en mode portrait
        GridLayoutManager gridLayoutManagerPortrait = new GridLayoutManager(ctx, 2, GridLayoutManager.VERTICAL, false);
        gridLayoutManagerPortrait.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position == 0 ? 2 : 1;
            }
        });
        return gridLayoutManagerPortrait;
    }

    /**
     * Creation d'un gridLayoutManager pour le mode Portrait.
     * @return un gridLayoutManager pour le mode Portrait.
     */
    public GridLayoutManager buildGridLayoutPortraitAfterRecherche() {
        // Definit l'agencement pour l'affichage en mode portrait
        return new GridLayoutManager(ctx, 2, GridLayoutManager.VERTICAL, false);
    }

    /**
     * Creation d'un gridLayoutManager pour le mode paysage.
     * @return un gridLayoutManager pour le mode paysage.
     */
    public GridLayoutManager buildGridLayoutLandscape(){

        GridLayoutManager gridLayoutManagerLandscape = new GridLayoutManager(ctx,3,GridLayoutManager.VERTICAL,false);
        gridLayoutManagerLandscape.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position == 0 ? 2 : 1;
            }
        });
        return gridLayoutManagerLandscape;
    }

    /**
     * Creation d'un gridLayoutManager pour le mode paysage.
     * @return un gridLayoutManager pour le mode paysage.
     */
    public GridLayoutManager buildGridLayoutLandscapeAfterRecherche(){
        return new GridLayoutManager(ctx,3,GridLayoutManager.VERTICAL,false);

    }

    /**
     * Creation d'un gridLayoutManager pour le mode Portrait des categorie de menu.
     * @return un gridLayoutManager pour le mode Portrait.
     */
    public GridLayoutManager buildGridLayoutPortraitForCatMenu() {
        // Definit l'agencement pour l'affichage en mode portrait
        return new GridLayoutManager(ctx, 2, GridLayoutManager.VERTICAL, false);
    }

    /**
     * Creation d'un gridLayoutManager pour le mode paysage des categorie de menu.
     * @return un gridLayoutManager pour le mode paysage.
     */
    public GridLayoutManager buildGridLayoutLandscapeForCatMenu(){
        return new GridLayoutManager(ctx,3,GridLayoutManager.VERTICAL,false);

    }

}
