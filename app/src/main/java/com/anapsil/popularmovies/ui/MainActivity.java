package com.anapsil.popularmovies.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.anapsil.popularmovies.R;
import com.anapsil.popularmovies.data.Movie;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;
    private MoviesAdapter adapter = new MoviesAdapter();
    private boolean isTopRated = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        RecyclerView recyclerView = findViewById(R.id.movies);
        recyclerView.hasFixedSize();
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.fetchPopularMovies();
        viewModel.movies.observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                adapter.updateMovies(movies);
            }
        });
        viewModel.error.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean isError) {
                if (isError != null && isError) {
                    Toast.makeText(MainActivity.this, getString(R.string.error_message), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_top_rated) {
            item.setTitle(controlMenuTitle());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private String controlMenuTitle() {
        if (isTopRated) {
            isTopRated = false;
            viewModel.fetchPopularMovies();
            return getString(R.string.menu_top_rated);
        } else {
            isTopRated = true;
            viewModel.fetchTopRatedMovies();
            return getString(R.string.menu_popular);
        }
    }
}
