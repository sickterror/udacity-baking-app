package com.timelesssoftware.bakingapp.Ui.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.BounceInterpolator;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ActionViewTarget;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.timelesssoftware.bakingapp.BakingApp;
import com.timelesssoftware.bakingapp.MainActivity;
import com.timelesssoftware.bakingapp.R;
import com.timelesssoftware.bakingapp.Ui.Adapter.RecipeIngreidentAdapter;
import com.timelesssoftware.bakingapp.Ui.Fragment.Recipe;
import com.timelesssoftware.bakingapp.Ui.Fragment.RecipeSteps;
import com.timelesssoftware.bakingapp.Ui.Widget.BakingWidgetService;
import com.timelesssoftware.bakingapp.Utils.Helpers.GeneralHelpers;
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

public class RecipeStepStepsActivity extends AppCompatActivity implements RecipeSteps.OnRecipeStepSelectListener, View.OnClickListener {

    private static final String RECEPIE_STEP_FRAGMENT = "recipe_step_fragemnt";
    private static final String RECEPIE_FRAGMENT = "recipe_fragment";
    public static final String SELECTED_RECIPE_STEPS = "selected_recipe_step";
    public static final String NEXT_STEPS = "next_step";
    private static final String SHOWED_SHOWCASE = "showed_showcase";
    public static final String RECIPE_TITLE = "recipe_title";
    private boolean mTwoPane;

    @Inject
    IBakingApi iBakingApi;

    @Inject
    SharedPreferences sharedPreferences;

    private RecipeSteps recipeSteps;
    private Recipe recipe;

    private RecipeListModel recipeListModel;

    private View mAddToWidghetBtn;
    private BottomSheetBehavior<View> bottomSheetBehavior;
    private View bottomSheetLayout;
    private RecyclerView ingredientRv;
    private List<RecipeIngredientModel> recipeIngredientModels;
    private RecipeIngreidentAdapter recipeIngreidentAdapter;
    private Bundle mSavedInstance;
    private View mCloseIngredientBottomSheet;


    private View mBottomSheetToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSavedInstance = savedInstanceState;
        setContentView(R.layout.tablet_view);
        BakingApp.getNetComponent().inject(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recipeListModel = getIntent().getParcelableExtra(MainActivity.SELECTED_RECIPE_MODEL);
        bottomSheetLayout = findViewById(R.id.linear_layout_bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout);
        mBottomSheetToolbar = findViewById(R.id.bottom_sheet_toolbar);
        mBottomSheetToolbar.setOnClickListener(this);
        mCloseIngredientBottomSheet = findViewById(R.id.close_bottom_sheet);
        mCloseIngredientBottomSheet.setOnClickListener(this);
        mTwoPane = getResources().getBoolean(R.bool.isTablet);
        if (mTwoPane) {
            initTwoPainView();
        } else {
            initMobileView();
        }
        // mAddToWidghetBtn.setOnClickListener(this);
        ingredientRv = findViewById(R.id.ingredient_rv);
        recipeIngredientModels = recipeListModel.ingredients;
        recipeIngreidentAdapter = new RecipeIngreidentAdapter(recipeIngredientModels);
        ingredientRv.setLayoutManager(new LinearLayoutManager(this));
        ingredientRv.setAdapter(recipeIngreidentAdapter);
        recipeIngreidentAdapter.notifyDataSetChanged();
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/

    private void initTwoPainView() {
        mAddToWidghetBtn = findViewById(R.id.add_recipe_to_widget_tablet);
        mAddToWidghetBtn.setOnClickListener(this);
        addRecipeFragment(R.id.recipe_container, "");
        addRecipeStepsFragment(R.id.recipe_list_container);
    }

    private void initMobileView() {
        mAddToWidghetBtn = findViewById(R.id.add_recipe_to_widget);
        mAddToWidghetBtn.setOnClickListener(this);
        addRecipeStepsFragment(R.id.single_view_holder);
        initShowcaseView();
    }

    private void addRecipeStepsFragment(int id) {
        if (mSavedInstance == null) {
            recipeSteps = RecipeSteps.newInstance(recipeListModel.steps);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(id, recipeSteps, RECEPIE_STEP_FRAGMENT).commit();
        } else {
            getSupportFragmentManager().findFragmentByTag(RECEPIE_STEP_FRAGMENT);
        }
    }

    private void addRecipeFragment(int id, String string) {
        if (mSavedInstance == null) {
            recipe = Recipe.newInstance(recipeListModel.steps.get(0));
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(id, recipe, RECEPIE_FRAGMENT).commit();
        } else {
            getSupportFragmentManager().findFragmentByTag(RECEPIE_FRAGMENT);
        }
    }

    @Override
    public void onFragmentInteraction(RecipeStepsModel recipeSteps) {
        if (mTwoPane) {
            Recipe recipe = Recipe.newInstance(recipeSteps);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.recipe_container, recipe, RECEPIE_FRAGMENT).commit();
        } else {
            Intent intent = new Intent(this, RecipeActivity.class);
            intent.putExtra(SELECTED_RECIPE_STEPS, recipeSteps);
            intent.putParcelableArrayListExtra(NEXT_STEPS, new ArrayList<>(recipeListModel.steps));
            intent.putExtra(RECIPE_TITLE, recipeListModel.getName());
            startActivity(intent);
        }
    }

    private void getBakingData() {
        iBakingApi.get().enqueue(new Callback<List<RecipeListModel>>() {
            @Override
            public void onResponse(Call<List<RecipeListModel>> call, Response<List<RecipeListModel>> response) {
                if (response != null) {
                    for (RecipeListModel recipeListModel : response.body()) {
                        Log.d("recipeList", recipeListModel.name);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<RecipeListModel>> call, Throwable t) {
                Log.w("recipeListW", t);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.add_recipe_to_widget || v.getId() == R.id.add_recipe_to_widget_tablet) {
            BakingWidgetService.startBakingService(this, recipeListModel);
            Snackbar.make(findViewById(R.id.coordinator), R.string.add_widget_snackbar, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        if (v.getId() == R.id.bottom_sheet_toolbar) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }

        if (v.getId() == R.id.close_bottom_sheet) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }

    private BottomSheetBehavior.BottomSheetCallback bottomSheetMobileCallback = new BottomSheetBehavior.BottomSheetCallback() {
        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                float margin = GeneralHelpers.dpToPx(16, RecipeStepStepsActivity.this.getApplicationContext());
                mCloseIngredientBottomSheet.animate().setInterpolator(new BounceInterpolator()).setDuration(500).alpha(1).translationX(-margin);
            } else {
                mCloseIngredientBottomSheet.animate().alpha(0).translationX(0);
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            mAddToWidghetBtn.animate().scaleX(1 - slideOffset).scaleY(1 - slideOffset).setDuration(0).start();
        }
    };

    private int calculateBottomSheetWidht() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (dpWidth / 70);
    }

    void initShowcaseView() {
        if (!sharedPreferences.getBoolean(SHOWED_SHOWCASE, false)) {
            sharedPreferences.edit().putBoolean(SHOWED_SHOWCASE, true).apply();
            new ShowcaseView.Builder(this)
                    .withMaterialShowcase()
                    .setTarget(new ViewTarget(mAddToWidghetBtn))
                    .setStyle(R.style.CustomShowcaseTheme)
                    .setContentTitle(getString(R.string.add_recipe_to_widget))
                    .setContentText(getString(R.string.add_recipe_ingredients_to_widget))
                    .hideOnTouchOutside()
                    .build();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
