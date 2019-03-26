package com.anapsil.popularmovies.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.anapsil.popularmovies.R;
import com.anapsil.popularmovies.data.Movie;
import com.squareup.picasso.Picasso;

import static com.anapsil.popularmovies.ui.MoviesAdapter.POSTER_URL;

public class MovieDetailsActivity extends AppCompatActivity {
    public static final String MOVIE_EXTRA = "movie";
    private ImageView imagePoster;
    private TextView textOriginalTitle;
    private TextView textYear;
    private TextView textLanguage;
    private TextView textVoteAverage;
    private TextView textOverview;
    private Movie movie;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        if (getIntent().hasExtra(MOVIE_EXTRA)) {
            movie = getIntent().getParcelableExtra(MOVIE_EXTRA);
            setTitle(movie.getTitle());
        }
        imagePoster = findViewById(R.id.image_poster);
        textOriginalTitle = findViewById(R.id.text_original_title);
        textYear = findViewById(R.id.text_year);
        textLanguage = findViewById(R.id.text_language);
        textVoteAverage = findViewById(R.id.text_vote_average);
        textOverview = findViewById(R.id.text_overview);

        Picasso.get()
                .load(POSTER_URL + movie.getPosterPath())
                .into(imagePoster);

        textOriginalTitle.setText(movie.getOriginalTitle());
        textYear.setText(movie.getReleaseDate().split("-")[0]);
        textLanguage.setText(movie.getOriginalLanguage());
        textVoteAverage.setText(movie.getVoteAverage().toString());
        textOverview.setText(movie.getOverview());
    }
}
