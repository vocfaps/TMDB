package com.example.tmdb.datamodel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrendingMovieResponseModel {
    @SerializedName("page")
    int page;
    @SerializedName("results")
    List<TrendingMovieModel> results;

    public int getPage() {
        return page;
    }

    public List<TrendingMovieModel> getResults() {
        return results;
    }
}
