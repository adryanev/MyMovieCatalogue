package com.adryanev.dicoding.mymoviecatalogue.ui.main.favourite;

import android.view.View;
import android.widget.TextView;

import com.adryanev.dicoding.mymoviecatalogue.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class FavouriteViewHolder extends RecyclerView.ViewHolder {

    public CircleImageView poster;
    public TextView title, tahun;
    public FavouriteViewHolder(@NonNull View itemView) {
        super(itemView);
        poster = itemView.findViewById(R.id.favourite_poster);
        title = itemView.findViewById(R.id.favourite_judul);
        tahun = itemView.findViewById(R.id.favourite_tahun);
    }
}
