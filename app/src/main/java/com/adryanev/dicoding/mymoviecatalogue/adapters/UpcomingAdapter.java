package com.adryanev.dicoding.mymoviecatalogue.adapters;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adryanev.dicoding.mymoviecatalogue.R;
import com.adryanev.dicoding.mymoviecatalogue.config.Config;
import com.adryanev.dicoding.mymoviecatalogue.data.entities.upcoming.Result;
import com.adryanev.dicoding.mymoviecatalogue.ui.main.upcoming.UpcomingViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UpcomingAdapter extends RecyclerView.Adapter<UpcomingViewHolder> {

    Context context;
    List<Result> movieList;
    public UpcomingAdapter(Context context){
        this.context = context;
    }

    public void setMovieList(List<Result> movieList) {
        this.movieList = movieList;
        notifyDataSetChanged();
    }

    public List<Result> getMovieList() {
        return movieList;
    }

    @NonNull
    @Override
    public UpcomingViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_upcoming,viewGroup,false);
       return new UpcomingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UpcomingViewHolder upcomingViewHolder, int i) {
        upcomingViewHolder.upcomingJudul.setText(movieList.get(i).getTitle());
        upcomingViewHolder.upcomingDate.setText(movieList.get(i).getReleaseDate());
        Picasso.get().load(Config.IMAGE_W500+movieList.get(i).getPosterPath()).into(upcomingViewHolder.upcomingBackdrop);
        String id = movieList.get(i).getId().toString();
        String title = movieList.get(i).getTitle();
        String replaced = title.replace(' ','-');

        final String url = Config.URL_MOVIE+id+"-"+replaced;
        upcomingViewHolder.upcomingShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT,url);
                context.startActivity(Intent.createChooser(i,context.getResources().getString(R.string.share_link)));
            }
        });

    }

    @Override
    public int getItemCount() {
        return movieList !=null? movieList.size() : 0 ;
    }
}
