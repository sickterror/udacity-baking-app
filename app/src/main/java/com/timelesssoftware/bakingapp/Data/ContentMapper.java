package com.timelesssoftware.bakingapp.Data;

import android.content.ContentValues;
import android.database.Cursor;

import com.timelesssoftware.bakingapp.Utils.Retrofit.Model.RecipeIngredientModel;
import com.timelesssoftware.bakingapp.Utils.Retrofit.Model.RecipeListModel;
import com.timelesssoftware.bakingapp.Utils.Retrofit.Model.RecipeStepsModel;

/**
 * Created by Luka on 24. 10. 2017.
 */

public class ContentMapper {
    public static ContentValues mapRecipeModelToContentValues(RecipeListModel recipeListModel) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(RecipeContract.RecipeField.id, recipeListModel.id);
        contentValues.put(RecipeContract.RecipeField.name, recipeListModel.name);
        contentValues.put(RecipeContract.RecipeField.servings, recipeListModel.servings);
        return contentValues;
    }

    public static void mapContentValuesToRecipeModel(ContentValues contentValues) {
        /*ContentValues contentValues = new ContentValues();
        contentValues.put(RecipeContract.RecipeField.id, recipeListModel.id);
        contentValues.put(RecipeContract.RecipeField.name, recipeListModel.name);
        contentValues.put(RecipeContract.RecipeField.servings, recipeListModel.servings);
        return contentValues;*/
    }

    public static ContentValues mapRecipeIngrediaentModelToContentValues(RecipeIngredientModel recipeIngredientModel, int recipeId) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(RecipeContract.RecipeIngredientsField.id, recipeId);
        contentValues.put(RecipeContract.RecipeIngredientsField.quantity, recipeIngredientModel.quantity);
        contentValues.put(RecipeContract.RecipeIngredientsField.measure, recipeIngredientModel.measure);
        contentValues.put(RecipeContract.RecipeIngredientsField.ingredient, recipeIngredientModel.ingredient);
        return contentValues;
    }

    public static RecipeIngredientModel mapContentValueToRecipeIngredientModel(Cursor c) {
        RecipeIngredientModel recipeIngredientModel = new RecipeIngredientModel();
        recipeIngredientModel.measure = c.getString(c.getColumnIndex(RecipeContract.RecipeIngredientsField.measure));
        recipeIngredientModel.ingredient = c.getString(c.getColumnIndex(RecipeContract.RecipeIngredientsField.ingredient));
        recipeIngredientModel.quantity = c.getDouble(c.getColumnIndex(RecipeContract.RecipeIngredientsField.quantity));
        return recipeIngredientModel;
    }
}
