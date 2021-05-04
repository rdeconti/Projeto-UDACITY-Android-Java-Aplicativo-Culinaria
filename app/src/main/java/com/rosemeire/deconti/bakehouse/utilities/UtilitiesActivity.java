package com.rosemeire.deconti.bakehouse.utilities;

/* ********************************************************************************************** */
/* UDACITY Android Developer NanoDegree Program
/* Created by Rosemeire Deconti on 30/10/2018
/* ********************************************************************************************** */

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;

public abstract class UtilitiesActivity extends AppCompatActivity {

    /* ****************************************************************************************** **
    /* **** Determine and monitor connectivity network
    /* ****************************************************************************************** */
    protected boolean isDeviceConnected() {

        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {

            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            return netInfo != null && netInfo.isConnectedOrConnecting();

        }

        return false;
    }
}