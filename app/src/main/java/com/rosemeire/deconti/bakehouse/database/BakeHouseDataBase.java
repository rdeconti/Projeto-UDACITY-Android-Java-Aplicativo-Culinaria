package com.rosemeire.deconti.bakehouse.database;

/* ********************************************************************************************** */
/* UDACITY Android Developer NanoDegree Program
/* Created by Rosemeire Deconti on 30/10/2018
/* ********************************************************************************************** */

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.rosemeire.deconti.bakehouse.database.dao.IngredientDAO;
import com.rosemeire.deconti.bakehouse.database.dao.RecipeDAO;
import com.rosemeire.deconti.bakehouse.database.dao.StepDAO;
import com.rosemeire.deconti.bakehouse.model.modelIngredient;
import com.rosemeire.deconti.bakehouse.model.modelRecipe;
import com.rosemeire.deconti.bakehouse.model.modelStep;

import static com.rosemeire.deconti.bakehouse.utilities.UtilitiesConstants.DATABASE_NAME;
import static com.rosemeire.deconti.bakehouse.utilities.UtilitiesConstants.DATABASE_VERSION;

/* ************************************************************************************************/
/* *** Accessing data using Room (persistence data) DAO (data access object)
/* ************************************************************************************************/
@Database(entities = {modelRecipe.class, modelIngredient.class, modelStep.class},
        version = DATABASE_VERSION, exportSchema = false)

public abstract class BakeHouseDataBase extends RoomDatabase {

    private static BakeHouseDataBase INSTANCE;

    public abstract RecipeDAO recipeDAO();
    public abstract IngredientDAO ingredientDAO();
    public abstract StepDAO stepDAO();

    public static BakeHouseDataBase getInstance(Context context) {

        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, BakeHouseDataBase.class, DATABASE_NAME).build();
        }

        return INSTANCE;
    }
}
