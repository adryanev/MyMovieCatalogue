package com.adryanev.dicoding.mymoviecatalogue.ui.main.now_playing;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adryanev.dicoding.mymoviecatalogue.R;
import com.adryanev.dicoding.mymoviecatalogue.data.entities.upcoming.Result;
import com.adryanev.dicoding.mymoviecatalogue.data.rest.ApiInterface;
import com.adryanev.dicoding.mymoviecatalogue.data.rest.response.ResponseUpcoming;
import com.adryanev.dicoding.mymoviecatalogue.utils.ItemClickSupport;
import com.adryanev.dicoding.mymoviecatalogue.utils.RetrofitClient;
import com.adryanev.dicoding.mymoviecatalogue.adapters.UpcomingAdapter;
import com.adryanev.dicoding.mymoviecatalogue.ui.moviedetail.MovieDetailActivity;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;
import timber.log.Timber;

public class NowPlayingFragment extends Fragment {

    RecyclerView recyclerView;
    UpcomingAdapter upcomingAdapter;
    NowPlayingViewModel viewModel;
    ShimmerFrameLayout shimmerFrameLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    private void prepareData(){
        viewModel.getNowPlaying().observe(getActivity(), new Observer<List<Result>>() {
            @Override
            public void onChanged(List<Result> results) {
                upcomingAdapter.setMovieList(results);
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
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
        View view = inflater.inflate(R.layout.fragment_now_playing,container,false);
        shimmerFrameLayout = view.findViewById(R.id.now_playing_shimmer);
        recyclerView = view.findViewById(R.id.now_playing_rv);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        upcomingAdapter = new UpcomingAdapter(getContext());
        recyclerView.setAdapter(upcomingAdapter);
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View view) {
                Timber.d("Item Clicked: "+upcomingAdapter.getMovieList().get(position).getId().toString());
                intentToDetail(upcomingAdapter.getMovieList().get(position).getId().toString());
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        shimmerFrameLayout.startShimmer();
        prepareData();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(NowPlayingViewModel.class);
    }
}
