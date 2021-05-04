package com.rosemeire.deconti.bakehouse.test;

/* ********************************************************************************************** */
/* UDACITY Android Developer NanoDegree Program
/* Created by Rosemeire Deconti on 30/10/2018
/* ********************************************************************************************** */

import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.test.espresso.IdlingResource;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

/* TODO RUBRIC POINT: Application uses "Espresso" to test UI */
/* ************************************************************************************************/
/* *** An idling resource represents an asynchronous operation whose results affect subsequent
/* *** operations in a UI test. By registering idling resources with Espresso, you can validate
/* *** these asynchronous operations more reliably when testing your app
/* ************************************************************************************************/
public class SimpleIdlingResource implements IdlingResource {

    private final AtomicBoolean mIsIdleNow = new AtomicBoolean(true);

    @Nullable
    private volatile ResourceCallback mCallback;

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    @Override
    public String getName() {

        return this.getClass().getName();

    }

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    @Override
    public boolean isIdleNow() {

        return mIsIdleNow.get();

    }

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {

        mCallback = callback;

    }

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void setIdleState(boolean isIdleNow) {

        mIsIdleNow.set(isIdleNow);

        if (isIdleNow && mCallback != null) {
            Objects.requireNonNull(mCallback).onTransitionToIdle();
        }

    }
}