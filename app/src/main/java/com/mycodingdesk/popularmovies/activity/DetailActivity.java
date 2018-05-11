package com.mycodingdesk.popularmovies.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mycodingdesk.popularmovies.R;
import com.mycodingdesk.popularmovies.databinding.ActivityDetailBinding;
import com.mycodingdesk.popularmovies.model.Movie;
import com.mycodingdesk.popularmovies.network.MovieAPI;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailActivity extends AppCompatActivity {

    public static final String MOVIE_DATA = "MOVIE_DATA";
    private ActivityDetailBinding mDetailActivityBinding;
    private boolean mIsTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDetailActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        setSupportActionBar(mDetailActivityBinding.toolbarMovieDetail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mIsTablet = getResources().getBoolean(R.bool.isTablet);

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(MOVIE_DATA)) {
                Movie movie = intent.getParcelableExtra(MOVIE_DATA);
                displayMovieData(movie);
            }
        }
    }

    private void displayMovieData(Movie movie) {
        mDetailActivityBinding.movieVoteTv.setRating(movie.getVoteAverage());
        mDetailActivityBinding.movieSynopsisTv.setText(movie.getOverview());
        mDetailActivityBinding.movieTitleTv.setText(movie.getTitle());
        mDetailActivityBinding.moviePosterIv.setContentDescription(getString(R.string.movielist_poster_content, movie.getTitle()));
        URL urlImage = MovieAPI.buildImageBackdropURL(mIsTablet);
        Picasso.with(this)
                .load(urlImage.toString() + movie.getBackdropPath())
                .into(mDetailActivityBinding.moviePosterIv);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());
        try {
            Date date = format.parse(movie.getReleaseDate());
            mDetailActivityBinding.movieReleasedateTv.setText(dateFormat.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
