
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

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;

import com.timelesssoftware.bakingapp.Utils.Retrofit.Model.RecipeIngredientModel;
import com.timelesssoftware.bakingapp.Utils.Retrofit.Model.RecipeListModel;

import java.util.ArrayList;

/**
 * Project "Baking App" - Created by Nikos Vaggalis as part of the Udacity Android Developer Nanodegree on 13/6/2017.
 */

public class BakingWidgetService extends IntentService {

    public static String FROM_ACTIVITY_INGREDIENTS_LIST = "FROM_ACTIVITY_INGREDIENTS_LIST";

    public BakingWidgetService() {
        super("UpdateBakingService");
    }

    public static void startBakingService(Context context, RecipeListModel recipeListModel) {
        Intent intent = new Intent(context, BakingWidgetService.class);
        intent.putExtra(FROM_ACTIVITY_INGREDIENTS_LIST, recipeListModel);
        context.startService(intent);
    }
    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            RecipeListModel recipeListModel = intent.getExtras().getParcelable(FROM_ACTIVITY_INGREDIENTS_LIST);
            //handleActionUpdateBakingWidgets(fromActivityIngredientsList);
            Intent widgetIntent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            widgetIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            widgetIntent.putExtra(FROM_ACTIVITY_INGREDIENTS_LIST, recipeListModel);
            sendBroadcast(widgetIntent);
        }
    }

}
