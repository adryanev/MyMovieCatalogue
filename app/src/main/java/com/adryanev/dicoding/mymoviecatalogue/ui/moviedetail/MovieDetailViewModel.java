package com.adryanev.dicoding.mymoviecatalogue.ui.moviedetail;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.adryanev.dicoding.mymoviecatalogue.data.AppRepository;
import com.adryanev.dicoding.mymoviecatalogue.data.rest.response.ResponseMovie;

import java.util.List;

import okhttp3.Response;

/**
 * Project: MyMovieCatalogue
 *
 * @author Adryan Eka Vandra <adryanekavandra@gmail.com>
 * Date: 12/22/2018
 * Time: 3:15 PM
 */
public class MovieDetailViewModel extends AndroidViewModel {
    AppRepository repository;
    MutableLiveData<ResponseMovie> data;
    public MovieDetailViewModel(@NonNull Application application) {
        super(application);
        repository = new AppRepository(application);
    }

    public MutableLiveData<ResponseMovie> getMovie(String id){
        data = repository.getMovie(id);
        return data;
    }
}
