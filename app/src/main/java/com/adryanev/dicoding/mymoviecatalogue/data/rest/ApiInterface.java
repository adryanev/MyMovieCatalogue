package com.adryanev.dicoding.mymoviecatalogue.data.rest;

import com.adryanev.dicoding.mymoviecatalogue.data.entities.movie.Movie;
import com.adryanev.dicoding.mymoviecatalogue.data.rest.response.ResponseSearch;
import com.adryanev.dicoding.mymoviecatalogue.data.rest.response.ResponseUpcoming;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("movie/popular")
    Call<ResponseSearch> getPopularMovies(@Query("page") int page);

    @GET("search/movie")
    Call <ResponseSearch> searchMovies(@Query("page") int page,
                                       @Query("query") String movie);

    @GET("movie/{movie_id}")
    Call<Movie> getMovie(@Path("movie_id") String movieId);

    @GET("movie/upcoming")
    Call <ResponseUpcoming> getUpcomingMovies(@Query("page") int page);

    @GET("movie/now_playing")
    Call<ResponseUpcoming> getNowPlaying(@Query("page") int page);
}
