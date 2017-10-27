package com.timelesssoftware.bakingapp.Ui.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.timelesssoftware.bakingapp.R;
import com.timelesssoftware.bakingapp.Utils.Retrofit.Model.RecipeIngredientModel;

import java.util.List;

/**
 * Created by Luka on 26.10.2017.
 */

public class RecipeIngreidentAdapter extends RecyclerView.Adapter<RecipeIngreidentAdapter.RecipeIngreidentViewHolder> {

    private List<RecipeIngredientModel> recipeIngredientModels;

    public RecipeIngreidentAdapter(List<RecipeIngredientModel> recipeIngredientModels) {
        this.recipeIngredientModels = recipeIngredientModels;
    }

    @Override
    public RecipeIngreidentAdapter.RecipeIngreidentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_view_holder, parent, false);
        return new RecipeIngreidentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecipeIngreidentAdapter.RecipeIngreidentViewHolder holder, int position) {
        RecipeIngredientModel recipeIngredientModel = recipeIngredientModels.get(position);
        holder.mIngridentName.setText(recipeIngredientModel.ingredient);
        holder.mIntgredientMessure.setText(recipeIngredientModel.measure);
        holder.mIngredientQty.setText(String.valueOf(recipeIngredientModel.quantity));
    }

    @Override
    public int getItemCount() {
        return recipeIngredientModels.size();
    }

    public class RecipeIngreidentViewHolder extends RecyclerView.ViewHolder {
        private final TextView mIngridentName;
        private final TextView mIngredientQty;
        private final TextView mIntgredientMessure;

        public RecipeIngreidentViewHolder(View itemView) {
            super(itemView);
            mIngridentName = itemView.findViewById(R.id.ingredient_name);
            mIngredientQty = itemView.findViewById(R.id.ingredient_qty);
            mIntgredientMessure = itemView.findViewById(R.id.ingredient_messure);
        }
    }
}
