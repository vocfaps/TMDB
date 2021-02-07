package com.example.tmdb.contracts;

import androidx.lifecycle.LiveData;

import com.example.tmdb.datamodel.MovieDetailModel;

public interface IMovieDetailRepository {
    LiveData<MovieDetailModel> getMovieDetails(int movieId);
    void bookMarkMovie(int movieId, String imageUrl);
    LiveData<Boolean> isMovieBookMarked(int movieId);
}
