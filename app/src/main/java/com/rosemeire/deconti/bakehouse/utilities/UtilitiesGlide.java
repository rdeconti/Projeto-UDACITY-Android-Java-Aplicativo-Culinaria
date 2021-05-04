package com.rosemeire.deconti.bakehouse.utilities;

/* ********************************************************************************************** */
/* UDACITY Android Developer NanoDegree Program
/* Created by Rosemeire Deconti on 30/10/2018
/* ********************************************************************************************** */

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/* TODO RUBRIC POINT: Use third-party library: GLIDE */

/* ************************************************************************************************/
/* ***  Image Loader Library for Android developed
/* ************************************************************************************************/
public class UtilitiesGlide {
    public static void loadImage(Context context, String url, final ImageView imageView) {

        Glide.with(context)
                .load(url)
                .into(imageView);

    }
}
