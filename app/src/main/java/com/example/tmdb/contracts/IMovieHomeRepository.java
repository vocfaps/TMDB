package com.example.tmdb.contracts;

import androidx.lifecycle.LiveData;

import com.example.tmdb.datamodel.BaseMovieModel;

import java.util.List;

public interface IMovieHomeRepository {
    LiveData<List<BaseMovieModel>> fetchTrendingMovies();
    LiveData<List<BaseMovieModel>> fetchNowPLayingMovies();
    void updateTrendingMovies();
    void updateNowPlayingMovies();
}
