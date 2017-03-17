package com.tylerbennet.software.popularmovies_phase1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Fragment inflates the xml view for the title image. This allows for a dynamic
 * application title bar in case future app/views require changes.
 *
 */
public class TitleFragment extends Fragment {

    /**
     * Default constructor
     */
    public TitleFragment() {
    }


    /**
     * Default onCreate method.
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     *
     * Default onCreateView method.
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_title, container, false);
    }


}
