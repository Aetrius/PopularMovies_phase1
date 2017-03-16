package com.tylerbennet.software.popularmovies_phase1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Tyler Bennet on 3/7/2017.
 */

public class MovieDetailFragment extends Fragment{
    private Movie mItem;
    private static final String LOG_TAG = MovieDetailFragment.class.getSimpleName();

    public MovieDetailFragment() {
        mItem = new Movie();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.movie_detail_fragment, container, false);
        ImageView movieDetailImage = (ImageView) rootView.findViewById(R.id.detailMovieImage);
        TextView movieTitle = (TextView) rootView.findViewById(R.id.movietitle);
        TextView movieYear = (TextView) rootView.findViewById(R.id.detailMovieYear);
        TextView movieSummary = (TextView) rootView.findViewById(R.id.movieDetailSummary);
        //TextView movieRuntime = (TextView) rootView.findViewById(R.id.detailMovieRuntime);
        TextView movieRating = (TextView) rootView.findViewById(R.id.detailMovieRating);
        // The detail Activity called via intent.  Inspect the intent for forecast data.
        Intent intent = getActivity().getIntent();
        if (intent != null) {
            //Movie movie = (Movie) intent.getParcelableExtra("movie");
            Movie movie = (Movie) intent.getExtras().getParcelable("movie");
            this.mItem = movie;
        }

        movieTitle.setText(mItem.getTitle());
        movieYear.setText(mItem.getReleaseDate().substring(0, 4));
        movieRating.setText(mItem.getUserRating() + " / 10");
        movieSummary.setText(mItem.getPlot());

        //movieRuntime.setText(mItem.)

        Picasso.with(getContext())
                .load(mItem.getFullImagePath())
                .fit()
                .into(movieDetailImage);


        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //inflater.inflate(R.menu.detailfragment, menu);

        // Retrieve the share menu item
        //MenuItem menuItem = menu.findItem(R.id.action_share);

    }


}
