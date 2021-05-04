package com.rosemeire.deconti.bakehouse.database.dao;

/* ********************************************************************************************** */
/* UDACITY Android Developer NanoDegree Program
/* Created by Rosemeire Deconti on 30/10/2018
/* ********************************************************************************************** */

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.rosemeire.deconti.bakehouse.model.modelStep;

import java.util.List;

/* TODO RUBRIC POINT: RECOMMENDATION FROM LAST PROJECT REVIEW: Use ROOM */

/* ************************************************************************************************/
/* *** Accessing data using Room (persistence data) DAO (data access object)- Steps
/* ************************************************************************************************/
@Dao
public interface StepDAO {

    @Query("SELECT * FROM modelStep")
    List<modelStep> getAll();

    @Insert
    void insertAll(List<modelStep> steps);
}
