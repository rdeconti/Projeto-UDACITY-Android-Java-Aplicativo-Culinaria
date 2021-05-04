package com.rosemeire.deconti.bakehouse.utilities;

/* ********************************************************************************************** */
/* UDACITY Android Developer NanoDegree Program
/* Created by Rosemeire Deconti on 30/10/2018
/* ********************************************************************************************** */

import com.rosemeire.deconti.bakehouse.userInterface.IngredientsListFragment;
import com.rosemeire.deconti.bakehouse.userInterface.RecipesActivity;
import com.rosemeire.deconti.bakehouse.userInterface.RecipesListActivity;
import com.rosemeire.deconti.bakehouse.userInterface.StepsDetailFragment;

public abstract class UtilitiesConstants {

    /* TODO RUBRIC POINT: Use recipes from a web service provided by Udacity */
    public static final String JSON_BASE_URL
            = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";

    public static final String STATE_RECIPE
            = RecipesListActivity.class.getName().concat(".STATE_RECIPE");

    public static final String RECIPE_RECORD
            =  RecipesActivity.class.getName().concat(".RECIPE_RECORD");

    public static final String STEP_SELECTED
            = StepsDetailFragment.class.getName().concat(".STEP_SELECTED");

    public static final String PLAYER_STATE
            = StepsDetailFragment.class.getName().concat(".PLAYER_STATE");

    public static final String INGREDIENTS_RECORD =
            IngredientsListFragment.class.getName().concat(".INGREDIENTS_RECORD");

    public static final String TAG_STEPS_LIST
            = "STEPS_LIST";

    public static final String SHARED_PREFERRED_LAST_SWEET_IMAGE_KEY = "LastSweetImageNumber";
    public static Integer SHARED_PREFERRED_LAST_SWEET_IMAGE_NUMBER = 0;
    public static Integer LAST_SWEET_IMAGE_NUMBER;

    public static String RECIPE_NAME;

    // SQLite definitions
    public static final String DATABASE_NAME = "bakehouse.db";
    public static final int DATABASE_VERSION = 1;
}
