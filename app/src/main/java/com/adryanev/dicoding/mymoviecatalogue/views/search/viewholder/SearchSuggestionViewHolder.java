package com.adryanev.dicoding.mymoviecatalogue.views.search.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.adryanev.dicoding.mymoviecatalogue.R;
import com.adryanev.dicoding.mymoviecatalogue.config.Config;
import com.adryanev.dicoding.mymoviecatalogue.data.entities.search.Search;
import com.adryanev.dicoding.mymoviecatalogue.views.search.adapter.SearchAdapter;
import com.squareup.picasso.Picasso;

public class SearchSuggestionViewHolder extends RecyclerView.ViewHolder {
    public ImageView backprop;
    public TextView title;
    public TextView year;
    private View.OnClickListener onItemClickListener;
    public SearchSuggestionViewHolder(@NonNull View itemView) {
        super(itemView);
        backprop = (ImageView) itemView.findViewById(R.id.search_backprop);
        title = (TextView) itemView.findViewById(R.id.search_title);
        year = (TextView) itemView.findViewById(R.id.search_year);

    }
    public void bind(final Search item, final SearchAdapter.OnItemClickListener listener) {
        title.setText(item.getTitle());
        year.setText(item.getReleaseDate());
        Picasso.get().load(Config.IMAGE_W185+item.getBackdropPath()).into(backprop);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(item);
            }
        });
    }
}
