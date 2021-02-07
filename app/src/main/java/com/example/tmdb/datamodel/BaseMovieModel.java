package com.example.tmdb.datamodel;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "movies")
public class BaseMovieModel implements Serializable {
    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    String url;
    @ColumnInfo(name = "original_title")
    @SerializedName("original_title")
    String name;
    @PrimaryKey
    @SerializedName("id")
    @ColumnInfo(name = "id")
    int id;

    @ColumnInfo(name = "isBookMarked")
    boolean isMarked;

    @ColumnInfo(name = "isTrending")
    boolean isTrending;

    @ColumnInfo(name = "isNowPlaying")
    boolean isNowPlaying;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isMarked() {
        return isMarked;
    }

    public void setMarked(boolean marked) {
        isMarked = marked;
    }

    public boolean isTrending() {
        return isTrending;
    }

    public void setTrending(boolean trending) {
        isTrending = trending;
    }

    public boolean isNowPlaying() {
        return isNowPlaying;
    }

    public void setNowPlaying(boolean nowPlaying) {
        isNowPlaying = nowPlaying;
    }
}