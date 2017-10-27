package com.timelesssoftware.bakingapp.Utils.Retrofit.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Luka on 23. 10. 2017.
 */

public class RecipeStepsModel implements Parcelable {
    public int id;
    public String shortDescription;
    public String description;
    public String videoURL;
    public String thumbnailURL;
    public int currentIndex;


    public RecipeStepsModel() {
    }

    protected RecipeStepsModel(Parcel in) {
        id = in.readInt();
        shortDescription = in.readString();
        description = in.readString();
        videoURL = in.readString();
        thumbnailURL = in.readString();
        currentIndex = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(shortDescription);
        dest.writeString(description);
        dest.writeString(videoURL);
        dest.writeString(thumbnailURL);
        dest.writeInt(currentIndex);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RecipeStepsModel> CREATOR = new Creator<RecipeStepsModel>() {
        @Override
        public RecipeStepsModel createFromParcel(Parcel in) {
            return new RecipeStepsModel(in);
        }

        @Override
        public RecipeStepsModel[] newArray(int size) {
            return new RecipeStepsModel[size];
        }
    };
}
