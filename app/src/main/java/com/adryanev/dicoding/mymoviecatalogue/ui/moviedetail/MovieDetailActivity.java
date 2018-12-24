package com.adryanev.dicoding.mymoviecatalogue.ui.moviedetail;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;

import com.adryanev.dicoding.mymoviecatalogue.data.entities.favourite.Favourite;
import com.adryanev.dicoding.mymoviecatalogue.ui.main.favourite.FavouriteViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.core.widget.NestedScrollView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import timber.log.Timber;

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
import com.adryanev.dicoding.mymoviecatalogue.data.entities.movie.Movie;
import com.adryanev.dicoding.mymoviecatalogue.ui.poster.PosterActivity;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class MovieDetailActivity extends AppCompatActivity {
    ImageView backprop;
    Toolbar toolbarDetail;
    NestedScrollView scrollView;
    FloatingActionButton fab;
    TextView judulFilm, tahunRilis, genreText, revenueText, durationText, countryText,tagline, overview;
    RatingBar ratingBar;
    MovieDetailViewModel viewModel;
    FavouriteViewModel favouriteViewModel;
    MaterialButton fav;
    Movie m;

    boolean isFavourite = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        viewModel= ViewModelProviders.of(this).get(MovieDetailViewModel.class);
        favouriteViewModel = ViewModelProviders.of(this).get(FavouriteViewModel.class);
        prepareView();



    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent i = getIntent();
        String movieId = i.getStringExtra("movie_id");
        getData(movieId);
        viewModel.getDataFav(Integer.parseInt(movieId)).observe(this, new Observer<Favourite>() {
            @Override
            public void onChanged(final Favourite favourite) {

                if(favourite == null){
                    fav.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    fav.setText(getResources().getString(R.string.favourite));
                    isFavourite = true;


                }else{
                    Timber.d("Favourite: %s",favourite.getId().toString());
                    isFavourite = false;
                    fav.setBackgroundColor(Color.DKGRAY);
                    fav.setText(getResources().getString(R.string.favourited));

                }
                fav.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(isFavourite){
                            Favourite fav = new Favourite();
                            fav.setId(m.getId());
                            fav.setPoster(m.getPosterPath());
                            fav.setTitle(m.getTitle());
                            fav.setReleaseDate(m.getReleaseDate());
                            favouriteViewModel.insertFavourite(fav);
                        }
                        else{
                            favouriteViewModel.removeFavourite(favourite);
                        }
                    }
                });
            }

        });

    }

    private void getData(String movieId) {
        viewModel.getMovie(movieId).observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(Movie movie) {
                setDataToView(movie);
                m = movie;
            }
        });

    }

    @SuppressLint("SetTextI18n")
    private void setDataToView(final Movie movie) {
        Picasso.get().load(Config.IMAGE_ORIGINAL+ movie.getBackdropPath()).into(backprop);
        judulFilm.setText(movie.getOriginalTitle());
        tahunRilis.setText(movie.getReleaseDate());
        genreText.setText(genreString(movie.getGenres()));
        revenueText.setText(currency(movie.getRevenue()));
        durationText.setText(movie.getRuntime()!=null? movie.getRuntime().toString():"0");
        countryText.setText(movie.getProductionCountries().get(0).getName());
        tagline.setText(movie.getTagline());
        overview.setText(movie.getOverview());
        ratingBar.setRating(movie.getVoteAverage().floatValue());
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MovieDetailActivity.this, PosterActivity.class);
                i.putExtra("path", movie.getPosterPath());
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
        return TextUtils.join(", ",namelist);
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
        fav = findViewById(R.id.detail_fav);
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
