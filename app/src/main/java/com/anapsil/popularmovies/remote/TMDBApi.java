package com.anapsil.popularmovies.remote;

import com.anapsil.popularmovies.data.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TMDBApi {

    @GET("/3/movie/popular")
    Call<MovieResponse> fetchPopularMovies(@Query("api_key") String apiKey);

    @GET("/3/movie/top_rated")
    Call<MovieResponse> fetchTopRatedMovies(@Query("api_key") String apiKey);
}
