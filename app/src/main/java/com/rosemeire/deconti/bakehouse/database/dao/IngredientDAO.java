package com.rosemeire.deconti.bakehouse.database.dao;

/* ********************************************************************************************** */
/* UDACITY Android Developer NanoDegree Program
/* Created by Rosemeire Deconti on 30/10/2018
/* ********************************************************************************************** */

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.rosemeire.deconti.bakehouse.model.modelIngredient;

import java.util.List;

/* TODO RUBRIC POINT: RECOMMENDATION FROM LAST PROJECT REVIEW: Use ROOM */

/* ************************************************************************************************/
/* *** Accessing data using Room (persistence data) DAO (data access object)- Ingredients
/* ************************************************************************************************/
@Dao
public interface IngredientDAO {

    @Query("SELECT * FROM modelIngredient")
    List<modelIngredient> getAll();

    @Insert
    void insertAll(List<modelIngredient> ingredients);
}
