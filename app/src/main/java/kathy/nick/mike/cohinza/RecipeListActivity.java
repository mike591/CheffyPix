package kathy.nick.mike.cohinza;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RecipeListActivity extends AppCompatActivity {
    public static final String RECIPE_ID_KEY = "RECIPE_ID_KEY";
    public static final String RECIPE_IMAGE = "RECIPE_IMAGE";
    private static final String TAG = "RecipeListActivity";
    private ArrayList<RecipeListItem> mRecipeListItems = new ArrayList<>();
    private String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        Intent intent = getIntent();
        query = intent.getStringExtra(MainActivity.QUERY_KEY);

        new MakeInternetCallTask().execute();

    } // END OF onCreate

    private class RecipeListAdapter extends BaseAdapter {

        private Context mContext;
        private ArrayList mDataSource;
        private LayoutInflater mInflater;

        public RecipeListAdapter(Context context, ArrayList items) {
            mContext = context;
            mDataSource = items;
            mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }


        @Override
        public int getCount() {
            return mDataSource.size();
        }

        @Override
        public Object getItem(int position) {
            return mDataSource.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View rowView = mInflater.inflate(R.layout.activity_recipe_list_item, parent, false);
            final RecipeListItem item = (RecipeListItem) mDataSource.get(position);

            ImageView mImageView = (ImageView) rowView.findViewById(R.id.recipe_list_image);
            mImageView.setImageBitmap(item.getImage());

            TextView mTextView = (TextView) rowView.findViewById(R.id.recipe_list_title);
            mTextView.setText(item.getTitle());

            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(RecipeListActivity.this, RecipeDetailActivity.class);

                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    item.getImage().compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte[] byteArray = stream.toByteArray();

                    intent.putExtra(RECIPE_ID_KEY, item.getRecipeId());
                    intent.putExtra(RECIPE_IMAGE, byteArray);



                    startActivity(intent);
                }
            });

            return rowView;
        }
    } // END OF ADAPTER

    private class MakeInternetCallTask extends AsyncTask<Void,Void,Void> {
        private ProgressBar mSpinner;

        @Override
        protected void onPreExecute() {
            mSpinner = (ProgressBar) findViewById(R.id.progress_bar);
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                String recipesString = new ApiUtils().RecipesSearchQuery(query);
                try {
                    JSONObject recipeJsonObject = new JSONObject(recipesString);
                    JSONArray recipeJsonArray = recipeJsonObject.getJSONArray("recipes");
                    for (int i = 0; i < recipeJsonArray.length(); i++) {
                        String recipeId = recipeJsonArray.getJSONObject(i).getString("recipe_id");
                        String recipeTitle = recipeJsonArray.getJSONObject(i).getString("title");
                        String recipeImageUrl = recipeJsonArray.getJSONObject(i).getString("image_url");
                        Bitmap recipeImage = ApiUtils.getBitmapFromURL(recipeImageUrl);
                        RecipeListItem item = new RecipeListItem(recipeId, recipeTitle, recipeImage);
                        mRecipeListItems.add(item);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            ListView mRecipeListView = (ListView) findViewById(R.id.activity_recipe_list);
            RecipeListAdapter adapter = new RecipeListAdapter(RecipeListActivity.this, mRecipeListItems);
            mRecipeListView.setAdapter(adapter);
            mSpinner.setVisibility(View.GONE);
        }

    } // END OF MakeInternetCallTask
}
