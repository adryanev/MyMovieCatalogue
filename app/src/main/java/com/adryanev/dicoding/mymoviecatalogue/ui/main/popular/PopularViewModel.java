package com.adryanev.dicoding.mymoviecatalogue.ui.main.popular;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.adryanev.dicoding.mymoviecatalogue.data.AppRepository;
import com.adryanev.dicoding.mymoviecatalogue.data.entities.search.Search;
import com.adryanev.dicoding.mymoviecatalogue.data.entities.upcoming.Result;

import java.util.List;

/**
 * Project: MyMovieCatalogue
 *
 * @author Adryan Eka Vandra <adryanekavandra@gmail.com>
 * Date: 12/22/2018
 * Time: 2:15 PM
 */
public class PopularViewModel extends AndroidViewModel {
    AppRepository repository;
    MutableLiveData<List<Search>> data;
    public PopularViewModel(@NonNull Application application) {
        super(application);
        repository = new AppRepository(application);
        data = repository.getPopular(1);

    }
    public MutableLiveData<List<Search>> getPopular(){
        return data;
    }
}
