package com.timelesssoftware.bakingapp.Ui.Adapter;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.timelesssoftware.bakingapp.R;
import com.timelesssoftware.bakingapp.Ui.Fragment.RecipeSteps;
import com.timelesssoftware.bakingapp.Utils.Glide.GlideApp;
import com.timelesssoftware.bakingapp.Utils.Retrofit.Model.RecipeListModel;

import java.util.List;

/**
 * Created by Luka on 23. 10. 2017.
 */

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder> {

    private List<RecipeListModel> recipeListModels;
    private OnRecipeSelectListener recipeSelectListener;

    public RecipeListAdapter(List<RecipeListModel> recipeListModels, OnRecipeSelectListener recipeSelectListener) {
        this.recipeListModels = recipeListModels;
        this.recipeSelectListener = recipeSelectListener;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_list_view_holder, parent, false);
        return new RecipeViewHolder(v, recipeSelectListener);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        RecipeListModel recipeListModel = recipeListModels.get(position);
        holder.mRecipeName.setText(recipeListModel.name);
        //Load image from local storage
        if (recipeListModel.getImage() == null || recipeListModel.getImage().isEmpty()) {
            holder.setImage(getImage(recipeListModel.getId()));
        } else {
            Glide.with(holder.itemView.getContext())
                    .load(recipeListModel.getImage())
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.placeholder))
                    .into(holder.mRecipeImag);
        }
    }

    private int getImage(int id) {
        switch (id) {
            case 1:
                return R.drawable.nutellapie;
            case 2:
                return R.drawable.brownies;
            case 3:
                return R.drawable.yellowcake;
            case 4:
                return R.drawable.cheescake;
        }
        return 0;
    }


    @Override
    public int getItemCount() {
        return recipeListModels.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView mRecipeName;
        private final ImageView mRecipeImag;
        private OnRecipeSelectListener recipeSelectListener;

        public RecipeViewHolder(View itemView, OnRecipeSelectListener recipeSelectListener) {
            super(itemView);
            this.recipeSelectListener = recipeSelectListener;
            mRecipeName = itemView.findViewById(R.id.recipe_name);
            mRecipeImag = itemView.findViewById(R.id.recipe_image);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            recipeSelectListener.onRecipeSelect(recipeListModels.get(getAdapterPosition()));
        }

        private void setImage(int res) {
            mRecipeImag.setImageResource(res);
            Bitmap bitmap = ((BitmapDrawable) mRecipeImag.getDrawable()).getBitmap();
            Palette p = Palette.from(bitmap).generate();
            mRecipeName.setBackgroundColor(p.getLightMutedColor(itemView.getContext().getResources().getColor(R.color.md_white_1000)));
        }
    }

    public interface OnRecipeSelectListener {
        void onRecipeSelect(RecipeListModel recipeListModel);
    }
}
