package com.adryanev.dicoding.mymoviecatalogue.ui.moviedetail;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import timber.log.Timber;

import com.adryanev.dicoding.mymoviecatalogue.data.AppRepository;
import com.adryanev.dicoding.mymoviecatalogue.data.entities.favourite.Favourite;
import com.adryanev.dicoding.mymoviecatalogue.data.entities.movie.Movie;

/**
 * Project: MyMovieCatalogue
 *
 * @author Adryan Eka Vandra <adryanekavandra@gmail.com>
 * Date: 12/22/2018
 * Time: 3:15 PM
 */
public class MovieDetailViewModel extends ViewModel {
    AppRepository repository;
    MutableLiveData<Movie> data;
    LiveData<Favourite> dataFav;
    String id;
    Context appContext;


    public MovieDetailViewModel(@NonNull String id, Application application) {
        repository = new AppRepository(application);
        this.id = id;
        Timber.d("MovieId: %s ",this.id);
        data = repository.getMovie(id);

    }

    public MutableLiveData<Movie> getMovie(){
        return data;
    }

    public LiveData<Favourite>getDataFav(Integer id){
        dataFav = repository.searchFavourite(id);
        return dataFav;
    }

}
