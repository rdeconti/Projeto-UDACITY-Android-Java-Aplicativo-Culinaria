package com.rosemeire.deconti.bakehouse.utilities;

/* ********************************************************************************************** */
/* UDACITY Android Developer NanoDegree Program
/* Created by Rosemeire Deconti on 30/10/2018
/* ********************************************************************************************** */

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

import static com.rosemeire.deconti.bakehouse.utilities.UtilitiesConstants.TAG_STEPS_LIST;

/* ************************************************************************************************/
/* *** Floating action button routines
/* ************************************************************************************************/
public class UtilitiesFloatingActionButton extends FloatingActionButton.Behavior {

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    public UtilitiesFloatingActionButton(Context context, AttributeSet attrs) {
         super();
    }

    /* ****************************************************************************************** **
    /* **** Hide the view off the bottom of the screen when scrolling down,
    /* **** and show it when scrolling up.
    /* ****************************************************************************************** */
    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout,
                                       @NonNull FloatingActionButton child,
                                       @NonNull View directTargetChild,
                                       @NonNull View target, int axes, int type) {

        return axes == ViewCompat.SCROLL_AXIS_VERTICAL ||
                super.onStartNestedScroll(coordinatorLayout, child, directTargetChild,
                        target, axes, type);
    }

    /* ****************************************************************************************** **
    /* **** Called when a nested scroll in progress has updated and the target has scrolled
    /* **** or attempted to scroll.
    /* ****************************************************************************************** */
    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout,
                               @NonNull FloatingActionButton child,
                               @NonNull View target, int dxConsumed, int dyConsumed,
                               int dxUnconsumed, int dyUnconsumed, int type) {

        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed,
                dyConsumed, dxUnconsumed, dyUnconsumed, type);

        if (target.getTag() != null && target.getTag().equals(TAG_STEPS_LIST)) {
            if (dyConsumed > 0 && child.getVisibility() == View.VISIBLE) {
                child.hide(new FloatingActionButton.OnVisibilityChangedListener() {
                    @SuppressLint("RestrictedApi")
                    @Override
                    public void onHidden(FloatingActionButton fab) {
                        super.onHidden(fab);
                        fab.setVisibility(View.INVISIBLE);
                    }
                });
            } else if (dyConsumed < 0 && child.getVisibility() != View.VISIBLE) {
                child.show();
            }
        } else if (child.getVisibility() != View.VISIBLE) {
            child.show();
        }
    }
 }