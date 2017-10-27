package com.timelesssoftware.bakingapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.timelesssoftware.bakingapp.Data.ContentMapper;
import com.timelesssoftware.bakingapp.Data.RecipeContract;
import com.timelesssoftware.bakingapp.Data.RecipeDbHelper;
import com.timelesssoftware.bakingapp.Ui.Activity.RecipeStepStepsActivity;
import com.timelesssoftware.bakingapp.Ui.Adapter.RecipeListAdapter;
import com.timelesssoftware.bakingapp.Ui.Fragment.Recipe;
import com.timelesssoftware.bakingapp.Ui.Fragment.RecipeSteps;
import com.timelesssoftware.bakingapp.Utils.Retrofit.IBakingApi;
import com.timelesssoftware.bakingapp.Utils.Retrofit.Model.RecipeIngredientModel;
import com.timelesssoftware.bakingapp.Utils.Retrofit.Model.RecipeListModel;
import com.timelesssoftware.bakingapp.Utils.Retrofit.Model.RecipeStepsModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecipeListAdapter.OnRecipeSelectListener {

    private static final String RECEPIE_STEP_FRAGMENT = "recipe_step_fragemnt";
    private static final String RECEPIE_FRAGMENT = "recipe_fragment";

    public static final String SELECTED_RECIPE_MODEL = "selected_recipe_model";
    public static final String SELECTED_RECIPE_LIST = "selected_recipe_list";
    private boolean mTwoPane;

    @Inject
    IBakingApi iBakingApi;

    @Inject
    RecipeDbHelper recipeDbHelper;
    private RecipeSteps recipeSteps;
    private Recipe recipe;

    private List<RecipeListModel> recipeListModels = new ArrayList<>();
    private RecyclerView recipeListRv;
    private RecipeListAdapter recipeListAdapter;
    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BakingApp.getNetComponent().inject(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recipeListRv = findViewById(R.id.recipe_list_rv);
        recipeListAdapter = new RecipeListAdapter(recipeListModels, this);
        if (getResources().getBoolean(R.bool.isTablet)) {
            recipeListRv.setLayoutManager(new GridLayoutManager(this, 3));
        } else {
            recipeListRv.setLayoutManager(new GridLayoutManager(this, 2));
        }
        recipeListRv.setAdapter(recipeListAdapter);
        Log.i("data", getResources().getBoolean(R.bool.isTablet) + "");
        getBakingData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void getBakingData() {
        iBakingApi.get().enqueue(new Callback<List<RecipeListModel>>() {
            @Override
            public void onResponse(Call<List<RecipeListModel>> call, Response<List<RecipeListModel>> response) {
                if (response != null) {
                    int count = 0;
                    for (RecipeListModel recipeListModel : response.body()) {
                        recipeListModels.add(recipeListModel);
                        recipeListAdapter.notifyItemInserted(count);
                        saveRecipe(recipeListModel);
                        for (RecipeIngredientModel recipeIngredientModel : recipeListModel.ingredients) {
                            recipeDbHelper.insertRecipeIngredient(recipeIngredientModel, recipeListModel.id);
                        }
                        count++;
                    }
                }

                checkData();
            }

            @Override
            public void onFailure(Call<List<RecipeListModel>> call, Throwable t) {
                Log.w("recipeListW", t);
                Snackbar.make(recipeListRv, R.string.main_activty_loading_error, Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void checkData() {
        for (RecipeListModel rec : recipeDbHelper.getRecipeList()) {
            Log.d(TAG, rec.name);
        }

        for (RecipeIngredientModel recI : recipeDbHelper.getRecipeIngredient()) {
            Log.d(TAG, recI.ingredient);
        }
    }


    private void saveRecipe(RecipeListModel recipeListModel) {
        ContentValues contentValues = ContentMapper.mapRecipeModelToContentValues(recipeListModel);
        getContentResolver().insert(RecipeContract.RecipeField.RECIPE_CONTENT_URI, contentValues);
    }

    @Override
    public void onRecipeSelect(RecipeListModel recipeListModel) {
        Intent intent = new Intent(this, RecipeStepStepsActivity.class);
        intent.putExtra(SELECTED_RECIPE_MODEL, recipeListModel);
        intent.putParcelableArrayListExtra(SELECTED_RECIPE_LIST, new ArrayList<Parcelable>(recipeListModel.steps));
        startActivity(intent);
    }

    public void initRecylcerView(List<RecipeListModel> listModels) {
        int count = 0;
        for (RecipeListModel recipeListModel : listModels) {
            recipeListModels.add(recipeListModel);
            recipeListAdapter.notifyItemInserted(count);
            //saveRecipe(recipeListModel);
            /*for (RecipeIngredientModel recipeIngredientModel : recipeListModel.ingredients) {
                recipeDbHelper.insertRecipeIngredient(recipeIngredientModel, recipeListModel.id);
            }*/
            count++;
        }
    }
}
