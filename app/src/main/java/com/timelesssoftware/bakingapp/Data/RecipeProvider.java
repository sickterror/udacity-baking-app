package com.timelesssoftware.bakingapp.Data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.timelesssoftware.bakingapp.Data.RecipeContract.CONTENT_AUTHORITY;


/**
 * Created by Luka on 11.10.2017.
 */

public class RecipeProvider extends ContentProvider {
    private static final int RECIPE = 100;
    private static final int RECIPE_WITH_ID = 200;
    private static final int RECIPE_INGREDIENTS = 300;
    private static final int RECIPE_INGREDIENTS_ID = 400;
    private BakingAppDb mDatabase;


    @Override
    public boolean onCreate() {
        mDatabase = new BakingAppDb(getContext());
        return true;
    }


    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = CONTENT_AUTHORITY;
        matcher.addURI(authority, RecipeContract.RecipeField.TABLE_RECIPE, RECIPE);
        matcher.addURI(authority, RecipeContract.RecipeField.TABLE_RECIPE + "/#", RECIPE_WITH_ID);
        matcher.addURI(authority, RecipeContract.RecipeIngredientsField.TABLE_RECIPE_INGREDIENTS, RECIPE_INGREDIENTS);
        matcher.addURI(authority, RecipeContract.RecipeIngredientsField.TABLE_RECIPE_INGREDIENTS + "/#", RECIPE_INGREDIENTS_ID);
        return matcher;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        final SQLiteDatabase db = mDatabase.getReadableDatabase();
        switch (buildUriMatcher().match(uri)) {
            case RECIPE:
                Cursor cursor = db.query(
                        RecipeContract.RecipeField.TABLE_RECIPE,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                Context context = getContext();
                if (null != context) {
                    cursor.setNotificationUri(context.getContentResolver(), uri);
                }
                return cursor;
            case RECIPE_WITH_ID:
                Cursor curosrWithQ = db.query(
                        RecipeContract.RecipeField.TABLE_RECIPE,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                if (null != getContext()) {
                    curosrWithQ.setNotificationUri(getContext().getContentResolver(), uri);
                }
                return curosrWithQ;
            case RECIPE_INGREDIENTS:
                Cursor curosrRecipeI = db.query(
                        RecipeContract.RecipeIngredientsField.TABLE_RECIPE_INGREDIENTS,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                if (null != getContext()) {
                    curosrRecipeI.setNotificationUri(getContext().getContentResolver(), uri);
                }
                return curosrRecipeI;
        }
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        SQLiteDatabase db = mDatabase.getWritableDatabase();
        switch (buildUriMatcher().match(uri)) {
            case RECIPE:
                db.insert(RecipeContract.RecipeField.TABLE_RECIPE, null, contentValues);
                return RecipeContract.RecipeField.RECIPE_CONTENT_URI.buildUpon().appendPath(contentValues.getAsString(RecipeContract.RecipeField.id)).build();
            case RECIPE_INGREDIENTS:
                db.insert(RecipeContract.RecipeIngredientsField.TABLE_RECIPE_INGREDIENTS, null, contentValues);
                return RecipeContract.RecipeIngredientsField.RECIPE_CONTENT_URI.buildUpon().appendPath(contentValues.getAsString(RecipeContract.RecipeIngredientsField.id)).build();
        }
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int numDeleted = 0;
        final SQLiteDatabase db = mDatabase.getReadableDatabase();
        switch (buildUriMatcher().match(uri)) {
            case RECIPE:
                numDeleted = db.delete(RecipeContract.RecipeField.TABLE_RECIPE, selection, selectionArgs);
                break;
            case RECIPE_WITH_ID:
                numDeleted = db.delete(RecipeContract.RecipeField.TABLE_RECIPE,
                        selection,
                        selectionArgs);
                break;
        }

        return numDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
