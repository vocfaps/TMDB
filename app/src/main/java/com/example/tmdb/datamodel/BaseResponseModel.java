package com.example.tmdb.datamodel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class BaseResponseModel implements Serializable {
    @SerializedName("page")
    int page;

    public int getPage() {
        return page;
    }

    public List<BaseMovieModel> getResults() {
        return results;
    }

    @SerializedName("results")
    List<BaseMovieModel> results;
}
