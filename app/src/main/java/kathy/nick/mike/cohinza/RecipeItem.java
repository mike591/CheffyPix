package kathy.nick.mike.cohinza;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by njvizzutti on 1/31/17.
 */

public class RecipeItem {
    private String mTitle;
    private String mImageUrl;
    private int mRecipeId;
    private int mSocialRank;
    private ArrayList mIngredients;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    public int getRecipeId() {
        return mRecipeId;
    }

    public void setRecipeId(int recipeId) {
        mRecipeId = recipeId;
    }

    public int getSocialRank() {
        return mSocialRank;
    }

    public void setSocialRank(int socialRank) {
        mSocialRank = socialRank;
    }

    public ArrayList getIngredients() {
        return mIngredients;
    }

    public void setIngredients(ArrayList ingredients) {
        mIngredients = ingredients;
    }

    public RecipeItem(String title, String imageUrl, int socialRank, int recipeId, ArrayList ingredients) {
        mTitle = title;
        mImageUrl = imageUrl;
        mRecipeId = recipeId;
        mSocialRank = socialRank;
        mIngredients = ingredients;
    }
}
