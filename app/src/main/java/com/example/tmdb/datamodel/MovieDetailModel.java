package com.example.tmdb.datamodel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MovieDetailModel implements Serializable {

    @SerializedName("id")
    int id;

    @SerializedName("title")
    String title;

    @SerializedName("overview")
    String overview;

    @SerializedName("vote_average")
    Double vote_average;

    private boolean isMarked;

    public boolean isMarked() {
        return isMarked;
    }

    public void setMarked(boolean marked) {
        isMarked = marked;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public Double getVote_average() {
        return vote_average;
    }
}
