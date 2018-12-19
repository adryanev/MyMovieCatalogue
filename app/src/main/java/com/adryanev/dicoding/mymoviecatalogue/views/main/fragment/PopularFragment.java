package com.adryanev.dicoding.mymoviecatalogue.views.main.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adryanev.dicoding.mymoviecatalogue.R;
import com.adryanev.dicoding.mymoviecatalogue.data.entities.search.Search;
import com.adryanev.dicoding.mymoviecatalogue.data.rest.ApiInterface;
import com.adryanev.dicoding.mymoviecatalogue.data.rest.response.ResponseSearch;
import com.adryanev.dicoding.mymoviecatalogue.utils.RetrofitClient;
import com.adryanev.dicoding.mymoviecatalogue.views.ScrollChildLayout;
import com.adryanev.dicoding.mymoviecatalogue.views.main.adapters.PopularAdapter;
import com.adryanev.dicoding.mymoviecatalogue.views.moviedetail.activity.MovieDetailActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class PopularFragment extends Fragment {

    ApiInterface apiService;
    List<Search> movieList;
    RecyclerView recyclerView;
    ScrollChildLayout scrollChildLayout;
    PopularAdapter adapter;

    public PopularFragment() {
        // Requires empty public constructor
    }

    public static PopularFragment newInstance() {
        return new PopularFragment();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiService = RetrofitClient.getClient().create(ApiInterface.class);

    }

    private void prepareData() {
        apiService.getPopularMovies(1).enqueue(new Callback<ResponseSearch>() {
            @Override
            public void onResponse(Call<ResponseSearch> call, Response<ResponseSearch> response) {
                movieList = response.body().getResults();
                adapter = new PopularAdapter(getContext(), movieList, new PopularAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Search item) {
                        Intent i = new Intent(getActivity(), MovieDetailActivity.class);
                        i.putExtra("movie_id",item.getId().toString());
                        startActivity(i);
                    }
                });
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ResponseSearch> call, Throwable t) {
                Timber.e(t.getLocalizedMessage());
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_popular,container,false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_main);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(layoutManager);
        scrollChildLayout = (ScrollChildLayout) view.findViewById(R.id.refreshLayout);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        prepareData();
        scrollChildLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getActivity().recreate();
                scrollChildLayout.setRefreshing(false);

            }
        });


    }

}
