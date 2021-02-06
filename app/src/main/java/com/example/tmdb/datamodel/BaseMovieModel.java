package com.example.tmdb.datamodel;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

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
}
