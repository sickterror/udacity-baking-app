
/*
 * Copyright 2017 Nikos Vaggalis
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.timelesssoftware.bakingapp.Ui.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;


import com.google.gson.Gson;
import com.timelesssoftware.bakingapp.R;
import com.timelesssoftware.bakingapp.Ui.Activity.RecipeStepStepsActivity;
import com.timelesssoftware.bakingapp.Utils.Retrofit.Model.RecipeIngredientModel;
import com.timelesssoftware.bakingapp.Utils.Retrofit.Model.RecipeListModel;

import java.util.ArrayList;

import static com.timelesssoftware.bakingapp.Ui.Widget.BakingWidgetService.FROM_ACTIVITY_INGREDIENTS_LIST;


/**
 * Project "Baking App" - Created by Nikos Vaggalis as part of the Udacity Android Developer Nanodegree on 13/6/2017.
 */

public class BakingWidgetProvider extends AppWidgetProvider {

    public static String REMOTEVIEW_WIDGET_INGREDIENT_LIST = "REMOTEVIEW_WIDGET_INGREDIENT_LIST";


    static ArrayList<RecipeIngredientModel> ingredientsList = new ArrayList<>();
    public static RecipeListModel recipeListModel;


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId, RecipeListModel ingredientsList) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        Intent appIntent = new Intent(context, RecipeStepStepsActivity.class);
        appIntent.addCategory(Intent.ACTION_MAIN);
        appIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        appIntent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.ingredient_widget_list_view, appPendingIntent);
        Gson gson = new Gson();
        Intent intent = new Intent(context, BakingListWidgetService.class);
        intent.putExtra(REMOTEVIEW_WIDGET_INGREDIENT_LIST, gson.toJson(ingredientsList));
        views.setRemoteAdapter(R.id.ingredient_widget_list_view, intent);
        views.setTextViewText(R.id.recipe_name, recipeListModel.name);
        appWidgetManager.updateAppWidget(appWidgetId, views);

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        //For some reason this is not called
        Log.d("widget", "update called");
    }

    public static void updateBakingWidgets(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds, RecipeListModel ingredientsList) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, ingredientsList);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, BakingWidgetProvider.class));
        final String action = intent.getAction();
        if (action.equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
            recipeListModel = intent.getExtras().getParcelable(FROM_ACTIVITY_INGREDIENTS_LIST);
            if (recipeListModel == null)
                return;
            ingredientsList = new ArrayList<>(recipeListModel.ingredients);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.ingredient_widget_list_view);
            BakingWidgetProvider.updateBakingWidgets(context, appWidgetManager, appWidgetIds, recipeListModel);
            super.onReceive(context, intent);
        }
    }

}

