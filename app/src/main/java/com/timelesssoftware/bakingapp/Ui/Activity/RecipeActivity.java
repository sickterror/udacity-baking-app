package com.timelesssoftware.bakingapp.Ui.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.timelesssoftware.bakingapp.R;
import com.timelesssoftware.bakingapp.Ui.Fragment.Recipe;
import com.timelesssoftware.bakingapp.Utils.Retrofit.Model.RecipeStepsModel;

import java.util.List;

public class RecipeActivity extends AppCompatActivity implements Recipe.OnRecipeFragmentInteractionListener {

    private static final String RECEPIE_FRAGMENT = "recipe_fragment";
    private RecipeStepsModel recipeSteps;
    private List<RecipeStepsModel> nextModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recipeSteps = getIntent().getExtras().getParcelable(RecipeStepStepsActivity.SELECTED_RECIPE_STEPS);
        nextModel = getIntent().getExtras().getParcelableArrayList(RecipeStepStepsActivity.NEXT_STEPS);

        getSupportActionBar().setTitle(getIntent().getExtras().getString(RecipeStepStepsActivity.RECIPE_TITLE, getString(R.string.recipe_title_defualt_value)));

        Recipe recipe = Recipe.newInstance(recipeSteps);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.recipe_fragment_container, recipe, RECEPIE_FRAGMENT).commit();
        } else {
            getSupportFragmentManager().findFragmentByTag(RECEPIE_FRAGMENT);
        }
    }

    @Override
    public void onNextStep() {
        if (nextModel != null) {
            int nextStep = recipeSteps.id + 1;
            int size = nextModel.size();
            if (size - 1 > nextStep) {
                Log.d("onNext", "listSize: " + nextModel.size() + " nextStep: " + nextStep);
                try {
                    recipeSteps = nextModel.get((nextStep));
                    Recipe recipe = Recipe.newInstance(recipeSteps);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.recipe_fragment_container, recipe, RECEPIE_FRAGMENT).commit();
                } catch (Exception e) {
                    Log.w("onNext", e);
                }
            }
        }
    }

    @Override
    public void onPrevStep() {
        if (nextModel != null) {
            int prevStep = recipeSteps.id - 1;
            if (prevStep >= 0) {
                Log.d("onPrev", "listSize: " + nextModel.size() + " nextStep: " + prevStep);
                try {
                    recipeSteps = nextModel.get((prevStep));
                    Recipe recipe = Recipe.newInstance(recipeSteps);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.recipe_fragment_container, recipe, RECEPIE_FRAGMENT).commit();

                } catch (Exception e) {
                    Log.w("onPrev", e);
                }
            }
        }
    }
}
