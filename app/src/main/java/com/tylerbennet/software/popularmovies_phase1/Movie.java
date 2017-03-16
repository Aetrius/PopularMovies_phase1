package com.tylerbennet.software.popularmovies_phase1;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Tyler Bennet on 3/4/2017.
 */

public class Movie implements Parcelable {
    private String title;
    private String imagePath;
    private String plot;
    private String userRating;
    private String releaseDate;
    private URL imageUrl;
    private static final String LOG_TAG = Movie.class.getSimpleName();

    public Movie() {
        //Default constructor

        //initialize local variables
        this.title = null;
        this.imagePath = null;
        this.plot = null;
        this.userRating = null;
        this.releaseDate = null;
        this.imageUrl = null;
    }

    public Movie(String inTitle, String inImagePath,
                 String inPlot, String inUserRating,
                 String inReleaseDate) {

        //Assign variables in to local class variables
        this.title = inTitle;
        this.imagePath = inImagePath;
        this.plot = inPlot;
        this.userRating = inUserRating;
        this.releaseDate = inReleaseDate;
        buildImageURL();
    }

    public String getTitle() {
        return this.title;
    }

    public String getImagePath() {
        return this.imagePath;
    }

    public String getPlot() {
        return this.plot;
    }

    public String getUserRating() {
        return this.userRating;
    }

    public String getReleaseDate() {
        return this.releaseDate;
    }

    public void buildImageURL() {
        try {
            Uri.Builder movieUri = new Uri.Builder()
                    .scheme("http")
                    .authority("image.tmdb.org")
                    //.path("/t/p/w185/")
                    .path("/t/p/w780/" + this.imagePath);
            //fullImagePath = movieUri.toString();
            imageUrl = new URL(movieUri.toString());
            //possibly should look into a custom 185 based on device size!??!
            //http://image.tmdb.org/t/p/w185 + /path
            //outputs a verbose log of the result string
            Log.v(LOG_TAG, imageUrl.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public URL getImageUrl() {
        return this.imageUrl;
    }

    public String getFullImagePath() {
        return imageUrl.toString();
    }


    protected Movie(Parcel in) {
        title = in.readString();
        imagePath = in.readString();
        plot = in.readString();
        userRating = in.readString();
        releaseDate = in.readString();
        imageUrl = (URL) in.readValue(URL.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(imagePath);
        dest.writeString(plot);
        dest.writeString(userRating);
        dest.writeString(releaseDate);
        dest.writeValue(imageUrl);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };


}
