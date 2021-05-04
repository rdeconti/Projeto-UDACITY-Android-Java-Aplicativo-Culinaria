package com.rosemeire.deconti.bakehouse.service.task;

/* ********************************************************************************************** */
/* UDACITY Android Developer NanoDegree Program
/* Created by Rosemeire Deconti on 30/10/2018
/* ********************************************************************************************** */

import android.content.Context;
import android.os.AsyncTask;

import com.rosemeire.deconti.bakehouse.database.BakeHouseDataBase;
import com.rosemeire.deconti.bakehouse.model.modelIngredient;
import com.rosemeire.deconti.bakehouse.model.modelRecipe;
import com.rosemeire.deconti.bakehouse.model.modelStep;

import java.util.ArrayList;

/* TODO RUBRIC POINT: Application has a widget on initial screen */

/* ************************************************************************************************/
/* *** Get recipe data to the widget
/* ************************************************************************************************/
public class SelectWidgetRecipeTask extends AsyncTask<Void, Void, modelRecipe> {

    private final BakeHouseDataBase mDb;
    private final SelectRecipeCallBack mCallBack;

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    public SelectWidgetRecipeTask(Context context, SelectRecipeCallBack callBack) {

        this.mDb = BakeHouseDataBase.getInstance(context);
        this.mCallBack = callBack;

    }

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    @Override
    protected modelRecipe doInBackground(Void... voids) {

        modelRecipe recipe = mDb.recipeDAO().get();

        if (recipe != null) {
            recipe.setIngredients((ArrayList<modelIngredient>) mDb.ingredientDAO().getAll());
            recipe.setSteps((ArrayList<modelStep>) mDb.stepDAO().getAll());
        }

        return recipe;
    }

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    @Override
    protected void onPostExecute(modelRecipe recipe) {
        super.onPostExecute(recipe);

        if(mCallBack != null ){
            mCallBack.onSelect(recipe);
        }
    }

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    public interface SelectRecipeCallBack {
        void onSelect(modelRecipe recipe);
    }
}
