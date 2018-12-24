package com.adryanev.dicoding.mymoviecatalogue.data.dao;

import com.adryanev.dicoding.mymoviecatalogue.data.entities.favourite.Favourite;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface FavouriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Favourite favourite);

    @Query("DELETE FROM favourite WHERE id = :id")
    void delete(Integer id);

    @Query("DELETE FROM favourite")
    void deleteAll();

    @Query("SELECT * FROM favourite")
    LiveData<List<Favourite>> getFavourites();

    @Query("SELECT * FROM favourite WHERE id = :id")
    LiveData<Favourite> searchFavourite(Integer id);

}
