package kathy.nick.mike.cohinza;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by njvizzutti on 1/31/17.
 */

public class RecipeDetail {
    private String mTitle;
    private String mImageUrl;
    private int mRecipeId;
    private int mSocialRank;
    private ArrayList mIngredients;

    public RecipeDetail(String title, String imageUrl, int socialRank, int recipeId, ArrayList ingredients) {
        mTitle = title;
        mImageUrl = imageUrl;
        mRecipeId = recipeId;
        mSocialRank = socialRank;
        mIngredients = ingredients;
    }
}
