package com.example.tmdb.contracts;

import androidx.lifecycle.LiveData;

import com.example.tmdb.datamodel.NowPlayingMovieModel;
import com.example.tmdb.datamodel.TrendingMovieModel;

import java.util.List;

public interface IMovieHomeRepository {
    LiveData<List<TrendingMovieModel>> fetchTrendingMovies();
    LiveData<List<NowPlayingMovieModel>> fetchNowPLayingMovies();
}
