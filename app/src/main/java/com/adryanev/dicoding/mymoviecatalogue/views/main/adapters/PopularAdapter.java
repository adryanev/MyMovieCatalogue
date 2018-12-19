package com.adryanev.dicoding.mymoviecatalogue.views.main.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adryanev.dicoding.mymoviecatalogue.R;
import com.adryanev.dicoding.mymoviecatalogue.config.Config;
import com.adryanev.dicoding.mymoviecatalogue.data.entities.search.Search;
import com.adryanev.dicoding.mymoviecatalogue.utils.DateUtility;
import com.adryanev.dicoding.mymoviecatalogue.views.main.activities.MainActivity;
import com.adryanev.dicoding.mymoviecatalogue.views.main.adapters.viewholder.MainViewHolder;
import com.adryanev.dicoding.mymoviecatalogue.views.moviedetail.activity.MovieDetailActivity;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.util.List;

import timber.log.Timber;

public class PopularAdapter extends RecyclerView.Adapter<MainViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Search item);
    }
    OnItemClickListener listener;
    private Context ctx;
    private List<Search> searches;

    public PopularAdapter(Context context, List<Search> searches, OnItemClickListener listener) {
        this.ctx = context;
        this.searches = searches;
        this.listener = listener;
    }


    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
       View view = LayoutInflater.from(ctx).inflate(R.layout.movie_item,viewGroup,false);
       return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder mainViewHolder, final int i) {
        mainViewHolder.bind(searches.get(i),listener);


    }

    @Override
    public int getItemCount() {
        if(searches == null){
            return 0;
        }
        return searches.size();
    }
}
