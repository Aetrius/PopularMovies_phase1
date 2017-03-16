package com.tylerbennet.software.popularmovies_phase1;

import java.util.ArrayList;

/**
 * Created by Tyler Bennet on 3/4/2017.
 */
//Holds a collection of movies and allows querying of the collection easily.
public class MovieCollection {
    private ArrayList<Movie> movieCollection;

    public MovieCollection() {
        movieCollection = new ArrayList<Movie>();

    }

    public MovieCollection(ArrayList<Movie> inArrayList) {
        this.movieCollection = inArrayList;
    }

    public void addMovie(Movie inMovie) {
        movieCollection.add(inMovie);
    }

    public Movie getMovieByName(String movieName) {
        //find movie by name ?
        return null;
    }

    public ArrayList<Movie> getMovieCollection() {
        return this.movieCollection;
    }

    public Movie getMovieAtPosition(int position) {
        return movieCollection.get(position);
    }
}
