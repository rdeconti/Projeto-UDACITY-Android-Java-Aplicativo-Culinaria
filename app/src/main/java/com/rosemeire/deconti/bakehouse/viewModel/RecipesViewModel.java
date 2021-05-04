package com.rosemeire.deconti.bakehouse.viewModel;

/* ********************************************************************************************** */
/* UDACITY Android Developer NanoDegree Program
/* Created by Rosemeire Deconti on 30/10/2018
/* ********************************************************************************************** */

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.rosemeire.deconti.bakehouse.model.modelRecipe;
import com.rosemeire.deconti.bakehouse.utilities.UtilitiesJson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/* ************************************************************************************************/
/* *** View Model: RECIPES
/* ************************************************************************************************/
public class RecipesViewModel extends AndroidViewModel {

    public interface LoadRecipeCallBack {
        void onError(Throwable t);
    }

    private MutableLiveData<ArrayList<modelRecipe>> mRecipeMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<modelRecipe> mRecipe = new MutableLiveData<>();
    private LoadRecipeCallBack loadRecipeCallBack;

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    public RecipesViewModel(Application mApplication) {
        super(mApplication);

        UtilitiesJson.getClient().listRecipes().enqueue(new Callback<ArrayList<modelRecipe>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<modelRecipe>> call,
                                   @NonNull Response<ArrayList<modelRecipe>> response) {
                if (response.isSuccessful()) {
                    mRecipeMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<modelRecipe>> call,
                                  @NonNull Throwable t) {
                Log.i("loadRecipes", t.getLocalizedMessage());
                loadRecipeCallBack.onError(t);
            }
        });

    }

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    public void setLoadRecipeCallBack(LoadRecipeCallBack loadRecipeCallBack) {
        this.loadRecipeCallBack = loadRecipeCallBack;
    }

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    public MutableLiveData<ArrayList<modelRecipe>> getRecipeMutableLiveData() {
        return mRecipeMutableLiveData;
    }

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    public MutableLiveData<modelRecipe> getRecipe() {

        return mRecipe;

    }

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    public String getIngredientValue(){
        return String.format("%s" , getRecipe().getValue() != null ?
                getRecipe().getValue().getIngredients().size() : "");
    }

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    public String getStepsValue(){
        return String.format("%s" , getRecipe().getValue()!= null ?
                getRecipe().getValue().getSteps().size() : "");
    }

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    public String getServingValue(){
        return String.format("%s" , getRecipe().getValue() != null ? getRecipe().getValue().getServings() : "");
    }
}
