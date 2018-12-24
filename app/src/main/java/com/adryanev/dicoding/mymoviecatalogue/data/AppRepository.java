package com.adryanev.dicoding.mymoviecatalogue.data;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.adryanev.dicoding.mymoviecatalogue.data.dao.FavouriteDao;
import com.adryanev.dicoding.mymoviecatalogue.data.entities.favourite.Favourite;
import com.adryanev.dicoding.mymoviecatalogue.data.entities.search.Search;
import com.adryanev.dicoding.mymoviecatalogue.data.entities.upcoming.Result;
import com.adryanev.dicoding.mymoviecatalogue.data.rest.ApiInterface;
import com.adryanev.dicoding.mymoviecatalogue.data.entities.movie.Movie;
import com.adryanev.dicoding.mymoviecatalogue.data.rest.response.ResponseSearch;
import com.adryanev.dicoding.mymoviecatalogue.data.rest.response.ResponseUpcoming;
import com.adryanev.dicoding.mymoviecatalogue.utils.AppExecutors;
import com.adryanev.dicoding.mymoviecatalogue.utils.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Project: MyMovieCatalogue
 *
 * @author Adryan Eka Vandra <adryanekavandra@gmail.com>
 * Date: 12/22/2018
 * Time: 4:41 AM
 */
public class AppRepository {

    AppExecutors executors;
    private FavouriteDao favouriteDao;
    LiveData<List<Favourite>> favouriteList;
    private ApiInterface service;
    private static AppRepository INSTANCE;
    public AppRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        favouriteDao = db.favouriteDao();
        service = RetrofitClient.getClient().create(ApiInterface.class);
        executors = new AppExecutors();
    }
   public MutableLiveData<List<Result>> getUpcoming(int pages){
       final MutableLiveData<List<Result>> data = new MutableLiveData<>();
       service.getUpcomingMovies(pages).enqueue(new Callback<ResponseUpcoming>() {
           @Override
           public void onResponse(Call<ResponseUpcoming> call, Response<ResponseUpcoming> response) {
                   Timber.d("Sukses mendapatkan data upcoming");
                   data.setValue(response.body().getResults());

           }

           @Override
           public void onFailure(Call<ResponseUpcoming> call, Throwable t) {
                Timber.e(t);
           }
       });
       return data;
   }

   public MutableLiveData<List<Result>> getNowPlaying(int page){
        final MutableLiveData<List<Result>> data = new MutableLiveData<>();
        service.getNowPlaying(page).enqueue(new Callback<ResponseUpcoming>() {
            @Override
            public void onResponse(Call<ResponseUpcoming> call, Response<ResponseUpcoming> response) {
                    Timber.d("Sukses mendapatkan data now playing");
                    data.setValue(response.body().getResults());

            }

            @Override
            public void onFailure(Call<ResponseUpcoming> call, Throwable t) {
                Timber.e(t);
            }
        });
        return data;
   }
   public MutableLiveData<List<Search>> getPopular(int page){
        final MutableLiveData<List<Search>> data  = new MutableLiveData<>();
        service.getPopularMovies(page).enqueue(new Callback<ResponseSearch>() {
            @Override
            public void onResponse(Call<ResponseSearch> call, Response<ResponseSearch> response) {
                Timber.d("Sukses mendapatkan data popular");
                data.setValue(response.body().getResults());
            }

            @Override
            public void onFailure(Call<ResponseSearch> call, Throwable t) {
                Timber.e(t);
            }
        });
        return data;
   }

   public MutableLiveData<List<Search>> searchMovie(final String query, int page){
        final MutableLiveData<List<Search>> data = new MutableLiveData<>();
        service.searchMovies(page,query).enqueue(new Callback<ResponseSearch>() {
            @Override
                public void onResponse(Call<ResponseSearch> call, Response<ResponseSearch> response) {
                Timber.d("Sukses melakukan pencarian: %s",query);
                if (response.body() != null) {
                    data.setValue(response.body().getResults());
                }



            }

            @Override
            public void onFailure(Call<ResponseSearch> call, Throwable t) {
                Timber.e(t);
            }
        });
        return data;
   }

   public MutableLiveData<Movie> getMovie(final String id){
        final MutableLiveData<Movie> data = new MutableLiveData<>();
        service.getMovie(id).enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                    Timber.d("Sukses mendapatkan movie: %s",id);
                    data.setValue(response.body());

            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Timber.e(t);
            }
        });
        return data;
   }

   public LiveData<List<Favourite>> getFavourite(){
        favouriteList = favouriteDao.getFavourites();
        return favouriteList;
   }

   public void insertFavourite(final Favourite favourite){
        Runnable insert = new Runnable() {
            @Override
            public void run() {
                favouriteDao.insert(favourite);
            }
        };
        executors.diskIO().execute(insert);
   }

   public void removeFavourite(final Favourite favourite){
        Runnable remove = new Runnable() {
            @Override
            public void run() {
                favouriteDao.delete(favourite.getId());
            }
        };
        executors.diskIO().execute(remove);
   }

   public LiveData<Favourite> searchFavourite(Integer id){
        LiveData<Favourite> fav = favouriteDao.searchFavourite(id);
        return fav;
   }


}
