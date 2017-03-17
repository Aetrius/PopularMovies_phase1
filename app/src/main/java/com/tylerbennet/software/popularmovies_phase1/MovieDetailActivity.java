package com.tylerbennet.software.popularmovies_phase1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Tyler Bennet on 3/7/2017.
 */

/**
 * Main Detail Activity after selecting a movie. This class handles our data being set
 * along with our images.
 */
public class MovieDetailActivity extends AppCompatActivity {
    private Movie mItem;
    private static final String LOG_TAG = MovieDetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mItem = new Movie();

        ImageView movieDetailImage = (ImageView) findViewById(R.id.detailMovieImage);
        TextView movieTitle = (TextView) findViewById(R.id.movietitle);
        TextView movieYear = (TextView) findViewById(R.id.detailMovieYear);
        TextView movieSummary = (TextView) findViewById(R.id.movieDetailSummary);
        TextView movieRating = (TextView) findViewById(R.id.detailMovieRating);

        // The detail Activity called via intent.
        Intent intent = getIntent();
        if (intent != null) {
            Movie movie = (Movie) intent.getExtras().getParcelable("movie");
            this.mItem = movie;
        }

        movieTitle.setText(mItem.getTitle());
        movieYear.setText(mItem.getReleaseDate().substring(0, 4));
        movieRating.setText(mItem.getUserRating() + " / 10");
        movieSummary.setText(mItem.getPlot());

        Picasso.with(this)
                .load(mItem.getFullImagePath())
                .fit()
                .into(movieDetailImage);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

}
