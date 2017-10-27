package com.timelesssoftware.bakingapp.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;


import com.timelesssoftware.bakingapp.Dagger.ApplicationContext;
import com.timelesssoftware.bakingapp.Utils.Retrofit.Model.RecipeIngredientModel;
import com.timelesssoftware.bakingapp.Utils.Retrofit.Model.RecipeListModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luka on 11. 10. 2017.
 */

public class RecipeDbHelper {
    private static final String TAG = "RecipeDbHelper";
    private Context context;

    public RecipeDbHelper(@ApplicationContext Context context) {
        this.context = context;
    }

    public List<RecipeListModel> getRecipeList() {
        List<RecipeListModel> recipeListModelLiset = new ArrayList<>();
        Cursor cursor = this.context.getContentResolver().query(RecipeContract.RecipeField.RECIPE_CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                //Log.d(TAG, cursor.getString(cursor.getColumnIndex(RecipeContract.RecipeField.name)));
            }
        }
        return recipeListModelLiset;
    }

    public void insertRecipeIngredient(RecipeIngredientModel recipeIngredientModel, int id) {
        ContentValues contentValues = ContentMapper.mapRecipeIngrediaentModelToContentValues(recipeIngredientModel, id);
        this.context.getContentResolver().insert(RecipeContract.RecipeIngredientsField.RECIPE_CONTENT_URI, contentValues);
    }

    public List<RecipeIngredientModel> getRecipeIngredient() {
        List<RecipeIngredientModel> recipeIngredientModelList = new ArrayList<>();
        Cursor cursor = this.context.getContentResolver().query(RecipeContract.RecipeIngredientsField.RECIPE_CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Log.d(TAG, cursor.getString(0));
                Log.d(TAG, cursor.getString(1));
                Log.d(TAG, cursor.getString(2));
                recipeIngredientModelList.add(ContentMapper.mapContentValueToRecipeIngredientModel(cursor));
            }
        }
        return recipeIngredientModelList;
    }

    public void clearDb() {
        int count1 = this.context.getContentResolver().delete(RecipeContract.RecipeField.RECIPE_CONTENT_URI, null, null);
        int count2 = this.context.getContentResolver().delete(RecipeContract.RecipeIngredientsField.RECIPE_CONTENT_URI, null, null);
        Log.d(TAG, "count1 " + count1 + ", " + "count2 " + count2);
    }

/*    public List<MovieModel> getFavoritedMoves() {
        List<MovieModel> list = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(RecipeContract.RecipeField.RECIPE_CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                MovieModel movieModel = ContentValuesMapper.mapCursorToMovieModel(cursor);
                list.add(movieModel);
            }
            cursor.close();
        }

        return list;
    }

    public MovieModel isMovieFavorited(String movieId) {
        Uri uri = RecipeContract.RecipeField.RECIPE_CONTENT_URI.buildUpon().appendPath(movieId).build();
        Cursor ca = context.getContentResolver().query(uri, null, RecipeContract.RecipeField.id + " = ?", new String[]{movieId}, null);
        if (ca != null) {
            while (ca.moveToNext()) {
                MovieModel model = ContentValuesMapper.mapCursorToMovieModel(ca);
                return model;
            }
        }
        return null;
    }

    public void markMovieAsFavorited(MovieModel movieModel) {
        ContentValues contentValues = ContentValuesMapper.mapMovieModelToContentValues(movieModel);
        context.getContentResolver().insert(RecipeContract.RecipeField.RECIPE_CONTENT_URI,
                contentValues);
    }

    public void removeMovieModel(String movieId) {
        Uri uri = RecipeContract.RecipeField.RECIPE_CONTENT_URI.buildUpon().appendPath(movieId).build();
        context.getContentResolver().delete(uri, RecipeContract.RecipeField.id + " = ?", new String[]{movieId});
    }*/

}
