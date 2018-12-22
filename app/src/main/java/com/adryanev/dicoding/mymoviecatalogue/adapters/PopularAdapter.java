package com.adryanev.dicoding.mymoviecatalogue.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adryanev.dicoding.mymoviecatalogue.R;
import com.adryanev.dicoding.mymoviecatalogue.data.entities.search.Search;
import com.adryanev.dicoding.mymoviecatalogue.ui.main.MainViewHolder;

import java.util.List;

public class PopularAdapter extends RecyclerView.Adapter<MainViewHolder> {


    private Context ctx;
    private List<Search> searches;

    public PopularAdapter(Context context) {
        this.ctx = context;
        this.searches = searches;
    }

    public List<Search> getSearches() {
        return searches;
    }

    public void setSearches(List<Search> searches) {
        this.searches = searches;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, final int i) {
       View view = LayoutInflater.from(ctx).inflate(R.layout.movie_item,viewGroup,false);
       return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder mainViewHolder, final int i) {
        mainViewHolder.bind(searches.get(i));


    }

    @Override
    public int getItemCount() {
        if(searches == null){
            return 0;
        }
        return searches.size();
    }
}
