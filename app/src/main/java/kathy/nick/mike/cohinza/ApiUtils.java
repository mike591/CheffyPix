package kathy.nick.mike.cohinza;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by michael on 1/31/17.
 */

public class ApiUtils {
    private static final String TAG = "ApiUtils";
    private static final String AUTHORITY = "http";
    private static final String WEBSITE = "www.food2fork.com";
    private static final String API_KEY = "08d2eb8f18d41ad04b54491bec589cb6";

    public String RecipesSearchQuery(String query) throws IOException {
        Uri.Builder builder = new Uri.Builder();

        builder.scheme(AUTHORITY)
                .authority(WEBSITE)
                .appendPath("api")
                .appendPath("search")
                .appendQueryParameter("key", API_KEY)
                .appendQueryParameter("q", query);
        String myUrl = builder.build().toString();
        URL url = new URL(myUrl);


        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            String stuff = readStream(in);
            return stuff;
        } finally {
            urlConnection.disconnect();
        }
    }

    public String DetailSearchQuery(String recipeId) throws IOException {
        Uri.Builder builder = new Uri.Builder();

        builder.scheme(AUTHORITY)
                .authority(WEBSITE)
                .appendPath("api")
                .appendPath("get")
                .appendQueryParameter("key", API_KEY)
                .appendQueryParameter("rId", recipeId);
        String myUrl = builder.build().toString();
        URL url = new URL(myUrl);


        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            String stuff = readStream(in);
            return stuff;
        } finally {
            urlConnection.disconnect();
        }
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            Log.e(TAG, "getBitmapFromURL: ", e );
            return null;
        }
    }

    private String readStream(InputStream is) {
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        try {
            int i = is.read();
            while(i != -1) {
                bo.write(i);
                i = is.read();
            }
        } catch (IOException e) {
            Log.e(TAG, "readStream: ", e);
        }
        return bo.toString();
    }

}
