package com.anapsil.popularmovies.ui;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.anapsil.popularmovies.BuildConfig;
import com.anapsil.popularmovies.data.Movie;
import com.anapsil.popularmovies.data.MovieResponse;
import com.anapsil.popularmovies.remote.TMDBApi;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainViewModel extends ViewModel {
    private final String BASE_URL = "https://api.themoviedb.org/";
    final MutableLiveData<List<Movie>> movies = new MutableLiveData<>();
    final MutableLiveData<Boolean> error = new MutableLiveData<>();
    private TMDBApi api;

    public MainViewModel() {
        this.api = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .build()
                .create(TMDBApi.class);
    }

    void fetchPopularMovies() {
        api.fetchPopularMovies(BuildConfig.TMDB_API_KEY).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    movies.postValue(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                error.postValue(true);
            }
        });
    }

    void fetchTopRatedMovies() {
        api.fetchTopRatedMovies(BuildConfig.TMDB_API_KEY).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    movies.postValue(response.body().getResults());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                error.postValue(true);
            }
        });
    }
}
