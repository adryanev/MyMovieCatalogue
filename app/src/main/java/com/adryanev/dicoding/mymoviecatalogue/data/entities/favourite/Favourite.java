package com.adryanev.dicoding.mymoviecatalogue.data.entities.favourite;

import android.os.Parcel;
import android.os.Parcelable;

import com.adryanev.dicoding.mymoviecatalogue.config.Config;

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
}
