package com.timelesssoftware.bakingapp.Utils.Retrofit;

import com.timelesssoftware.bakingapp.Utils.Retrofit.Model.RecipeListModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Luka on 23. 10. 2017.
 */

public interface IBakingApi {
    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<List<RecipeListModel>> get();
}
