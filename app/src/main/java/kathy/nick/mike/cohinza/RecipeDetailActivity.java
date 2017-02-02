package kathy.nick.mike.cohinza;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class RecipeDetailActivity extends AppCompatActivity {
    private static final String TAG = "RecipeDetailActivity";
    private String recipeId;
    private Bitmap recipeImage;
    private String recipeTitle;
    private ImageView mImageView;
    private TextView mTitleView;
    private String linkText;
    private ArrayList<String> mIngredientsList;


    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        Intent intent = getIntent();
        recipeId = intent.getStringExtra(RecipeListActivity.RECIPE_ID_KEY);

        byte[] byteArray = intent.getByteArrayExtra(RecipeListActivity.RECIPE_IMAGE);
        recipeImage = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        recipeTitle = intent.getStringExtra(RecipeListActivity.RECIPE_TITLE);
        mImageView = (ImageView) findViewById(R.id.recipe_image);
        mImageView.setImageBitmap(recipeImage);
        mTitleView = (TextView) findViewById(R.id.recipe_title);
        mTitleView.setText(recipeTitle);

        new MakeIngredientsAPICallTask().execute();
    }


    private class MakeIngredientsAPICallTask extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                String recipeString = new ApiUtils().DetailSearchQuery(recipeId);
                JSONObject recipeJsonObject = new JSONObject(recipeString);
                JSONObject recipeDetailJsonObject = new JSONObject(recipeJsonObject.getString("recipe"));
                linkText = recipeDetailJsonObject.getString("source_url");
                JSONArray ingredientJsonArray = recipeDetailJsonObject.getJSONArray("ingredients");

                if (ingredientJsonArray != null) {
                    int len = ingredientJsonArray.length();
                    mIngredientsList = new ArrayList<String>();
                    for (int i=0;i<len;i++){
                        String ingredient = ingredientJsonArray.get(i).toString();
                        mIngredientsList.add(ingredient);
                    };
                };

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            NonScrollListView mIngredientListView = (NonScrollListView) findViewById(R.id.recipe_ingredient_list);
            ArrayAdapter<String> mIngredientListAdapter = new ArrayAdapter<String>(RecipeDetailActivity.this, R.layout.activity_recipe_detail_ingredient, mIngredientsList);
            mIngredientListView.setAdapter(mIngredientListAdapter);
            TextView mLinkView = (TextView) findViewById(R.id.recipe_url);
            mLinkView.setText(linkText);
            mLinkView.setMovementMethod(LinkMovementMethod.getInstance());
/*
            mIngredientListView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    // Disallow the touch request for parent scroll on touch of child view
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    return false;
                }
            });
*/
            //setListViewHeightBasedOnChildren(mIngredientListView);

        }
    }
};
