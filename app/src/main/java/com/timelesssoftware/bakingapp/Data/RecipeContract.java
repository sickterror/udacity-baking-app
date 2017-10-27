package com.timelesssoftware.bakingapp.Data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Luka on 11.10.2017.
 */

public class RecipeContract {

    public static final String CONTENT_AUTHORITY = "com.timelesssoftware.bakingapp";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);


    public static final class RecipeField implements BaseColumns {
        // table name
        public static final String TABLE_RECIPE = "table_recipe";
        // columns

        public static final String id = "id";
        public static final String name = "name";
        public static final String servings = "servings";


        // create content uri
        public static final Uri RECIPE_CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(TABLE_RECIPE).build();
        // create cursor of base type directory for multiple entries
        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_RECIPE;
        // create cursor of base type item for single entry
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_RECIPE;

        // for building URIs on insertion
        public static Uri buildFlavorsUri(long id) {
            return ContentUris.withAppendedId(RECIPE_CONTENT_URI, id);
        }
    }

    public static final class RecipeIngredientsField implements BaseColumns {
        // table name
        public static final String TABLE_RECIPE_INGREDIENTS = "table_recipe_ingredients";
        // columns

        public static final String id = "id";
        public static final String quantity = "quantity";
        public static final String measure = "measure";
        public static final String ingredient = "ingredient";

        // create content uri
        public static final Uri RECIPE_CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(TABLE_RECIPE_INGREDIENTS).build();
        // create cursor of base type directory for multiple entries
        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_RECIPE_INGREDIENTS;
        // create cursor of base type item for single entry
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_RECIPE_INGREDIENTS;

        // for building URIs on insertion
        public static Uri buildFlavorsUri(long id) {
            return ContentUris.withAppendedId(RECIPE_CONTENT_URI, id);
        }
    }

    public static final class RecipeStepsField implements BaseColumns {
        // table name
        public static final String TABLE_RECIPE_STEPS = "table_recipe_steps";
        // columns

        public static final String id = "id";
        public static String step = "step";
        public static final String shortDescription = "shortDescription";
        public static final String description = "description";
        public static final String videoURL = "videoURL";
        public static final String thumbnailURL = "thumbnailURL";

        // create content uri
        public static final Uri RECIPE_CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(TABLE_RECIPE_STEPS).build();
        // create cursor of base type directory for multiple entries
        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_RECIPE_STEPS;
        // create cursor of base type item for single entry
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + TABLE_RECIPE_STEPS;


        // for building URIs on insertion
        public static Uri buildFlavorsUri(long id) {
            return ContentUris.withAppendedId(RECIPE_CONTENT_URI, id);
        }
    }
}
