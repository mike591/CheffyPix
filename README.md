![logo](docs/images/cheffy_black_whitespace.png)

## CheffyPix

### Background

CheffyPix is a native mobile app for Android, whose purpose is to connect foodies together and celebrate cooking. Users can search recipes for their favorite foods, take pictures of their completed recipes, and share pictures and recipes with others.

### Demo

See our demo page [here](link) for an in-depth view of our features.

### Features List

- Start searching for your favorite recipes immediately from the homepage
- View search results as a scrolling list of images to quickly find your recipe
- View recipe page with a full list of ingredients and a link to cooking directions
- Navigate from recipe view to recipe sharing mode with the Share button
- Take pictures of your own creation and send them to your friends and family
- Auto-fill your text, email, or any other compatible sharing app with the recipe and your image

### Screenshots

TODO: homepage with search view, list view, lack of response view, ingredients, redirect, share custom picture, texting with response

## Install

Find us on Google Play! CheffyPix is a free android app and can be found [here](add link).

## Architecture and Technologies

This project was implemented in Java using Android Studio. The application uses the Food2Fork API for recipe information and details.

### Android App Architecture
CheffyPix was created in Android Studio and is a native Android application. 
It is written in Java, compiled with Android SDK tools 25.2.5 and intended for Android Nougat and above.
Our app runs, as is default, its own virtual machine with its own Linux process. In addition, it will request Camera and Device Storage permission.

As such, it interacts with multiple Managers in the Android application framework:
- For the rendering of our views: interacts with Window Manager and View System and Resource Manager
- For the core logic of our views: Activity Manager
- For interactions with device storage to save images: Package Manager
- And others for core functionality

Our code implements MVC ideals through the use of Activities, Layouts, and Recipe Classes. Our views are largely created in our layouts, which are xml files containing references to resources and classes related to the user interface. These are then inflated by our Activities, which control events and user inputs, and handle framework-imposed events. Our information is encapsulated by our Recipe classes, which model and define what we will be rendering on our views.

Our list of Activities (each handling distinct screens for user interaction) include:
- A homepage with logo and search bar, as  `MainActivity`
- A recipe list view of results, as `RecipeListActivity`
- A recipe detail view, as `RecipeDetailActivity`

As well we have classes for:
- Recipe results for our lists view, as `RecipeListItem`
- Recipe details for our detailed recipe view, as `RecipeDetail`

In addition, we have resources and classes for display design:
- Layouts for each Activity to define the visual structure of the UI
- Drawables for details such as the rounded search bar and custom drop shadows
- Custom View classes to override unwanted default behaviors

### Food2Fork API

Our application makes use of the [Food2Fork API](http://food2fork.com/about/api).

### Implementation Details

The application code includes inplementations of:

- Asynchronous tasks, implemented by subclassing built-in `AsyncTask` to reduce the need for manipulating threads or handlers
- API request interaction by building an URI reference with the query and API key appended, and opening the connection using `HttpURLConnection` methods
- API response interaction by reading the the stream and parsing the JSON response with `JSONObject` methods and related functionality
- JSON to Bitmap parser to display images retreived from the API
- Recipe classes to properly encapsulate the recipe information retreived
- Nested views, implemented with `ListView`s and appropriate adapters to populate the list item

As well the application uses many design customizations, including:
- Custom View class for lists
- Many drawables including an implementation of drop shadows and rounded search bar
- Custom logo and variations created by the team

### Sample Code

In the code below, we show an example of asynchornous tasks and encapsulation with classes. AN API call runs in the background, and on completion, the information is retrieved through class methods and mounted to the various views. An adapter is used to inflate the list item view into the recipe list view.

```java
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
            if (mRecipeListItems.size() == 0) {
                mSadImageView.setImageResource(R.drawable.ic_cheffy_black_sad);
                mSadTextView.setText("No recipes found!");

            } else {
                RecipeListAdapter adapter = new RecipeListAdapter(RecipeListActivity.this, mRecipeListItems);
                mRecipeListView.setAdapter(adapter);
            }
            mSpinner.setVisibility(View.GONE);
        }
}
```
This async task class shown above is used in `RecipeListView`. It will show a spinner as it runs, call the API in `doInBackground`, create a `RecipeItem` with the  information from the API, and `onPostExecute` it will then stop the spinner and call an adapter to mount the information to a `ListView`.

The code below shows the use of an adapter to correctly display each list item as designed in the `ListItem` layout file and attach necessary listeners. On click or tap, the activity will fire off new `Intent`s with title and image information passed along to the new view.

```java
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
                    intent.putExtra(RECIPE_TITLE, item.getTitle());

                    startActivity(intent);
                }
            });

            return rowView;
        }
    }
```

## Logo Design
Our project name was created by Michael Mach. Our logo was designed and drawn by Nicholas Vizzutti. Variations based on the original design were created by Kathy Luo.

##Team Members

Michael Mach | Nicholas Vizzutti | Kathy Luo
---|---|---
[LinkedIn](https://www.linkedin.com/in/michael-mach-77485791) | [LinkedIn](https://www.linkedin.com/in/nvizzutti)| [LinkedIn](https://www.linkedin.com/in/kathy-luo-24517751)
[GitHub](https://github.com/mike591) | [GitHub](https://github.com/NVizzutti) | [Github](https://github.com/kalu1302)
