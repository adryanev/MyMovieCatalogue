package com.adryanev.dicoding;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.MainViewHolder> {

    Cursor cursor;
    Context context;

    public MainRecyclerAdapter(Context context){
        this.context = context;
    }
    @NonNull
    @Override
    public MainRecyclerAdapter.MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.content_main,parent,false);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainRecyclerAdapter.MainViewHolder holder, int position) {

        if(cursor.moveToPosition(position)){
            holder.title.setText(DatabaseContract.getColumnString(cursor,DatabaseContract.FavouriteColumns.KEY_TITLE));
            holder.date.setText(DatabaseContract.getColumnString(cursor,DatabaseContract.FavouriteColumns.KEY_RELEASE_DATE));
            Picasso.get().load(Config.IMAGE_W185+DatabaseContract.getColumnString(cursor,DatabaseContract.FavouriteColumns.KEY_POSTER)).into(holder.poster);

        }
    }

    void setFavourite (Cursor cursor){
        this.cursor = cursor;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return cursor == null ?0: cursor.getCount();
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {
        TextView title, date;
        CircleImageView poster;
        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.main_title);
            date = itemView.findViewById(R.id.date);
            poster = itemView.findViewById(R.id.main_poster);
        }
    }
}
