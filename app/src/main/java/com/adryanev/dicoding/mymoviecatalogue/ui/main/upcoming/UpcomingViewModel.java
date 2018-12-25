package com.adryanev.dicoding.mymoviecatalogue.ui.main.upcoming;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.adryanev.dicoding.mymoviecatalogue.data.AppRepository;
import com.adryanev.dicoding.mymoviecatalogue.data.entities.upcoming.Result;

import java.util.List;

/**
 * Project: MyMovieCatalogue
 *
 * @author Adryan Eka Vandra <adryanekavandra@gmail.com>
 * Date: 12/22/2018
 * Time: 2:16 PM
 */
public class UpcomingViewModel extends AndroidViewModel {
    AppRepository repository;
    MutableLiveData<List<Result>> data;
    public UpcomingViewModel(@NonNull Application application) {
        super(application);
        repository = new AppRepository(application);
    }
    public MutableLiveData<List<Result>> getUpcoming(int pages){
        data = repository.getUpcoming(pages);
        return data;
    }
}
