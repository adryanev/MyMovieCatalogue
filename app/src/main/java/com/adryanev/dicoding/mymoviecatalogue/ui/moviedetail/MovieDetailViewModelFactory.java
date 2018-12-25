package com.adryanev.dicoding.mymoviecatalogue.ui.moviedetail;

import android.app.Application;

import com.adryanev.dicoding.mymoviecatalogue.data.entities.movie.Movie;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

public class MovieDetailViewModelFactory extends ViewModelProvider.AndroidViewModelFactory {

    Application application;
    String movieId;
    public MovieDetailViewModelFactory(@NonNull Application application, String id) {
        super(application);
        this.application = application;
        this.movieId = id;

    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(MovieDetailViewModel.class)){
            return (T) new MovieDetailViewModel(movieId,application);
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
