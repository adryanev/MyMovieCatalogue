package com.adryanev.dicoding.mymoviecatalogue.ui.main.favourite;

import android.app.Application;

import com.adryanev.dicoding.mymoviecatalogue.data.AppRepository;
import com.adryanev.dicoding.mymoviecatalogue.data.entities.favourite.Favourite;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class FavouriteViewModel extends AndroidViewModel {

    AppRepository repository;
    LiveData<List<Favourite>> dataFavourite;
    public FavouriteViewModel(@NonNull Application application) {
        super(application);
        repository = new AppRepository(application);
        dataFavourite = repository.getFavourite();

    }

    public LiveData<List<Favourite>> getDataFavourite() {
        return dataFavourite;
    }

    public void insertFavourite(Favourite favourite){
        repository.insertFavourite(favourite);
    }

    public void removeFavourite(Favourite fav){
        repository.removeFavourite(fav);
    }

}
