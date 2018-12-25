package com.adryanev.dicoding.mymoviecatalogue.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.adryanev.dicoding.mymoviecatalogue.R;
import com.adryanev.dicoding.mymoviecatalogue.config.Config;
import com.adryanev.dicoding.mymoviecatalogue.data.entities.favourite.Favourite;
import com.adryanev.dicoding.mymoviecatalogue.ui.main.favourite.FavouriteViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;


public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteViewHolder> {
    private Context context;
    private List<Favourite> favouritesList;

    public FavouriteAdapter(Context context){
        this.context = context;
    }
    public List<Favourite> getFavouritesList() {
        return favouritesList;
    }

    public void setFavouritesList(List<Favourite> favouritesList) {
        this.favouritesList = favouritesList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavouriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_favourite,parent,false);
        return new FavouriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteViewHolder holder, int position) {

        holder.title.setText(favouritesList.get(position).getTitle());
        holder.tahun.setText(favouritesList.get(position).getReleaseDate());
        Picasso.get().load(Config.IMAGE_W185+favouritesList.get(position).getPoster()).into(holder.poster);
    }

    @Override
    public int getItemCount() {
        return favouritesList !=null? favouritesList.size() : 0;
    }
}
