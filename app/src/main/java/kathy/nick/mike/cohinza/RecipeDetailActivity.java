package kathy.nick.mike.cohinza;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class RecipeDetailActivity extends AppCompatActivity {
    private static final String TAG = "RecipeDetailActivity";
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1234567890;
    private String recipeId;
    private Bitmap recipeImage;
    private String recipeTitle;
    private ImageView mImageView;
    private Button mCameraButton;
    private int REQUEST_IMAGE_CAPTURE;
    private TextView mTitleView;
    private String linkText;
    private ArrayList<String> mIngredientsList;


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

        mCameraButton = (Button) findViewById(R.id.camera_button);
        mCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                REQUEST_IMAGE_CAPTURE = 0;
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(imageBitmap);

            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (shouldShowRequestPermissionRationale(
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    // Explain to the user why we need to read the contacts
                }

                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

                // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
                // app-defined int constant that should be quite unique

            }

            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("image/jpeg");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            File f = new File(Environment.getExternalStorageDirectory() + File.separator + "temporary_file.jpg");
            try {
                f.createNewFile();
                FileOutputStream fo = new FileOutputStream(f);
                fo.write(bytes.toByteArray());
            } catch (IOException e) {
                e.printStackTrace();
            }
            share.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///sdcard/temporary_file.jpg"));
            String ingredients = "";
            for (int i = 0; i < mIngredientsList.size(); i++) {
                ingredients += "- " + mIngredientsList.get(i) + "\n";
            }
            share.putExtra(android.content.Intent.EXTRA_TEXT, ingredients);

            startActivity(Intent.createChooser(share, "Share Image"));
        }
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
            mLinkView.setClickable(true);
            mLinkView.setMovementMethod(LinkMovementMethod.getInstance());
            String htmlText = "<a href='" + linkText + "'>" + "View Recipe Directions" + "</a>";
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                mLinkView.setText(Html.fromHtml(htmlText, Html.FROM_HTML_MODE_LEGACY)); 
            } else {
                mLinkView.setText(Html.fromHtml(htmlText));
            }
        }
    }
};
