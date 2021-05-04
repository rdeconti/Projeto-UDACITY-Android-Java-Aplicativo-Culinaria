package com.rosemeire.deconti.bakehouse.userInterface;

/* ********************************************************************************************** */
/* UDACITY Android Developer NanoDegree Program
/* Created by Rosemeire Deconti on 30/10/2018
/* ********************************************************************************************** */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.ImageView;

import com.rosemeire.deconti.bakehouse.R;
import com.rosemeire.deconti.bakehouse.utilities.UtilitiesActivity;

import static com.rosemeire.deconti.bakehouse.utilities.UtilitiesConstants.LAST_SWEET_IMAGE_NUMBER;
import static com.rosemeire.deconti.bakehouse.utilities.UtilitiesConstants.SHARED_PREFERRED_LAST_SWEET_IMAGE_KEY;
import static com.rosemeire.deconti.bakehouse.utilities.UtilitiesConstants.SHARED_PREFERRED_LAST_SWEET_IMAGE_NUMBER;

/* ********************************************************************************************** **
/* **** Treat Application Presentation Screen
/* ********************************************************************************************** */
public class SplashActivity extends UtilitiesActivity {

    private ImageView m_ImageView;

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash_activity);

        SharedPreferences sharedPreferences = getSharedPreferences
                (SHARED_PREFERRED_LAST_SWEET_IMAGE_KEY, MODE_PRIVATE);

        LAST_SWEET_IMAGE_NUMBER = sharedPreferences.getInt("LastSweetImageNumber", 0);

        LAST_SWEET_IMAGE_NUMBER++;

        m_ImageView = findViewById(R.id.imageView_splash);

        switch (LAST_SWEET_IMAGE_NUMBER){

            case 1:
                m_ImageView.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.ic_bakehouse1));
                break;

            case 2:
                m_ImageView.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.ic_bakehouse2));
                break;

            case 3:
                m_ImageView.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.ic_bakehouse3));
                break;

            case 4:
                m_ImageView.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.ic_bakehouse4));
                break;

            case 5:
                m_ImageView.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.ic_bakehouse5));
                break;

            case 6:
                m_ImageView.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.ic_bakehouse6));
                break;

            case 7:
                m_ImageView.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.ic_bakehouse7));
                break;

            case 8:
                m_ImageView.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.ic_bakehouse8));
                break;

            case 9:
                m_ImageView.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.ic_bakehouse9));
                break;

            case 10:
                m_ImageView.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.ic_bakehouse10));
                break;

            default:
                m_ImageView.setImageDrawable(ContextCompat.getDrawable(this, R.mipmap.ic_bakehouse10));
                LAST_SWEET_IMAGE_NUMBER = 0;
                break;
        }

        SharedPreferences.Editor editor = getSharedPreferences
                (SHARED_PREFERRED_LAST_SWEET_IMAGE_KEY, MODE_PRIVATE).edit();
        editor.putInt("LastSweetImageNumber", LAST_SWEET_IMAGE_NUMBER);
        editor.apply();

        CountDownTimer mCountDownTimer = new CountDownTimer(2500, 200) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onFinish() {

                fadeOut();
                finish();

            }
        };

        mCountDownTimer.start();

    }

    /* ****************************************************************************************** **
    /* **** Treat transition from SPLASH to Application
    /* ****************************************************************************************** */
    private void fadeOut() {

        Intent mainIntent = new Intent(this, RecipesListActivity.class);

        startActivity(mainIntent);

        overridePendingTransition(R.anim.splash_in, R.anim.splash_out);

    }

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    @Override
    public void onBackPressed() {

    }

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    @Override
    protected void attachBaseContext(Context newBase) {

        super.attachBaseContext((newBase));

    }

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    @Override
    public void onSaveInstanceState(Bundle outState) {

        Log.w("*** SPLASH SCREEN ***", "onSaveInstanceState: " + LAST_SWEET_IMAGE_NUMBER);

        SHARED_PREFERRED_LAST_SWEET_IMAGE_NUMBER = LAST_SWEET_IMAGE_NUMBER;

        outState.putInt(SHARED_PREFERRED_LAST_SWEET_IMAGE_KEY, SHARED_PREFERRED_LAST_SWEET_IMAGE_NUMBER);

        Log.w("*** SPLASH SCREEN ***", "onSaveInstanceState: " + SHARED_PREFERRED_LAST_SWEET_IMAGE_NUMBER);

        super.onSaveInstanceState(outState);

    }
}
