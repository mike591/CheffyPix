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

As such it interacts with multiple Managers in the Android application framework:
- For the rendering of our views: interacts with Window Manager and View System and Resource Manager
- For the core logic of our views: Activity Manager
- For interactions with device storage to save images: Package Manager
- And others for core functionality

Our code implements MVC ideals through the use of Activities, Layouts, and Recipe Classes. Our views are largely created in our layouts, which are xml files containing references to resources and classes that have to do with the user interface. These are then inflated by our Activities, which control events and user inputs, and handle framework-imposed events. Our information is encapsulated by our Recipe classes which model and define what we will be rendering on our views.

Our list of Activities (each handling distinct screens for user interaction) include:
- A homepage with logo and search bar, as MainActivity
- A recipe list view of results, as RecipeListActivity
- A recipe detail view, as RecipeDetailActivity

As well we have classes for:
- Recipe results for our lists view, as RecipeListItem
- Recipe details for our detailed recipe view, as RecipeDetail

In addition, we have resources and classes for display design:
- Layouts for each Activity to define the visual structure of the UI
- Drawables for details such as the rounded search bar and custom drop shadows
- Custom View classes to override unwanted default behaviors

### Food2Fork API

Sample api call response

parsers implemented

### Implementation Details

The application code includes inplementations of:

- Asynchronous tasks, implemented by subclassing built-in AsyncTask to reduce the need for manipulating threads or handlers
- API request interaction by building an URI reference with the query and API key appended, and opening the connection using HttpURLConnection methods
- API response interaction by reading the the stream and parsing the JSON response with JSONObject methods and related functionality
- JSON to Bitmap parser to display images retreived from the API
- Recipe classes to properly encapsulate the recipe information retreived
- Nested views, implemented with ListViews and appropriate adapters to populate the list item

As well the application uses many design customizations, including:
- Custom View class for lists
- Many drawables including an implementation of drop shadows and rounded search bar
- Custom logo and variations created by the team

### Sample Code

In the sample code below, we show an example of asynchornous tasks and encapsulation with classes. AN API call runs in the background, and on completion, the information is retrieved through class methods and mounted to the various views:

TODO put code example of RecipeListView

The code below shows the use of an adaptor to correctly display the list item as designed in the ListItem xml.

TODO put code example from adapter

## Logo Design
Our project name was thought up by Michael Mach. Our logo was designed and drawn by Nicholas Vizzutti. Variations based on the the original design were created by Kathy Luo.

##Team Members

Michael Mach | Nicholas Vizzutti | Kathy Luo
---|---|---
[LinkedIn](https://www.linkedin.com/in/michael-mach-77485791) | [LinkedIn](https://www.linkedin.com/in/nvizzutti)| [LinkedIn](https://www.linkedin.com/in/kathy-luo-24517751)
[GitHub](https://github.com/mike591) | [GitHub](https://github.com/NVizzutti) | [Github](https://github.com/kalu1302)
