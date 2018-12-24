package com.adryanev.dicoding.mymoviecatalogue.config;

import com.adryanev.dicoding.mymoviecatalogue.BuildConfig;

public class Config {

    public static final String API_KEY = BuildConfig.ApiKey;
    public static final String API_ENDPOINT = "https://api.themoviedb.org/3/";
    private static final String IMAGE_ENDPOINT = "https://image.tmdb.org/t/p/";
    public static final String IMAGE_W185 = IMAGE_ENDPOINT+"w185/";
    public static final String IMAGE_W92 = IMAGE_ENDPOINT+"w92/";
    public static final String IMAGE_W154 = IMAGE_ENDPOINT+"w154/";
    public static final String IMAGE_W342 = IMAGE_ENDPOINT+"w342/";
    public static final String IMAGE_W500 = IMAGE_ENDPOINT+"w500/";
    public static final String IMAGE_W780 = IMAGE_ENDPOINT+"w780/";
    public static final String IMAGE_ORIGINAL = IMAGE_ENDPOINT+"original/";
    public static final String URL_MOVIE = "https://www.themoviedb.org/movie/";

    public static class Database{
        public static final String DB_NAME = "catalogue_db";
        public static final String TABLE_FAVOURITE = "favourite";
        public static final String KEY_ID ="id";
        public static final String KEY_TITLE = "title";
        public static final String KEY_RELEASE_DATE= "release_date";
        public static final String KEY_POSTER = "poster";


    }
}
