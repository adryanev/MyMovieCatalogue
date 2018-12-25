package com.adryanev.dicoding.mymoviecatalogue.ui.main.popular;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adryanev.dicoding.mymoviecatalogue.R;
import com.adryanev.dicoding.mymoviecatalogue.data.entities.search.Search;
import com.adryanev.dicoding.mymoviecatalogue.data.rest.ApiInterface;
import com.adryanev.dicoding.mymoviecatalogue.data.rest.response.ResponseSearch;
import com.adryanev.dicoding.mymoviecatalogue.utils.ItemClickSupport;
import com.adryanev.dicoding.mymoviecatalogue.utils.RetrofitClient;
import com.adryanev.dicoding.mymoviecatalogue.ui.ScrollChildLayout;
import com.adryanev.dicoding.mymoviecatalogue.adapters.PopularAdapter;
import com.adryanev.dicoding.mymoviecatalogue.ui.moviedetail.MovieDetailActivity;
import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class PopularFragment extends Fragment {

    RecyclerView recyclerView;
    ScrollChildLayout scrollChildLayout;
    PopularAdapter adapter;
    PopularViewModel viewModel;
    ShimmerFrameLayout shimmerFrameLayout;

    public PopularFragment() {
        // Requires empty public constructor
    }

    public static PopularFragment newInstance() {
        return new PopularFragment();
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void prepareData() { ;
        viewModel.getPopular().observe(getActivity(), new Observer<List<Search>>() {
            @Override
            public void onChanged(List<Search> searches) {
                adapter.setSearches(searches);
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_popular,container,false);
        shimmerFrameLayout = view.findViewById(R.id.popular_shimmer);

        recyclerView = view.findViewById(R.id.rv_main);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),2);
        recyclerView.setLayoutManager(layoutManager);
        scrollChildLayout = view.findViewById(R.id.refreshLayout);
        adapter = new PopularAdapter(getContext());
        recyclerView.setAdapter(adapter);
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View view) {
                Intent i = new Intent(getActivity(),MovieDetailActivity.class);
                i.putExtra("movie_id",adapter.getSearches().get(position).getId().toString());
                startActivity(i);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        shimmerFrameLayout.startShimmer();

        prepareData();
        scrollChildLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getActivity().recreate();
                scrollChildLayout.setRefreshing(false);

            }
        });


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(PopularViewModel.class);
    }
}
