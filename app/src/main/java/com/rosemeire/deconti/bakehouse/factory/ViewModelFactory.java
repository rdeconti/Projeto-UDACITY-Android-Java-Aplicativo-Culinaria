package com.rosemeire.deconti.bakehouse.factory;

/* ********************************************************************************************** */
/* UDACITY Android Developer NanoDegree Program
/* Created by Rosemeire Deconti on 30/10/2018
/* ********************************************************************************************** */

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.rosemeire.deconti.bakehouse.R;
import com.rosemeire.deconti.bakehouse.database.BakeHouseDataBase;
import com.rosemeire.deconti.bakehouse.viewModel.IngredientsViewModel;
import com.rosemeire.deconti.bakehouse.viewModel.RecipesViewModel;
import com.rosemeire.deconti.bakehouse.viewModel.StepsViewModel;

/* ************************************************************************************************/
/* *** ViewModelProvider belongs to Maven artifact android.arch.lifecycle: View Model :1.1.1
/* ************************************************************************************************/
public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final BakeHouseDataBase mdb;
    private final Application mApplication;
    private static volatile ViewModelFactory INSTANCE;

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    private  ViewModelFactory(BakeHouseDataBase dataBase, Application application) {

        this.mdb = dataBase;
        this.mApplication = application;

    }

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    public static ViewModelFactory getInstance(Application application) {

        if (INSTANCE == null) {
            synchronized (ViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ViewModelFactory(BakeHouseDataBase.getInstance(application
                            .getApplicationContext()),
                            application);
                }
            }
        }
        return INSTANCE;
    }

    /* ****************************************************************************************** **
    /* **** Returns an existing ViewModel or creates a new one in the scope
    /* **** associated with this ViewModelProvider
    /* ****************************************************************************************** */
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if (modelClass.isAssignableFrom(RecipesViewModel.class)) {
            return (T) new RecipesViewModel(mApplication);

        } else if (modelClass.isAssignableFrom(StepsViewModel.class)) {
            return (T) new StepsViewModel(mApplication);

        } else if (modelClass.isAssignableFrom(IngredientsViewModel.class)) {
            return (T) new IngredientsViewModel(mApplication);

        }

        throw new IllegalArgumentException(mApplication.getString(R.string.msg_problems_viewModelClass) + modelClass.getName());

    }
}



