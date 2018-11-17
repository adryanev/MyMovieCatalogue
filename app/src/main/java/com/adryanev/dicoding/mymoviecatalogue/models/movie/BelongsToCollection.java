package com.adryanev.dicoding.mymoviecatalogue.models.movie;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BelongsToCollection {
    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("poster_path")
    @Expose
    private String posterPath;

    public void setBackdropPath(String backdropPath){
        this.backdropPath = backdropPath;
    }

    public String getBackdropPath(){
        return backdropPath;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }

    public void setPosterPath(String posterPath){
        this.posterPath = posterPath;
    }

    public String getPosterPath(){
        return posterPath;
    }

}
