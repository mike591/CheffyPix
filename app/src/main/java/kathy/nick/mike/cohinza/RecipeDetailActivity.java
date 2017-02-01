package kathy.nick.mike.cohinza;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class RecipeDetailActivity extends AppCompatActivity {
    private static final String TAG = "RecipeDetailActivity";
    private String recipeId;
    private Bitmap recipeImage;
    private String recipeTitle;
    private ImageView mImageView;
    private ArrayList<String> mIngredientsList;

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

        new MakeIngredientsAPICallTask().execute();
    }


    private class MakeIngredientsAPICallTask extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                String recipeString = new ApiUtils().DetailSearchQuery(recipeId);
                JSONObject recipeJsonObject = new JSONObject(recipeString);
                JSONObject recipeDetailJsonObject = new JSONObject(recipeJsonObject.getString("recipe"));
                JSONArray ingredientJsonArray = recipeDetailJsonObject.getJSONArray("ingredients");

                if (ingredientJsonArray != null) {
                    int len = ingredientJsonArray.length();
                    mIngredientsList = new ArrayList<String>();
                    for (int i=0;i<len;i++){
                        String ingredient = ingredientJsonArray.get(i).toString();
                        mIngredientsList.add(ingredient);
                    };
                };

                recipeTitle = recipeJsonObject.getString("title");
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            ListView mIngredientListView = (ListView) findViewById(R.id.recipe_ingredient_list);
            ArrayAdapter<String> mIngredientListAdapter = new ArrayAdapter<String>(RecipeDetailActivity.this, R.layout.activity_recipe_detail_ingredient, mIngredientsList);
            mIngredientListView.setAdapter(mIngredientListAdapter);
        }
    }
};
