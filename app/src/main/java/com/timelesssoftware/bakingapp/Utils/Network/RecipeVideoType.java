package com.timelesssoftware.bakingapp.Utils.Network;

/**
 * Created by Luka on 27.10.2017.
 */

public class RecipeVideoType implements RecipeMediaType {

    public String url;

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String getUrl() {
        return this.url;
    }
}
