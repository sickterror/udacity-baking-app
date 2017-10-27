package com.timelesssoftware.bakingapp.Dagger.Components;

import android.content.Context;


import com.timelesssoftware.bakingapp.Dagger.ApplicationContext;
import com.timelesssoftware.bakingapp.Dagger.Modules.AppModule;
import com.timelesssoftware.bakingapp.Dagger.Modules.NetModule;
import com.timelesssoftware.bakingapp.MainActivity;
import com.timelesssoftware.bakingapp.Ui.Activity.RecipeStepStepsActivity;
import com.timelesssoftware.bakingapp.Ui.Widget.BakingListWidgetService;
import com.timelesssoftware.bakingapp.Ui.Widget.BakingWidgetProvider;
import com.timelesssoftware.bakingapp.Utils.Network.ApiHandler;

import javax.inject.Singleton;

import dagger.Component;

;


/**
 * Created by lukacas on 26/07/2017.
 */


@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public abstract class NetComponent {
    public abstract void inject(ApiHandler apiHandler);

    //void inject(AllPosLocationsViewModel allPosLocationsViewModel);
    @ApplicationContext
    public abstract Context getContext();

    public abstract void inject(MainActivity mainActivity);

    public abstract void inject(RecipeStepStepsActivity recipeStepsActivity);

    public abstract void inject(BakingWidgetProvider bakingWidgetProvider);

    public void inject(BakingListWidgetService bakingListWidgetService) {

    }
}


