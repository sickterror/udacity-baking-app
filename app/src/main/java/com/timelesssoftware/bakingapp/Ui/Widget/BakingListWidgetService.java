
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

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.Toast;


import com.google.gson.Gson;
import com.timelesssoftware.bakingapp.BakingApp;
import com.timelesssoftware.bakingapp.MainActivity;
import com.timelesssoftware.bakingapp.R;
import com.timelesssoftware.bakingapp.Utils.Retrofit.Model.RecipeIngredientModel;
import com.timelesssoftware.bakingapp.Utils.Retrofit.Model.RecipeListModel;

import java.util.List;

import javax.inject.Inject;

import static com.timelesssoftware.bakingapp.Ui.Widget.BakingWidgetProvider.REMOTEVIEW_WIDGET_INGREDIENT_LIST;
import static com.timelesssoftware.bakingapp.Ui.Widget.BakingWidgetProvider.ingredientsList;
import static com.timelesssoftware.bakingapp.Ui.Widget.BakingWidgetProvider.recipeListModel;

/**
 * Project "Baking App" - Created by Nikos Vaggalis as part of the Udacity Android Developer Nanodegree on 13/6/2017.
 */

public class BakingListWidgetService extends RemoteViewsService {
    List<RecipeIngredientModel> remoteViewingredientsList;
    RecipeListModel recipeListModel;


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GridRemoteViewsFactory(this.getApplicationContext(), intent);
    }


    class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

        Context mContext = null;

        public GridRemoteViewsFactory(Context context, Intent intent) {
            mContext = context;
            String _stringRecipeListModel = intent.getExtras().getString(REMOTEVIEW_WIDGET_INGREDIENT_LIST);
            Gson gson = new Gson();
            recipeListModel = gson.fromJson(_stringRecipeListModel, RecipeListModel.class);
        }

        @Override
        public void onCreate() {
        }

        @Override
        public void onDataSetChanged() {
            remoteViewingredientsList = recipeListModel.ingredients;
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return remoteViewingredientsList.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {

            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.recipe_widget_ingredient);

            RecipeIngredientModel modelAtView = remoteViewingredientsList.get(position);
            views.setTextViewText(R.id.widget_recipe_title, modelAtView.ingredient);
            views.setTextViewText(R.id.widget_recipe_ingreident_messure, modelAtView.measure);
            views.setTextViewText(R.id.widget_recipe_ingreident_pcs, String.valueOf(modelAtView.quantity));
            Intent fillInIntent = new Intent();
            fillInIntent.putExtra(MainActivity.SELECTED_RECIPE_MODEL, recipeListModel);
            views.setOnClickFillInIntent(R.id.widget_recipe_list_item, fillInIntent);
            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }


}

