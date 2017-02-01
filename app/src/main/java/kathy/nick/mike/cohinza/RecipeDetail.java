package kathy.nick.mike.cohinza;

import android.graphics.Bitmap;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by njvizzutti on 1/31/17.
 */

public class RecipeDetail {
    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Bitmap getImage() {
        return mImage;
    }

    public void setImage(Bitmap image) {
        mImage = image;
    }

    public String getRecipeId() {
        return mRecipeId;
    }

    public void setRecipeId(String recipeId) {
        mRecipeId = recipeId;
    }

    public String getSocialRank() {
        return mSocialRank;
    }

    public void setSocialRank(String socialRank) {
        mSocialRank = socialRank;
    }

    public ArrayList getIngredients() {
        return mIngredients;
    }

    public void setIngredients(ArrayList ingredients) {
        mIngredients = ingredients;
    }

    private String mTitle;
    private Bitmap mImage;
    private String mRecipeId;
    private String mSocialRank;
    private ArrayList mIngredients;

    public RecipeDetail(String title, Bitmap image, String socialRank, String recipeId, ArrayList ingredients) {
        mTitle = title;
        mImage = image;
        mRecipeId = recipeId;
        mSocialRank = socialRank;
        mIngredients = ingredients;
    }
}
