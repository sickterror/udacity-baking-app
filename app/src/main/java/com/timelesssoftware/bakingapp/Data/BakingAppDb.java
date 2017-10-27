package com.timelesssoftware.bakingapp.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Luka on 11.10.2017.
 */

public class BakingAppDb extends SQLiteOpenHelper {

    private static final String DB_NAME = "baking_app_db";
    private static final int DB_VERSION = 2;
    private final Context mContext;

    public BakingAppDb(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + RecipeContract.RecipeField.TABLE_RECIPE + "("
                + RecipeContract.RecipeField._ID + " INTEGER NOT NULL PRIMARY KEY,"
                + RecipeContract.RecipeField.id + " NUMBER NOT NULL,"
                + RecipeContract.RecipeField.name + " TEXT NOT NULL,"
                + RecipeContract.RecipeField.servings + " NUMBER NOT NULL,"
                + "UNIQUE (" + RecipeContract.RecipeField.id + ") ON CONFLICT REPLACE)");

        db.execSQL("CREATE TABLE " + RecipeContract.RecipeIngredientsField.TABLE_RECIPE_INGREDIENTS + "("
                + RecipeContract.RecipeIngredientsField._ID + " INTEGER NOT NULL PRIMARY KEY,"
                + RecipeContract.RecipeIngredientsField.id + " INTEGER NOT NULL,"
                + RecipeContract.RecipeIngredientsField.quantity + " DOUBLE NOT NULL,"
                + RecipeContract.RecipeIngredientsField.measure + " TEXT NOT NULL,"
                + RecipeContract.RecipeIngredientsField.ingredient + " TEXT NOT NULL)");

        db.execSQL("CREATE TABLE " + RecipeContract.RecipeStepsField.TABLE_RECIPE_STEPS + "("
                + RecipeContract.RecipeStepsField._ID + " INTEGER NOT NULL PRIMARY KEY,"
                + RecipeContract.RecipeStepsField.id + " INTEGER NOT NULL,"
                + RecipeContract.RecipeStepsField.step + " INTEGER NOT NULL,"
                + RecipeContract.RecipeStepsField.shortDescription + " TEXT NOT NULL,"
                + RecipeContract.RecipeStepsField.description + " TEXT NOT NULL,"
                + RecipeContract.RecipeStepsField.videoURL + " TEXT NOT NULL,"
                + RecipeContract.RecipeStepsField.thumbnailURL + " TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public static void deleteDatabase(Context context) {
        context.deleteDatabase(DB_NAME);
    }
}
