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

/* ************************************************************************************************/
/* *** Treat insert recipe to widget database
/* ************************************************************************************************/
public class InsertWidgetRecipeTask extends AsyncTask<modelRecipe, Void, modelRecipe> {

    private final BakeHouseDataBase mDb;
    private final InsertRecipeCallBack mCallBack;

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    public interface InsertRecipeCallBack{
        void onInsert(modelRecipe recipe);
    }

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    public InsertWidgetRecipeTask(Context context, InsertRecipeCallBack callBack) {

        mDb = BakeHouseDataBase.getInstance(context);
        mCallBack = callBack;

    }

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    @Override
    protected modelRecipe doInBackground(modelRecipe... recipes) {

        modelRecipe recipeRecord = recipes[0];

        mDb.recipeDAO().clear();
        mDb.recipeDAO().insert(recipeRecord);

        for(modelIngredient i : recipeRecord.getIngredients()){
            i.setRecipeId(recipeRecord.getId());
        }

        mDb.ingredientDAO().insertAll(recipeRecord.getIngredients());

        for (modelStep s :recipeRecord.getSteps()){
             s.setRecipeId(recipeRecord.getId());
        }

        mDb.stepDAO().insertAll(recipeRecord.getSteps());

        return recipeRecord;
    }

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    @Override
    protected void onPostExecute(modelRecipe recipe) {
        super.onPostExecute(recipe);

        if(mCallBack != null) {
            mCallBack.onInsert(recipe);
        }
    }
}
