package kathy.nick.mike.cohinza;
import java.util.Queue;

/**
 * Created by michael on 1/31/17.
 */

public class Recipe {
    private String mTitle;
    private String mImageUrl;
    private int mRecipeId;

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

    public Recipe(int recipeId, String title, String imageUrl) {
        mTitle = title;
        mImageUrl = imageUrl;
        mRecipeId = recipeId;
    }

}