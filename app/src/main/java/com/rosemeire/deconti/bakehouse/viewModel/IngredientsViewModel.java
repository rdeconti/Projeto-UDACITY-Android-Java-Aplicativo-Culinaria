package com.rosemeire.deconti.bakehouse.viewModel;

/* ********************************************************************************************** */
/* UDACITY Android Developer NanoDegree Program
/* Created by Rosemeire Deconti on 30/10/2018
/* ********************************************************************************************** */

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.rosemeire.deconti.bakehouse.model.modelIngredient;

import java.util.List;

/* ************************************************************************************************/
/* *** View Model: INGREDIENTS
/* ************************************************************************************************/
public class IngredientsViewModel extends AndroidViewModel {

    private MutableLiveData<List<modelIngredient>> mIngredientsList = new MutableLiveData<>();
    private MutableLiveData<modelIngredient> mIngredients = new MutableLiveData<>();

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    public IngredientsViewModel(@NonNull Application application) {
        super(application);
    }

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    public MutableLiveData<modelIngredient> getIngredients() {
        return mIngredients;
    }

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    public MutableLiveData<List<modelIngredient>> getIngredientsList() {
        return mIngredientsList;
    }

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    public String getQuantityValue(){
        return mIngredients.getValue() != null ? mIngredients.getValue().getQuantity().toString() : "";
    }
}
