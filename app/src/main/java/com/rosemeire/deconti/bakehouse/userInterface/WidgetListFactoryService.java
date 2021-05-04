package com.rosemeire.deconti.bakehouse.userInterface;

/* ********************************************************************************************** */
/* UDACITY Android Developer NanoDegree Program
/* Created by Rosemeire Deconti on 30/10/2018
/* ********************************************************************************************** */

import android.content.Intent;
import android.widget.RemoteViewsService;

/* ************************************************************************************************/
/* *** Widget service
/* ************************************************************************************************/
public class WidgetListFactoryService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetRecipesIngredients(this.getApplicationContext());
    }
}
