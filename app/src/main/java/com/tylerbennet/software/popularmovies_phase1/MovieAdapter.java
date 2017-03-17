package com.tylerbennet.software.popularmovies_phase1;

import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.R.attr.rotation;

/**
 * Created by Tyler Bennet on 3/4/2017.
 */

/**
 * Custom Adapter to handle images dynamically based on screensize.
 *
 */
public class MovieAdapter extends BaseAdapter {
    private static final String LOG_TAG = MovieAdapter.class.getSimpleName();
    private ArrayList<Movie> movieList = new ArrayList<Movie>();
    private Context context;
    private int layoutResourceId;
    private int height;
    private int width;
    private Display display;

    /**
     * Custom constructor, inflates layout file, and the list is the data we want to populate into the lists.
     */

    public MovieAdapter(Context c, MovieCollection collectionIn) {
        //super(context, R.layout.movie_grid_view);
        this.context = c;
        this.movieList = collectionIn.getMovieCollection();
        // references to our images
        int height = context.getResources().getDisplayMetrics().heightPixels;
        int width = context.getResources().getDisplayMetrics().widthPixels;
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

    }

    public int getCount() {
        return movieList.size();
    }

    public Movie getItem(int position) {
        return movieList.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        //Giving the inflated layout to the view object
        View gridView;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridView = inflater.inflate(R.layout.list_item_movies, null);
            imageView = (ImageView) gridView.findViewById(R.id.grid_item_image);
        } else {
            imageView = (ImageView) convertView;
        }

        Picasso.with(context).load(movieList.get(position).getFullImagePath()).fetch();

        if (rotation == 0) {
            Picasso.with(context)
                    .load(movieList.get(position).getFullImagePath())
                    .into(imageView);
        } else {
            Picasso.with(context)
                    .load(movieList.get(position).getFullImagePath())
                    .fit()
                    .into(imageView);
        }

        return imageView;
    }

    public void addMovieCollection(MovieCollection collectionIn) {
        //Add movies
        this.movieList = collectionIn.getMovieCollection();
        notifyDataSetChanged();
    }

    public void clear() {
        movieList.clear();
    }

}
