package com.adryanev.dicoding.mymoviecatalogue.ui.main.now_playing;

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
 * Time: 2:15 PM
 */
public class NowPlayingViewModel extends AndroidViewModel {
    AppRepository repository;
    MutableLiveData<List<Result>> data;
    public NowPlayingViewModel(@NonNull Application application) {
        super(application);
        repository = new AppRepository(application);
    }
    public MutableLiveData<List<Result>> getNowPlaying(int pages){
        data = repository.getNowPlaying(pages);
        return data;
    }
}
