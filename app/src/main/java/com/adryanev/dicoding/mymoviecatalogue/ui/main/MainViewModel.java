package com.adryanev.dicoding.mymoviecatalogue.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.adryanev.dicoding.mymoviecatalogue.data.AppRepository;
import com.adryanev.dicoding.mymoviecatalogue.data.entities.search.Search;
import com.adryanev.dicoding.mymoviecatalogue.data.rest.response.ResponseSearch;

import java.util.List;

import timber.log.Timber;

/**
 * Project: MyMovieCatalogue
 *
 * @author Adryan Eka Vandra <adryanekavandra@gmail.com>
 * Date: 12/22/2018
 * Time: 4:40 AM
 */
public class MainViewModel extends AndroidViewModel {

    MutableLiveData<List<Search>> data;
    AppRepository repository;
    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = new AppRepository(application);
    }

    public MutableLiveData<List<Search>> search(String query, int page){
        data = repository.searchMovie(query,page);
        return data;
    }


}
