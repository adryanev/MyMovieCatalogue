package com.adryanev.dicoding.mymoviecatalogue.data;

import android.content.Context;

import com.adryanev.dicoding.mymoviecatalogue.config.Config;
import com.adryanev.dicoding.mymoviecatalogue.data.dao.FavouriteDao;
import com.adryanev.dicoding.mymoviecatalogue.data.entities.favourite.Favourite;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import timber.log.Timber;

@Database(entities = {Favourite.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract FavouriteDao favouriteDao();
    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(Context context){
        if(INSTANCE == null){
            synchronized (AppDatabase.class){
                if(INSTANCE == null){
                    Timber.d("Creating Database");
                    INSTANCE = buildDatabase(context);
                }
            }
        }
        Timber.d("Database INSTANCE created");
        return INSTANCE;
    }

    private static AppDatabase buildDatabase(Context context) {
        Timber.d("Building room database");
        return Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,Config.Database.DB_NAME)
                .allowMainThreadQueries().fallbackToDestructiveMigration().addCallback(roomCallback).build();
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Timber.d("Creating database connection");
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            Timber.d("Database Connection is open");
        }
    };


}
