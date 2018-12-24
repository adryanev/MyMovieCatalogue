package com.adryanev.dicoding;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainAdapter extends CursorAdapter {
    Context context;

    public MainAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
        this.context = context;
    }

    public Cursor getCursor(){
         return super.getCursor();
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.content_main,parent, false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        if (cursor!=null){
            CircleImageView poster;
            TextView title,date;
            poster = view.findViewById(R.id.main_poster);
            title = view.findViewById(R.id.main_title);
            date = view.findViewById(R.id.date);

            Picasso.get().load(Config.IMAGE_W185+DatabaseContract.getColumnString(cursor,DatabaseContract.FavouriteColumns.KEY_POSTER)).into(poster);
            title.setText(DatabaseContract.getColumnString(cursor,DatabaseContract.FavouriteColumns.KEY_TITLE));
            date.setText(DatabaseContract.getColumnString(cursor,DatabaseContract.FavouriteColumns.KEY_RELEASE_DATE));



        }
    }
}
