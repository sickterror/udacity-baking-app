package com.timelesssoftware.bakingapp.Ui.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.timelesssoftware.bakingapp.R;
import com.timelesssoftware.bakingapp.Utils.Retrofit.Model.RecipeStepsModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luka on 23. 10. 2017.
 */

public class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepsAdapter.RecipeStepsViewHolder> {

    List<RecipeStepsModel> recipeStepModelList = new ArrayList<>();
    OnRecipeStepAdapterSelectListener onRecipeStepAdapterSelectListener;

    public RecipeStepsAdapter(List<RecipeStepsModel> recipeStepModelList, OnRecipeStepAdapterSelectListener onRecipeStepAdapterSelectListener) {
        this.recipeStepModelList = recipeStepModelList;
        this.onRecipeStepAdapterSelectListener = onRecipeStepAdapterSelectListener;
    }

    @Override
    public RecipeStepsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_step_view_holder, parent, false);
        return new RecipeStepsViewHolder(v, onRecipeStepAdapterSelectListener);
    }

    @Override
    public void onBindViewHolder(RecipeStepsViewHolder holder, int position) {
        RecipeStepsModel recipeStepModel = recipeStepModelList.get(position);
        holder.mStepTv.setText(recipeStepModel.shortDescription);
        holder.mStep.setText(String.valueOf(recipeStepModel.id + 1) + ".");
    }

    @Override
    public int getItemCount() {
        return recipeStepModelList.size();
    }

    public class RecipeStepsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView mStepTv;
        private final TextView mStep;

        public RecipeStepsViewHolder(View itemView, OnRecipeStepAdapterSelectListener onRecipeStepAdapterSelectListener) {
            super(itemView);
            mStepTv = itemView.findViewById(R.id.step_name);
            mStep = itemView.findViewById(R.id.step);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            RecipeStepsModel recipeStepsModel = recipeStepModelList.get(getAdapterPosition());
            recipeStepsModel.currentIndex = getAdapterPosition();
            onRecipeStepAdapterSelectListener.onRecipeSelect(recipeStepsModel);
        }
    }

    public interface OnRecipeStepAdapterSelectListener {
        void onRecipeSelect(RecipeStepsModel recipeStepModel);
    }
}
