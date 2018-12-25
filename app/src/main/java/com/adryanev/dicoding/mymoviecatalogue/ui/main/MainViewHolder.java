package com.adryanev.dicoding.mymoviecatalogue.ui.main;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.adryanev.dicoding.mymoviecatalogue.R;
import com.adryanev.dicoding.mymoviecatalogue.config.Config;
import com.adryanev.dicoding.mymoviecatalogue.data.entities.search.Search;
import com.adryanev.dicoding.mymoviecatalogue.adapters.PopularAdapter;
import com.squareup.picasso.Picasso;

public class MainViewHolder extends RecyclerView.ViewHolder{

    public ImageView backprop;
    public TextView title;
    public TextView year;

    public MainViewHolder(@NonNull View itemView) {
        super(itemView);
        backprop = itemView.findViewById(R.id.imageView);
        title =  itemView.findViewById(R.id.item_title);
        year = itemView.findViewById(R.id.item_year);

    }
    public void bind(final Search item) {
        title.setText(item.getTitle());
        year.setText(item.getReleaseDate());
        Picasso.get().load(Config.IMAGE_W342+item.getPosterPath()).into(backprop);
    }

}
