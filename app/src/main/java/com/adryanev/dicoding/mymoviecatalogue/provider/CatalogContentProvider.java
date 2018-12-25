package com.adryanev.dicoding.mymoviecatalogue.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.widget.Switch;

import com.adryanev.dicoding.mymoviecatalogue.config.Config;
import com.adryanev.dicoding.mymoviecatalogue.data.AppDatabase;
import com.adryanev.dicoding.mymoviecatalogue.data.dao.FavouriteDao;

import java.util.regex.Matcher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import timber.log.Timber;

import static android.service.notification.Condition.SCHEME;
import static com.adryanev.dicoding.mymoviecatalogue.config.Config.Database.TABLE_FAVOURITE;

public class CatalogContentProvider extends ContentProvider {

    public static final String AUTHORITY = "com.adryanev.dicoding.mymoviecatalogue";
    public static final String SCHEME = "content";

    AppDatabase database;
    FavouriteDao favouriteDao;

    private static final int CODE_FAVOURITE_DIR = 1;

    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    // Base content yang digunakan untuk akses content provider
    public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_FAVOURITE)
            .build();
    static {
        MATCHER.addURI(AUTHORITY,TABLE_FAVOURITE,CODE_FAVOURITE_DIR);
    }
    @Override
    public boolean onCreate()
    {
        database = AppDatabase.getDatabase(getContext().getApplicationContext());
        favouriteDao = database.favouriteDao();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
       int code = MATCHER.match(uri);
        Cursor cursor;
        Timber.d("CatalogueContentProvider URI: %s , Status:  %d",uri.toString(),code);
       if(code == CODE_FAVOURITE_DIR){
           cursor = favouriteDao.getFavouritesAll();
           cursor.setNotificationUri(getContext().getContentResolver(), uri);
           return cursor;
       }
       throw new IllegalArgumentException("Unknown Uri: "+uri);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (MATCHER.match(uri)){
            case CODE_FAVOURITE_DIR:
                return "vnd.android.cursor.dir/"+AUTHORITY+"."+TABLE_FAVOURITE;

            default: throw new IllegalArgumentException("Unknown Uri: "+uri);

        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
