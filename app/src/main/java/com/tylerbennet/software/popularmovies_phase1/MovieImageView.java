package com.tylerbennet.software.popularmovies_phase1;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import static android.content.Context.WINDOW_SERVICE;

/**
 * Created by Tyler Bennet on 3/7/2017.
 */

public class MovieImageView extends ImageView {
    private static final String LOG_TAG = MovieImageView.class.getSimpleName();

    public MovieImageView(Context context) {
        super(context);
    }

    public MovieImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MovieImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //int widthPixels = View.MeasureSpec.getSize( widthMeasureSpec );
        //int heightPixels = View.MeasureSpec.getSize( heightMeasureSpec );

        int widthPixels;
        int height = 0;
        int width = 0;

        DisplayMetrics displayMetrics = new DisplayMetrics();


        Display display = ((WindowManager) getContext().getSystemService(WINDOW_SERVICE))
                .getDefaultDisplay();

        int orientation = display.getRotation();

        if (orientation == Surface.ROTATION_90 || orientation == Surface.ROTATION_270) {
            widthPixels = View.MeasureSpec.getSize( widthMeasureSpec );

            ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            height = displayMetrics.heightPixels;
            width = displayMetrics.widthPixels;
            setMeasuredDimension(widthPixels, height);

        } else if (orientation == Surface.ROTATION_0 || orientation == Surface.ROTATION_180){
            //DisplayMetrics displayMetrics = new DisplayMetrics();
            widthPixels = View.MeasureSpec.getSize( widthMeasureSpec );
            ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            height = displayMetrics.heightPixels;
            width = displayMetrics.widthPixels;
            setMeasuredDimension(widthPixels, height/2);
        }
    }

}
