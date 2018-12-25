package com.adryanev.dicoding.mymoviecatalogue.ui.main.favourite;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.adryanev.dicoding.mymoviecatalogue.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import timber.log.Timber;

import com.adryanev.dicoding.mymoviecatalogue.adapters.FavouriteAdapter;
import com.adryanev.dicoding.mymoviecatalogue.data.entities.favourite.Favourite;
import com.adryanev.dicoding.mymoviecatalogue.ui.main.favourite.FavouriteViewModel;
import com.adryanev.dicoding.mymoviecatalogue.ui.moviedetail.MovieDetailActivity;
import com.adryanev.dicoding.mymoviecatalogue.utils.ItemClickSupport;

import java.util.List;

public class FavouriteFragment extends Fragment {

    private FavouriteViewModel viewModel;
    RecyclerView recyclerView;
    FavouriteAdapter adapter;

    @Override
    public void onResume() {
        super.onResume();
        viewModel.getDataFavourite().observe(getViewLifecycleOwner(), new Observer<List<Favourite>>() {
            @Override
            public void onChanged(List<Favourite> favourites) {
                adapter.setFavouritesList(favourites);
                Timber.d("Favourite Adapter Size: %d",adapter.getItemCount());
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favourite,container,false);
        recyclerView = view.findViewById(R.id.favourite_rv);
        adapter = new FavouriteAdapter(getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View view) {
                Timber.d("Item Clicked: "+adapter.getFavouritesList().get(position).getId().toString());
                intentToDetail(adapter.getFavouritesList().get(position).getId().toString());
            }
        });
        return view;
    }

    private void intentToDetail(String s) {
        Intent i = new Intent(getActivity(), MovieDetailActivity.class);
        i.putExtra("movie_id",s);
        startActivity(i);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(FavouriteViewModel.class);
    }
}
