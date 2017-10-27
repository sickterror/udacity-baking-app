package com.timelesssoftware.bakingapp.Utils.Retrofit.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Luka on 23. 10. 2017.
 */

public class RecipeListModel implements Parcelable {
    public int id;
    public String name;
    public List<RecipeStepsModel> steps;
    public List<RecipeIngredientModel> ingredients;
    public int servings;
    public String image;

    public RecipeListModel() {
    }

    protected RecipeListModel(Parcel in) {
        id = in.readInt();
        name = in.readString();
        steps = in.createTypedArrayList(RecipeStepsModel.CREATOR);
        ingredients = in.createTypedArrayList(RecipeIngredientModel.CREATOR);
        servings = in.readInt();
        image = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeTypedList(steps);
        dest.writeTypedList(ingredients);
        dest.writeInt(servings);
        dest.writeString(image);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RecipeListModel> CREATOR = new Creator<RecipeListModel>() {
        @Override
        public RecipeListModel createFromParcel(Parcel in) {
            return new RecipeListModel(in);
        }

        @Override
        public RecipeListModel[] newArray(int size) {
            return new RecipeListModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<RecipeStepsModel> getSteps() {
        return steps;
    }

    public List<RecipeIngredientModel> getIngredients() {
        return ingredients;
    }

    public int getServings() {
        return servings;
    }

    public String getImage() {
        return image;
    }
}
