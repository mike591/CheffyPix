package kathy.nick.mike.cohinza;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RecipeListActivity extends AppCompatActivity {
    private ArrayList<RecipeListItem> mRecipeListItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        ListView mRecipeListView = (ListView) findViewById(R.id.activity_recipe_list);

        ArrayList recipeList = new ArrayList();
        recipeList.add("recipe 1");
        recipeList.add("recipe 2");
        recipeList.add("recipe 3");
        recipeList.add("recipe 4");

        RecipeListAdapter adapter = new RecipeListAdapter(RecipeListActivity.this, recipeList);
        mRecipeListView.setAdapter(adapter);

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

            ImageView mImageView = (ImageView) rowView.findViewById(R.id.recipe_list_image);
            mImageView.setImageDrawable(getDrawable(R.drawable.default_food));

            return rowView;
        }
    } // END OF ADAPTER
}
