package com.adryanev.dicoding.mymoviecatalogue.data;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.adryanev.dicoding.mymoviecatalogue.data.entities.search.Search;
import com.adryanev.dicoding.mymoviecatalogue.data.entities.upcoming.Result;
import com.adryanev.dicoding.mymoviecatalogue.data.rest.ApiInterface;
import com.adryanev.dicoding.mymoviecatalogue.data.rest.response.ResponseMovie;
import com.adryanev.dicoding.mymoviecatalogue.data.rest.response.ResponseSearch;
import com.adryanev.dicoding.mymoviecatalogue.data.rest.response.ResponseUpcoming;
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

    private ApiInterface service;
    private static AppRepository INSTANCE;
    public AppRepository(Application application){
        service = RetrofitClient.getClient().create(ApiInterface.class);
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

   public MutableLiveData<ResponseMovie> getMovie(final String id){
        final MutableLiveData<ResponseMovie> data = new MutableLiveData<>();
        service.getMovie(id).enqueue(new Callback<ResponseMovie>() {
            @Override
            public void onResponse(Call<ResponseMovie> call, Response<ResponseMovie> response) {
                    Timber.d("Sukses mendapatkan movie: %s",id);
                    data.setValue(response.body());

            }

            @Override
            public void onFailure(Call<ResponseMovie> call, Throwable t) {
                Timber.e(t);
            }
        });
        return data;
   }

}
