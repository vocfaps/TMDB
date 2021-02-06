package com.example.tmdb.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.tmdb.datamodel.TrendingMovieModel;

import java.util.List;

@Dao
public interface TrendingMoviesDao {

    @Query("SELECT * FROM trendingmovies")
    LiveData<List<TrendingMovieModel>> getAllTrendingMovies();

    @Transaction
    default void pushNewResults(List<TrendingMovieModel> list){
        nukeTable();
        insertAllTrendingModels(list);
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllTrendingModels(List<TrendingMovieModel> list);

    @Query("DELETE FROM trendingmovies")
    void nukeTable();

}
