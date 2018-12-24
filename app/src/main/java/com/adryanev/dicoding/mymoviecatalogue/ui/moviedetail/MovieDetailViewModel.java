package com.adryanev.dicoding.mymoviecatalogue.ui.moviedetail;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

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
public class MovieDetailViewModel extends AndroidViewModel {
    AppRepository repository;
    MutableLiveData<Movie> data;
    LiveData<Favourite> dataFav;
    public MovieDetailViewModel(@NonNull Application application) {
        super(application);
        repository = new AppRepository(application);
    }

    public MutableLiveData<Movie> getMovie(String id){
        data = repository.getMovie(id);
        return data;
    }

    public LiveData<Favourite>getDataFav(Integer id){
        dataFav = repository.searchFavourite(id);
        return dataFav;
    }

}
