package com.adryanev.dicoding.mymoviecatalogue.ui.search;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.adryanev.dicoding.mymoviecatalogue.R;
import com.adryanev.dicoding.mymoviecatalogue.config.Config;
import com.adryanev.dicoding.mymoviecatalogue.data.entities.search.Search;
import com.adryanev.dicoding.mymoviecatalogue.adapters.SearchAdapter;
import com.squareup.picasso.Picasso;

public class SearchSuggestionViewHolder extends RecyclerView.ViewHolder {
    public CircleImageView backprop;
    public TextView title;
    public TextView year;
    public SearchSuggestionViewHolder(@NonNull View itemView) {
        super(itemView);
        backprop = itemView.findViewById(R.id.search_backprop);
        title = itemView.findViewById(R.id.search_title);
        year = itemView.findViewById(R.id.search_year);

    }
    public void bind(final Search item) {
        title.setText(item.getTitle());
        year.setText(item.getReleaseDate());
        Picasso.get().load(Config.IMAGE_W185+item.getPosterPath()).into(backprop);
    }
}
