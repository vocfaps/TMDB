package com.example.tmdb.contracts;


import androidx.lifecycle.LiveData;

import com.example.tmdb.datamodel.BaseMovieModel;

import java.util.List;

public interface IBookMarkMovieRepository {
    LiveData<List<BaseMovieModel>> fetchMarkedMovies();
}
