package com.anapsil.popularmovies.ui;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anapsil.popularmovies.R;
import com.anapsil.popularmovies.data.Movie;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.anapsil.popularmovies.ui.MovieDetailsActivity.MOVIE_EXTRA;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {
    public static final String POSTER_URL = "http://image.tmdb.org/t/p/w200";
    private List<Movie> movies = new ArrayList<>();

    void updateMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_movie_item, viewGroup, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int i) {
        Movie movie = movies.get(i);

        movieViewHolder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imageMovie;
        private TextView textMovie;

        MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imageMovie = itemView.findViewById(R.id.image_movie);
            textMovie = itemView.findViewById(R.id.text_movie);
            itemView.setOnClickListener(this);
        }

        void bind(Movie movie) {
            textMovie.setText(movie.getTitle());
            Picasso.get()
                    .load(POSTER_URL + movie.getPosterPath())
                    .into(imageMovie, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(Exception e) {
                            Log.e("PICASSO_ERROR", e.getLocalizedMessage());
                        }
                    });
        }

        @Override
        public void onClick(View v) {
            Context context = v.getContext();
            Intent intent = new Intent(context, MovieDetailsActivity.class);
            intent.putExtra(MOVIE_EXTRA, movies.get(getAdapterPosition()));
            context.startActivity(intent);
        }
    }
}
