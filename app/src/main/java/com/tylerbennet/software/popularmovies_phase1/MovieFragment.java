package com.tylerbennet.software.popularmovies_phase1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Main Fragment that handles the core application features. This handles the
 * tasks for connecting up and downloading data from our vendor database.
 * In addition this allows for the translation of data to parse it into
 * a readable format for our adapters.
 */
public class MovieFragment extends Fragment {

    private static String LOG_TAG =  MovieFragment.class.getSimpleName();
    private ArrayAdapter<String> mListAdapter;
    private MovieCollection collectionOfMovies;
    private MovieAdapter mAdapter;

    /**
     * Default constructor handles the initialization of our
     * collection of movie data.
     */
    public MovieFragment() {
        // Required empty public constructor
        collectionOfMovies = new MovieCollection();
    }

    /**
     * Default onCreate method.
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    /**
     * OnStart method handles our application when the state changes
     * or the activity starts to pull the movie data.
     */
    @Override
    public void onStart() {
        super.onStart();
        if (isOnline()) {
            updateMovies();
        }

    }

    /**
     * isOnline checks for our network connectivity and reports back the results.
     *
     * @return boolean
     */
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    /**
     * Updates the movies shared preferences for access by the rest of the application.
     * Handles the execution of our async task to fetch JSON data from the
     * vendor website.
     */
    public void updateMovies() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String searchType = prefs.getString(
                getResources().getString(R.string.pref_sort_key),
                getResources().getString(R.string.pref_sort_popular));
        FetchMoviesTask test = new FetchMoviesTask();
        test.execute(searchType);
    }

    /**
     * OnCreateView handles the movie adapter along with initializing the
     * gridview.
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.movie_grid_view, container, false);
        mAdapter = new MovieAdapter(rootView.getContext(), new MovieCollection());

        GridView gridView = (GridView) rootView.findViewById(R.id.gridview);

        gridView.setAdapter(mAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie individualMovie = mAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), MovieDetailActivity.class)
                        .putExtra("movie", individualMovie);
                startActivity(intent);
            }
        });

        return rootView;
    }

    /**
     * FetchMoviesTask is an async task that parses JSON data from the vendor
     * website using our API key and URI built from code. Task will handle collecting
     * the data and send this back to the MovieFragment class.
     */
    public class FetchMoviesTask extends AsyncTask<String, Void, MovieCollection> {

        /**
         * Default method not utilized.
         */
        @Override
        protected void onPreExecute() {
        }

        /**
         * Background method for collecting movie data in the JSON format.
         *
         * @param params
         * @return
         */
        @Override
        protected MovieCollection doInBackground(String... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String movieJsonString = null;

            try {
                Uri.Builder movieUri = new Uri.Builder()
                        .scheme("https")
                        .authority("api.themoviedb.org")
                        .path("3/movie/")
                        .appendPath(params[0])
                        .appendQueryParameter("api_key", BuildConfig.API_KEY);
                Log.v("MovieFragment", movieUri.toString());

                URL url = new URL(movieUri.toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                movieJsonString = buffer.toString();
                Log.v(LOG_TAG, movieJsonString.toString());

            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if(reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            try {
                return getMovieDataFromJson(movieJsonString);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            return null;
        }

        /**
         * Default method not utilized.
         * @param params
         */
        @Override
        protected void onProgressUpdate(Void... params) {

        }

        /**
         * Method updates the movie adapter when async process is completed along
         * with updating the adapter that our data has changed.
         */
        @Override
        protected void onPostExecute(MovieCollection result) {
            if (result != null) {
                mAdapter.clear();
                mAdapter.addMovieCollection(result);
            }
        }

        /**
         * Method parses our JSON data into a Movie object for later use.
         *
         * @param movieJsonStr
         * @return
         * @throws JSONException
         */
        private MovieCollection getMovieDataFromJson(String movieJsonStr) throws JSONException {
            final String RESULTS = "results";
            final String JSON_TITLE = "original_title";
            final String JSON_MOVIE_IMAGE_PATH = "poster_path";
            final String JSON_PLOT = "overview";
            final String JSON_USER_RATING = "vote_average";
            final String JSON_RELEASE_DATE = "release_date";

            JSONObject movieJson = new JSONObject(movieJsonStr);
            JSONArray movieArray = movieJson.getJSONArray(RESULTS);

            //Creates a movie collection for easy object access.
            MovieCollection movieList = new MovieCollection();

            for (int i = 0; i < movieArray.length(); i++) {
                String title;
                String movieImagePath;
                String plot;
                String userRating;
                String releaseDate;
                String runtime;

                //Get the next movie JSON Object
                JSONObject movieObject = movieArray.getJSONObject(i);
                title = movieObject.getString(JSON_TITLE);
                movieImagePath = movieObject.getString(JSON_MOVIE_IMAGE_PATH);
                plot = movieObject.getString(JSON_PLOT);
                userRating = movieObject.getString(JSON_USER_RATING);
                releaseDate = movieObject.getString(JSON_RELEASE_DATE);

                //Add our movie to the collection.
                movieList.addMovie(new Movie(title, movieImagePath, plot, userRating, releaseDate));

            }

            return movieList;
        }


    }
}


