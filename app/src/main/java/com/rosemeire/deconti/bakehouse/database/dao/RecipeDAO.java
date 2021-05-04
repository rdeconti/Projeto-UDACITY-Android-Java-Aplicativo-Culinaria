package com.rosemeire.deconti.bakehouse.database.dao;

/* ********************************************************************************************** */
/* UDACITY Android Developer NanoDegree Program
/* Created by Rosemeire Deconti on 30/10/2018
/* ********************************************************************************************** */

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.rosemeire.deconti.bakehouse.model.modelRecipe;

/* TODO RUBRIC POINT: RECOMMENDATION FROM LAST PROJECT REVIEW: Use ROOM */

/* ************************************************************************************************/
/* *** Accessing data using Room (persistence data) DAO (data access object)- Recipes
/* ************************************************************************************************/
@Dao
public interface RecipeDAO {

    @Query("SELECT * FROM modelRecipe LIMIT 1")
    modelRecipe get();

    @Insert
    void insert(modelRecipe recipe);

    @Query("DELETE FROM modelRecipe")
    void clear();
}
