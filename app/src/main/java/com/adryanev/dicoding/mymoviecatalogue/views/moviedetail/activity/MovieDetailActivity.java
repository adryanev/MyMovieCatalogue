package com.adryanev.dicoding.mymoviecatalogue.views.moviedetail.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.adryanev.dicoding.mymoviecatalogue.R;
import com.adryanev.dicoding.mymoviecatalogue.config.Config;
import com.adryanev.dicoding.mymoviecatalogue.data.entities.movie.Genre;
import com.adryanev.dicoding.mymoviecatalogue.data.rest.ApiInterface;
import com.adryanev.dicoding.mymoviecatalogue.data.rest.response.ResponseMovie;
import com.adryanev.dicoding.mymoviecatalogue.utils.RetrofitClient;
import com.adryanev.dicoding.mymoviecatalogue.views.poster.activity.PosterActivity;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity {
    ImageView backprop;
    Toolbar toolbarDetail;
    NestedScrollView scrollView;
    ApiInterface apiService;
    FloatingActionButton fab;
    TextView judulFilm, tahunRilis, genreText, revenueText, durationText, countryText,tagline, overview;
    RatingBar ratingBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        apiService = RetrofitClient.getClient().create(ApiInterface.class);
        prepareView();
        getData();



    }

    private void getData() {
        Intent i = getIntent();
        String movieId = i.getStringExtra("movie_id");
        apiService.getMovie(movieId).enqueue(new Callback<ResponseMovie>() {
            @Override
            public void onResponse(Call<ResponseMovie> call, Response<ResponseMovie> response) {
                ResponseMovie responseMovie = response.body();
                setDataToView(responseMovie);
            }

            @Override
            public void onFailure(Call<ResponseMovie> call, Throwable t) {

            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void setDataToView(final ResponseMovie responseMovie) {
        Picasso.get().load(Config.IMAGE_ORIGINAL+responseMovie.getBackdropPath()).into(backprop);
        judulFilm.setText(responseMovie.getOriginalTitle());
        tahunRilis.setText(responseMovie.getReleaseDate());
        genreText.setText(genreString(responseMovie.getGenres()));
        revenueText.setText(currency(responseMovie.getRevenue()));
        durationText.setText(responseMovie.getRuntime()!=null? responseMovie.getRuntime().toString():"0");
        countryText.setText(responseMovie.getProductionCountries().get(0).getName());
        tagline.setText(responseMovie.getTagline());
        overview.setText(responseMovie.getOverview());
        ratingBar.setRating(responseMovie.getVoteAverage().floatValue());
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MovieDetailActivity.this, PosterActivity.class);
                i.putExtra("path",responseMovie.getBackdropPath());
                startActivity(i);
            }
        });
    }

    private String currency(Long text){
        NumberFormat df = NumberFormat.getCurrencyInstance();
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setCurrencySymbol("$");
        dfs.setGroupingSeparator('.');
        dfs.setMonetaryDecimalSeparator('.');
        ((DecimalFormat) df).setDecimalFormatSymbols(dfs);
        return df.format(text);
    }
    private String genreString(List<Genre> genres) {
        String s = "";
        List<String> namelist  = new ArrayList<>();
        for(Genre g: genres){
           namelist.add(g.getName());
        }
        return TextUtils.join(",",namelist);
    }

    private void prepareView() {
        backprop = findViewById(R.id.backdrop);
        toolbarDetail = findViewById(R.id.toolbar_detail);
        scrollView = findViewById(R.id.detail_scroll_view);
        judulFilm = findViewById(R.id.judul_film);
        tahunRilis = findViewById(R.id.tahun_rilis);
        genreText = findViewById(R.id.genre_text);
        revenueText = findViewById(R.id.revenue_text);
        durationText = findViewById(R.id.duration_text);
        countryText = findViewById(R.id.country_text);
        tagline = findViewById(R.id.tagline);
        overview = findViewById(R.id.overview);
        ratingBar = findViewById(R.id.ratingBar);
        fab = findViewById(R.id.fab_detail);
        setSupportActionBar(toolbarDetail);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ratingBar.setMax(10);
        ratingBar.setNumStars(10);
        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

}
