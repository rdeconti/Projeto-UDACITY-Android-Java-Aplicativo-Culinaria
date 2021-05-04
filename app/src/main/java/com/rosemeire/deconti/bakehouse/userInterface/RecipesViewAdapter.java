package com.rosemeire.deconti.bakehouse.userInterface;

/* ********************************************************************************************** */
/* UDACITY Android Developer NanoDegree Program
/* Created by Rosemeire Deconti on 30/10/2018
/* ********************************************************************************************** */

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.rosemeire.deconti.bakehouse.R;
import com.rosemeire.deconti.bakehouse.model.modelRecipe;

import static com.rosemeire.deconti.bakehouse.utilities.UtilitiesConstants.INGREDIENTS_RECORD;

/* ************************************************************************************************/
/* *** Obtain recipe detail data
/* ************************************************************************************************/
public class RecipesViewAdapter extends FragmentPagerAdapter {

    private final String[] titlesTabs;
    private final modelRecipe mRecipe;

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    public RecipesViewAdapter(FragmentManager fragmentManager, Context context, modelRecipe recipe) {
        super(fragmentManager);

        titlesTabs = context.getResources().getStringArray(R.array.recipe_table_titles);

        mRecipe = recipe;
    }

    /* ****************************************************************************************** **
    /* **** Get recipe data depending on table item to be treated
    /* ****************************************************************************************** */
    @Override
    public Fragment getItem(int position) {

        switch (position) {

            case 0:
                Bundle ingredientsArguments = new Bundle();
                ingredientsArguments.putParcelableArrayList(
                        INGREDIENTS_RECORD, mRecipe.getIngredients());
                IngredientsListFragment ingredientsFragment =  new IngredientsListFragment();
                ingredientsFragment.setArguments(ingredientsArguments);
                return ingredientsFragment;

            case 1:
                Bundle stepArguments = new Bundle();
                stepArguments.putParcelableArrayList(
                        StepsListFragment.STEPS_RECORD, mRecipe.getSteps());
                StepsListFragment stepFragment =  new StepsListFragment();
                stepFragment.setArguments(stepArguments);
                return stepFragment;

            default:
                return new StepsListFragment();
        }
    }

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    @Override
    public int getCount() {

        return titlesTabs.length;

    }
    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        return titlesTabs[position];

    }
}
