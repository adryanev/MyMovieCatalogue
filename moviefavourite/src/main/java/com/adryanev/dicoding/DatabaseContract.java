package com.adryanev.dicoding;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static final String AUTHORITY = "com.adryanev.dicoding.mymoviecatalogue";
    public static final String SCHEME = "content";


    /*
    Penggunaan Base Columns akan memudahkan dalam penggunaan suatu table
    Untuk id yang autoincrement sudah default ada di dalam kelas BaseColumns dengan nama field _ID
     */
    public static final class FavouriteColumns implements BaseColumns {
        public static final String DB_NAME = "catalogue_db";
        public static final String TABLE_FAVOURITE = "favourite";
        public static final String KEY_TITLE = "title";
        public static final String KEY_RELEASE_DATE= "release_date";
        public static final String KEY_POSTER = "poster";

        // Base content yang digunakan untuk akses content provider
        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_FAVOURITE)
                .build();

    }


    /*
    Digunakan untuk mempermudah akses data di dalam cursor dengan parameter nama column
    */
    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }
}
