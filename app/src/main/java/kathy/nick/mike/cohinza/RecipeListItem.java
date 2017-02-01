package kathy.nick.mike.cohinza;
import android.graphics.Bitmap;

import java.util.Queue;

/**
 * Created by michael on 1/31/17.
 */

public class RecipeListItem {
    private String mTitle;
    private Bitmap mImage;
    private String mRecipeId;

    public Bitmap getImage() {
        return mImage;
    }

    public void setImage(Bitmap image) {
        mImage = image;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getRecipeId() {
        return mRecipeId;
    }

    public void setRecipeId(String recipeId) {
        mRecipeId = recipeId;
    }

    public RecipeListItem(String recipeId, String title, Bitmap image) {
        mTitle = title;
        mImage = image;
        mRecipeId = recipeId;
    }

}