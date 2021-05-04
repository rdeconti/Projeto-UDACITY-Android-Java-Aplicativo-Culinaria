package com.rosemeire.deconti.bakehouse.userInterface;

/* ********************************************************************************************** */
/* UDACITY Android Developer NanoDegree Program
/* Created by Rosemeire Deconti on 30/10/2018
/* ********************************************************************************************** */

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.Snackbar;
import android.support.test.espresso.IdlingResource;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.rosemeire.deconti.bakehouse.R;
import com.rosemeire.deconti.bakehouse.adapter.RecipeRecyclerViewAdapter;
import com.rosemeire.deconti.bakehouse.databinding.RecipesListActivityBinding;
import com.rosemeire.deconti.bakehouse.factory.ViewModelFactory;
import com.rosemeire.deconti.bakehouse.model.modelRecipe;
import com.rosemeire.deconti.bakehouse.test.SimpleIdlingResource;
import com.rosemeire.deconti.bakehouse.utilities.UtilitiesActivity;
import com.rosemeire.deconti.bakehouse.viewModel.RecipesViewModel;

import java.util.ArrayList;

import static com.rosemeire.deconti.bakehouse.utilities.UtilitiesConstants.RECIPE_RECORD;
import static com.rosemeire.deconti.bakehouse.utilities.UtilitiesConstants.STATE_RECIPE;

/* TODO RUBRIC POINT: Check all application classes and set RUBRIC POINTS */
/*

/* ************************************************************************************************/
/* *** Treat recipe list
/* ************************************************************************************************/
public class RecipesListActivity extends UtilitiesActivity implements
          RecipeRecyclerViewAdapter.EventHandler
        , View.OnClickListener
        , RecipesViewModel.LoadRecipeCallBack {

    private RecipesListActivityBinding mMainBinding;
    private RecipeRecyclerViewAdapter adapter;
    private ArrayList<modelRecipe> mRecipes;
    private RecipesViewModel viewModel;
    private SimpleIdlingResource mIdlingResource;

    // TODO DELETE THIS BECAUSE IT IS NOT USED
    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    public static void startActivity(UtilitiesActivity activity) {
        activity.startActivity(

            new Intent(activity, RecipesListActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
    }

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMainBinding = DataBindingUtil.setContentView(this, R.layout.recipes_list_activity);

        setSupportActionBar(mMainBinding.toolbarMain);

        mIdlingResource = new SimpleIdlingResource();

        int spanCount = 1;

        if (getResources().getBoolean(R.bool.isRunningOnTablet)) {
            spanCount = 3;
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            spanCount = 2;
        }

        mMainBinding.recyclerViewMain.setLayoutManager(new GridLayoutManager(this, spanCount));

        mMainBinding.progressBarMain.setVisibility(View.GONE);

        mMainBinding.toolbarMain.setSubtitle(R.string.label_recipe_list);

        mMainBinding.toolbarMain.setSubtitleTextColor(getResources().getColor(R.color.titleColor));

        if (isDeviceConnected()) {

            setupViewModel();

            if (savedInstanceState != null && savedInstanceState.containsKey(STATE_RECIPE)) {
                mRecipes = savedInstanceState.getParcelableArrayList(STATE_RECIPE);
                viewModel.getRecipeMutableLiveData().setValue(mRecipes);
            }

        } else {

            showErrorNetworkConnection();

        }
    }

    /* ****************************************************************************************** **
    /* **** Obtain recipe list
    /* ****************************************************************************************** */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setupViewModel() {

        final ViewModelFactory factory = ViewModelFactory.getInstance(getApplication());
        viewModel = ViewModelProviders.of(this, factory).get(RecipesViewModel.class);
        viewModel.setLoadRecipeCallBack(this);

        mMainBinding.progressBarMain.setVisibility(View.VISIBLE);
        Drawable draw = getDrawable(R.drawable.custom_progress_bar);
        mMainBinding.progressBarMain.setProgressDrawable(draw);

        viewModel.getRecipeMutableLiveData().observe(this, new Observer<ArrayList<modelRecipe>>() {
            @Override
            public void onChanged(@Nullable ArrayList<modelRecipe> recipes) {

                if (recipes != null && !recipes.isEmpty()) {

                     mRecipes = recipes;
                    adapter = new RecipeRecyclerViewAdapter(getApplication(),RecipesListActivity.this);
                    adapter.setRecipes(recipes);
                    mMainBinding.recyclerViewMain.setAdapter(adapter);

                }

                mMainBinding.progressBarMain.setVisibility(View.GONE);

            }
        });
    }

    /* ****************************************************************************************** **
    /* **** Treat click on recipe to upload recipe data
    /* ****************************************************************************************** */
    @Override
    public void onItemClick(modelRecipe recipe) {

        Bundle extras = new Bundle();

        extras.putParcelable(RECIPE_RECORD, recipe);

        RecipesActivity.startActivity(this, extras);

    }

    /* ****************************************************************************************** **
    /* **** Save recipe list at onSaveInstanceState
    /* ****************************************************************************************** */
    @Override
    public void onSaveInstanceState(Bundle bundle) {

        bundle.putParcelableArrayList(STATE_RECIPE, mRecipes);

        super.onSaveInstanceState(bundle);
    }

    /* ****************************************************************************************** **
    /* **** Treat error about network connection fail
    /* ****************************************************************************************** */
    private void showErrorNetworkConnection() {

        mMainBinding.progressBarMain.setVisibility(View.GONE);

        mMainBinding.imageViewMain.setVisibility(View.VISIBLE);
        mMainBinding.imageViewMain.setImageDrawable(
                getResources().getDrawable(R.drawable.ic_bakehouse_wifi_off));

        Snackbar error = Snackbar.make(
                mMainBinding.coordinatorLayoutMain,
                R.string.msg_network_fail,
                Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.msg_connection_retry, this)
                .setActionTextColor(getResources().getColor(android.R.color.white));

        ((TextView) error.getView().findViewById(android.support.design.R.id.snackbar_text))
                .setTextColor(getResources().getColor(android.R.color.white));

        error.show();
    }

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {

        if (isDeviceConnected()) {

            setupViewModel();

            mMainBinding.imageViewMain.setVisibility(View.GONE);

        } else {

            showErrorNetworkConnection();
        }
    }

    // TODO CHECK IF THIS CLASS ARE BEEN USED AND FOR WHAT
    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    @Override
    public void onError(Throwable t) {

        mMainBinding.progressBarMain.setVisibility(View.GONE);

        mMainBinding.imageViewMain.setVisibility(View.VISIBLE);
        mMainBinding.imageViewMain.setImageDrawable(
                getResources().getDrawable(R.drawable.ic_bakehouse_icon));

        Snackbar error = Snackbar.make(
                mMainBinding.coordinatorLayoutMain,
                t.getMessage(),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.msg_connection_retry, this)
                .setActionTextColor(getResources().getColor(android.R.color.white));

        ((TextView) error.getView().findViewById(android.support.design.R.id.snackbar_text))
                .setTextColor(getResources().getColor(android.R.color.white));

        error.show();
    }

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {

        return mIdlingResource;

    }
}
