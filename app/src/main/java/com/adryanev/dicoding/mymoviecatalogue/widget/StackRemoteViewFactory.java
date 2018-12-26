package com.adryanev.dicoding.mymoviecatalogue.widget;

import android.appwidget.AppWidgetManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.adryanev.dicoding.mymoviecatalogue.R;
import com.adryanev.dicoding.mymoviecatalogue.config.Config;
import com.adryanev.dicoding.mymoviecatalogue.data.DatabaseContract;
import com.adryanev.dicoding.mymoviecatalogue.data.entities.favourite.Favourite;
import com.adryanev.dicoding.mymoviecatalogue.provider.CatalogContentProvider;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import timber.log.Timber;

public class StackRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {
    private List<Bitmap> mWidgetItems = new ArrayList<>();
    private List<Favourite> favouritesList = new ArrayList<>();
    private Context mContext;
    Cursor cursor;
    private int mAppWidgetId;

    public StackRemoteViewFactory(Context context, Intent intent) {
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
    }
    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        getFavouriteMovies(mContext);

        for(Favourite favourite: favouritesList){
            Bitmap b = null;
            try {
                b = Picasso.get().load(Config.IMAGE_W342+favourite.getPoster()).get();
                mWidgetItems.add(b);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }

    private void getFavouriteMovies(Context mContext) {
        final long identityToken = Binder.clearCallingIdentity();
        getContentResolver(mContext);
        Binder.clearCallingIdentity();

    }

    private void getContentResolver(Context context) {
        cursor =  context.getContentResolver().query(DatabaseContract.FavouriteColumns.CONTENT_URI,null,null,null,null);
        Timber.d(DatabaseContract.FavouriteColumns.CONTENT_URI.toString());
       while (cursor.moveToNext()){
           Timber.d("Data: %s",DatabaseContract.getColumnString(cursor,DatabaseContract.FavouriteColumns.KEY_TITLE));
        favouritesList.add(new Favourite(cursor));
       }
    }

    @Override
    public void onDestroy() {
        cursor.close();
    }

    @Override
    public int getCount() {
        return mWidgetItems.size();

    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        Timber.d(mWidgetItems.get(position).toString());
        rv.setImageViewBitmap(R.id.image_widget ,mWidgetItems.get(position));

        Bundle extras = new Bundle();
        extras.putInt(MyMovieCatalogueWidget.EXTRA_ITEM, position);
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent);
        rv.setTextViewText(R.id.tv_movie_title,favouritesList.get(position).getTitle());
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }


}
