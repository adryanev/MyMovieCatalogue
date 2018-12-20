package com.adryanev.dicoding.mymoviecatalogue.views.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adryanev.dicoding.mymoviecatalogue.R;
import com.adryanev.dicoding.mymoviecatalogue.data.entities.upcoming.Result;
import com.adryanev.dicoding.mymoviecatalogue.data.rest.ApiInterface;
import com.adryanev.dicoding.mymoviecatalogue.data.rest.response.ResponseUpcoming;
import com.adryanev.dicoding.mymoviecatalogue.utils.ItemClickSupport;
import com.adryanev.dicoding.mymoviecatalogue.utils.RetrofitClient;
import com.adryanev.dicoding.mymoviecatalogue.views.main.adapters.UpcomingAdapter;
import com.adryanev.dicoding.mymoviecatalogue.views.moviedetail.activity.MovieDetailActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class NowPlayingFragment extends Fragment {

    ApiInterface apiService;
    List<Result> movieList;
    RecyclerView recyclerView;
    NestedScrollView nestedScrollView;
    UpcomingAdapter upcomingAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiService = RetrofitClient.getClient().create(ApiInterface.class);
    }
    private void prepareData(){
        apiService.getNowPlaying(1).enqueue(new Callback<ResponseUpcoming>() {
            @Override
            public void onResponse(Call<ResponseUpcoming> call, Response<ResponseUpcoming> response) {
                movieList = response.body().getResults();
                upcomingAdapter = new UpcomingAdapter(getContext(),movieList);
                recyclerView.setAdapter(upcomingAdapter);
                ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View view) {
                        Timber.d("Item Clicked: "+movieList.get(position).getId().toString());
                        intentToDetail(movieList.get(position).getId().toString());
                    }
                });



            }

            @Override
            public void onFailure(Call<ResponseUpcoming> call, Throwable t) {
                Timber.e(t.getLocalizedMessage());

            }
        });
    }

    private void intentToDetail(String id) {
        Intent i = new Intent(getActivity(), MovieDetailActivity.class);
        i.putExtra("movie_id",id);
        startActivity(i);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upcoming,container,false);
        recyclerView = view.findViewById(R.id.upcoming_rv);
        nestedScrollView = view.findViewById(R.id.upcoming_scroll);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        prepareData();
    }
}
