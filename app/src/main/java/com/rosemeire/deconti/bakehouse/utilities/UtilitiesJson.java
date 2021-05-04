package com.rosemeire.deconti.bakehouse.utilities;

/* ********************************************************************************************** */
/* UDACITY Android Developer NanoDegree Program
/* Created by Rosemeire Deconti on 30/10/2018
/* ********************************************************************************************** */

import com.rosemeire.deconti.bakehouse.model.modelRecipe;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

import static com.rosemeire.deconti.bakehouse.utilities.UtilitiesConstants.JSON_BASE_URL;

/* TODO RUBRIC POINT: Use third-party library: RETROFIT */

/* ************************************************************************************************/
/* *** Retrofit turns your HTTP API into a Java interface
/* ************************************************************************************************/
public class UtilitiesJson {

    private static final OkHttpClient HTTP_CLIENT;

    static {
        HTTP_CLIENT = new OkHttpClient.Builder().build();
    }

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    public interface BakingServiceClient {

        @GET("baking.json")
        retrofit2.Call<ArrayList<modelRecipe>> listRecipes();
    }

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    private UtilitiesJson(){

    }

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    public static BakingServiceClient getClient() {

        return new Retrofit.Builder()
                .baseUrl(JSON_BASE_URL)
                .client(HTTP_CLIENT)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(BakingServiceClient.class);
    }
}
