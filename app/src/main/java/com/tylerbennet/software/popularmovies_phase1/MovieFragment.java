package com.tylerbennet.software.popularmovies_phase1;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class MovieFragment extends Fragment {

    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovies();
    }

    public void updateMovies() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String searchType = prefs.getString(
                getResources().getString(R.string.pref_sort_key),
                getResources().getString(R.string.pref_sort_popular));
        FetchMoviesTask test = new FetchMoviesTask();
        test.execute(searchType);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie, container, false);
    }

    public class FetchMoviesTask extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            //super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... params) {

            //Build URL to parse
            // /movie/popular
            // /movie/top_rated
            // http://api.themoviedb.org/3/movie/popular?api_key=604e5c2da5dbedfd39e7c6860c2e78a2
            Uri.Builder movieUri = new Uri.Builder()
                    .scheme("https")
                    .authority("api.themoviedb.org")
                    .path("3/movie/")
                    .appendPath(params[0])
                    .appendQueryParameter("api_key", BuildConfig.API_KEY);
            Log.v("MovieFragment", movieUri.toString());


            return null;
        }

        @Override
        protected void onProgressUpdate(Void... params) {

        }

        @Override
        protected void onPostExecute(Void v) {
            //return result;
        }

        private String[] getMovieDataFromJson(String movieJsonStr) {

            return null;
        }


    }
}


