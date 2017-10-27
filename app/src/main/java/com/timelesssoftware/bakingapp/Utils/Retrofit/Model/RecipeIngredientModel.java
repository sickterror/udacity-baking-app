package com.timelesssoftware.bakingapp.Utils.Retrofit.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Luka on 23. 10. 2017.
 */

public class RecipeIngredientModel implements Parcelable {
    public double quantity;
    public String measure;
    public String ingredient;

    public RecipeIngredientModel() {
    }

    protected RecipeIngredientModel(Parcel in) {
        quantity = in.readDouble();
        measure = in.readString();
        ingredient = in.readString();
    }

    public static final Creator<RecipeIngredientModel> CREATOR = new Creator<RecipeIngredientModel>() {
        @Override
        public RecipeIngredientModel createFromParcel(Parcel in) {
            return new RecipeIngredientModel(in);
        }

        @Override
        public RecipeIngredientModel[] newArray(int size) {
            return new RecipeIngredientModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(quantity);
        parcel.writeString(measure);
        parcel.writeString(ingredient);
    }
}
