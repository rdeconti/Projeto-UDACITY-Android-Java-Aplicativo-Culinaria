package com.rosemeire.deconti.bakehouse.userInterface;

/* ********************************************************************************************** */
/* UDACITY Android Developer NanoDegree Program
/* Created by Rosemeire Deconti on 30/10/2018
/* ********************************************************************************************** */

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.rosemeire.deconti.bakehouse.R;
import com.rosemeire.deconti.bakehouse.model.modelRecipe;
import com.rosemeire.deconti.bakehouse.service.task.SelectWidgetRecipeTask;

import static com.rosemeire.deconti.bakehouse.utilities.UtilitiesConstants.RECIPE_RECORD;

/* ************************************************************************************************/
/* *** Update widget data
/* ************************************************************************************************/
public class WidgetRecipes extends AppWidgetProvider {

    private static final int INTENT_REQUEST_CODE = 0;

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    public static void update(Context context) {

        Intent intent = new Intent(context, WidgetRecipes.class);

        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

        int[] ids = AppWidgetManager.getInstance(context).getAppWidgetIds(
                new ComponentName(context, WidgetRecipes.class));

        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);

        context.sendBroadcast(intent);
    }

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    @Override
    public void onUpdate(final Context context,
                         final AppWidgetManager appWidgetManager,
                         final int[] appWidgetIds) {

        new SelectWidgetRecipeTask(context, new SelectWidgetRecipeTask.SelectRecipeCallBack() {
            @Override
            public void onSelect(modelRecipe recipe) {
                for (int appWidgetId : appWidgetIds) {
                    updateAppWidget(context, appWidgetManager, appWidgetId, recipe);
                }

            }
        }).execute();
    }

    /* ****************************************************************************************** **
    /* ****************************************************************************************** */
    private void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                 int appWidgetId, modelRecipe recipe) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_recipes);

        views.setEmptyView(R.id.listView_widget, R.id.linearLayout_widget);

        views.setRemoteAdapter(R.id.listView_widget,
                new Intent(context, WidgetListFactoryService.class));

        if (recipe != null) {

            views.setTextViewText(R.id.textView_widgetTitle, context.getString(R.string.app_name) + " " + recipe.getName());

            Bundle extras = new Bundle();

            extras.putParcelable(RECIPE_RECORD, recipe);

            Intent appIntent = new Intent(context, RecipesActivity.class);

            appIntent.putExtras(extras);

            PendingIntent appPendingIntent = PendingIntent
                    .getActivity(context, INTENT_REQUEST_CODE, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            views.setOnClickPendingIntent(R.id.widget_recipe_layout, appPendingIntent);

        } else {

            Intent appIntent = new Intent(context, SplashActivity.class);

            PendingIntent appPendingIntent = PendingIntent
                    .getActivity(context, INTENT_REQUEST_CODE, appIntent, PendingIntent.FLAG_CANCEL_CURRENT);

            views.setOnClickPendingIntent(R.id.linearLayout_widget, appPendingIntent);
        }

        appWidgetManager.updateAppWidget(appWidgetId, views);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.listView_widget);
    }
}
