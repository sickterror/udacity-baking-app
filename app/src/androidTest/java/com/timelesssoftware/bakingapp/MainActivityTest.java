package com.timelesssoftware.bakingapp;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.timelesssoftware.bakingapp.Utils.Retrofit.Model.RecipeListModel;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.mockito.Mockito.*;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void registerIdlingResource() {
        final List<RecipeListModel> recipeListModel = new ArrayList<>();
        recipeListModel.add(mock(RecipeListModel.class));
        mActivityTestRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mActivityTestRule.getActivity().initRecylcerView(recipeListModel);
            }
        });
    }

    @Test
    public void mainActivityTest() {


        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recipe_list_rv), isDisplayed()));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction textView = onView(
                allOf(withId(R.id.step_name), withText("Recipe Introduction"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_step_rv),
                                        0),
                                1),
                        isDisplayed()));
        textView.check(matches(withText("Recipe Introduction")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.step_name), withText("Starting prep"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_step_rv),
                                        1),
                                1),
                        isDisplayed()));
        textView2.check(matches(withText("Starting prep")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.step_name), withText("Prep the cookie crust."),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_step_rv),
                                        2),
                                1),
                        isDisplayed()));
        textView3.check(matches(withText("Prep the cookie crust.")));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.step_name), withText("Prep the cookie crust."),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recipe_step_rv),
                                        2),
                                1),
                        isDisplayed()));
        textView4.check(matches(withText("Prep the cookie crust.")));

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
