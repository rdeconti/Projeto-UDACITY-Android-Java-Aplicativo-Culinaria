package com.rosemeire.deconti.bakehouse.userInterface;

/* ********************************************************************************************** */
/* UDACITY Android Developer NanoDegree Program
/* Created by Rosemeire Deconti on 30/10/2018
/* ********************************************************************************************** */

import android.content.Context;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.rosemeire.deconti.bakehouse.R;
import com.rosemeire.deconti.bakehouse.database.BakeHouseDataBase;
import com.rosemeire.deconti.bakehouse.model.modelIngredient;

import java.util.List;

/* ************************************************************************************************/
/* *** Get ingredients of recipe to WIDGET
/* ***
/* ************************************************************************************************/
public class WidgetRecipesIngredients implements RemoteViewsService.RemoteViewsFactory {

    private final Context mContext;
    private List<modelIngredient> mRecipesIngredients;

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    public WidgetRecipesIngredients(Context context) {

        this.mContext = context;

    }

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    @Override
    public void onCreate() {

    }

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    @Override
    public void onDataSetChanged() {
        mRecipesIngredients = BakeHouseDataBase.
                getInstance(mContext.getApplicationContext()).ingredientDAO().getAll();
    }

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    @Override
    public void onDestroy() {

        mRecipesIngredients = null;

    }

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    @Override
    public int getCount() {

        return mRecipesIngredients != null ? mRecipesIngredients.size() : 0;

    }

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    @Override
    public RemoteViews getViewAt(int position) {

        modelIngredient ingredient = mRecipesIngredients.get(position);

        RemoteViews remoteVies = new RemoteViews(mContext.getPackageName(),
                R.layout.widget_recipes_ingredient);

        remoteVies.setTextViewText(R.id.textView_widgetTitle, ingredient.getIngredient() +
                " " + String.valueOf(ingredient.getQuantity()) + " " + (ingredient.getMeasure()));

        return remoteVies;
    }

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    @Override
    public RemoteViews getLoadingView() {

        return null;

    }

    @Override
    public int getViewTypeCount() {

        return 1;

    }

    @Override
    public long getItemId(int position) {

        return position;

    }

    @Override
    public boolean hasStableIds() {

        return true;

    }
}
