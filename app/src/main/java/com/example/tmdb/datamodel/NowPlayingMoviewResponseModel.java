package com.example.tmdb.datamodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NowPlayingMoviewResponseModel {
    @SerializedName("page")
    int page;
    @SerializedName("results")
    List<NowPlayingMovieModel> results;

    public int getPage() {
        return page;
    }

    public List<NowPlayingMovieModel> getResults() {
        return results;
    }
}
