package kathy.nick.mike.cohinza;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class RecipeDetailActivity extends AppCompatActivity {
    private static final String TAG = "RecipeDetailActivity";
    private String recipeId;
    private Bitmap recipeImage;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        Intent intent = getIntent();
        recipeId = intent.getStringExtra(RecipeListActivity.RECIPE_ID_KEY);

        byte[] byteArray = intent.getByteArrayExtra(RecipeListActivity.RECIPE_IMAGE);
        recipeImage = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        mImageView = (ImageView) findViewById(R.id.recipe_image);
        mImageView.setImageBitmap(recipeImage);

    }
}
