package com.timelesssoftware.bakingapp.Dagger.Modules;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.timelesssoftware.bakingapp.Dagger.ApplicationContext;
import com.timelesssoftware.bakingapp.Data.RecipeDbHelper;
import com.timelesssoftware.bakingapp.Utils.Network.ApiHandler;
import com.timelesssoftware.bakingapp.Utils.Retrofit.IBakingApi;


import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by lukacas on 26/07/2017.
 */

@Module
public class NetModule {
    // Dagger will only look for methods annotated with @Provides
    @Provides
    @Singleton
    // Application reference must come from AppModule.class
    SharedPreferences providesSharedPreferences(Application application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    @Provides
    @Singleton
    Cache provideOkHttpCache(Application application) {
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(application.getCacheDir(), cacheSize);
        return cache;
    }

    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        //gsonBuilder.setFieldNamingPolicy();
        return gsonBuilder.create();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Cache cache) {
        return new OkHttpClient.Builder()
                .cache(cache)
                .build();
    }

    @Provides
    @Singleton
    ApiHandler provideApiHandler(OkHttpClient okHttpClient, Gson mGson, SharedPreferences mSharedPrefernces) {
        return new ApiHandler(okHttpClient, mGson, mSharedPrefernces);
    }

    @Provides
    @Singleton
    Retrofit.Builder providerRetrofitBuilder(OkHttpClient okHttpClient, Gson gson) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .baseUrl("https://d17h27t6h515a5.cloudfront.net/");
    }

    @Provides
    @Singleton
    IBakingApi provideIBakingApi(Retrofit.Builder builder) {
        return builder.build().create(IBakingApi.class);
    }

    @Provides
    @Singleton
    RecipeDbHelper provideIRecipeDbHelper(@ApplicationContext Context context) {
        return new RecipeDbHelper(context);
    }
}
