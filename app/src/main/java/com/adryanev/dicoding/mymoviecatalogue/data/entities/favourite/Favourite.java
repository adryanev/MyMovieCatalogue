package com.adryanev.dicoding.mymoviecatalogue.data.entities.favourite;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.adryanev.dicoding.mymoviecatalogue.config.Config;
import com.adryanev.dicoding.mymoviecatalogue.data.DatabaseContract;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = Config.Database.TABLE_FAVOURITE)
public class Favourite implements Parcelable {

    @PrimaryKey
    @ColumnInfo(name = Config.Database.KEY_ID)
    private Integer id;
    @ColumnInfo(name = Config.Database.KEY_TITLE)
    private String title;
    @ColumnInfo(name = Config.Database.KEY_RELEASE_DATE)
    private String releaseDate;
    @ColumnInfo(name = Config.Database.KEY_POSTER)
    private String poster;

    public Favourite() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public static Favourite fromContentValues(ContentValues values){
        final Favourite favourite = new Favourite();
        if(values.containsKey(Config.Database.KEY_ID)) favourite.setId(values.getAsInteger(Config.Database.KEY_ID));
        if(values.containsKey(Config.Database.KEY_POSTER)) favourite.setPoster(values.getAsString(Config.Database.KEY_POSTER));
        if(values.containsKey(Config.Database.KEY_TITLE)) favourite.setTitle(values.getAsString(Config.Database.KEY_TITLE));
        if(values.containsKey(Config.Database.KEY_RELEASE_DATE)) favourite.setTitle(values.getAsString(Config.Database.KEY_RELEASE_DATE));

        return favourite;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.title);
        dest.writeString(this.releaseDate);
        dest.writeString(this.poster);
    }

    protected Favourite(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.title = in.readString();
        this.releaseDate = in.readString();
        this.poster = in.readString();
    }

    public static final Creator<Favourite> CREATOR = new Creator<Favourite>() {
        @Override
        public Favourite createFromParcel(Parcel source) {
            return new Favourite(source);
        }

        @Override
        public Favourite[] newArray(int size) {
            return new Favourite[size];
        }
    };
    public Favourite(Cursor cursor){
        this.id = DatabaseContract.getColumnInt(cursor,Config.Database.KEY_ID);
        this.poster = DatabaseContract.getColumnString(cursor,DatabaseContract.FavouriteColumns.KEY_POSTER);
        this.title = DatabaseContract.getColumnString(cursor,DatabaseContract.FavouriteColumns.KEY_TITLE);
        this.releaseDate = DatabaseContract.getColumnString(cursor,DatabaseContract.FavouriteColumns.KEY_RELEASE_DATE);
    }
}
