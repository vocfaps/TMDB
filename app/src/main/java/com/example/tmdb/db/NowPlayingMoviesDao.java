package com.example.tmdb.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.tmdb.datamodel.NowPlayingMovieModel;

import java.util.List;

@Dao
public interface NowPlayingMoviesDao {

    @Query("SELECT * FROM nowplayingmovies")
    LiveData<List<NowPlayingMovieModel>> getAllNewMovies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNewMovies(List<NowPlayingMovieModel> list);

    @Query("DELETE FROM nowplayingmovies")
    void nukeTable();

    @Transaction
    default void pushNewResults(List<NowPlayingMovieModel> list){
        nukeTable();
        insertNewMovies(list);
    }

}
