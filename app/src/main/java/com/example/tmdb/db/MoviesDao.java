package com.example.tmdb.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.tmdb.datamodel.BaseMovieModel;
import com.example.tmdb.logger.LoggerUtil;

import java.util.ArrayList;
import java.util.List;

@Dao
public abstract class MoviesDao extends BaseDao<BaseMovieModel>{

    @Query("SELECT * FROM movies WHERE isTrending = 1")
    public abstract LiveData<List<BaseMovieModel>> getAllTrendingMovies();

    @Transaction
    public void pushNewTrendingResults(List<BaseMovieModel> list){
        //unset all movies that are not trending anymore.
        //update still the trending movies and add the new ones
        //delete all non trending, non playing, non bookmarked movies
        List<Integer> newTrendingIdList = new ArrayList<>();
        for(BaseMovieModel movieModel : list){
            newTrendingIdList.add(movieModel.getId());
        }
        unflagOldTrendingMovies(newTrendingIdList);

        for(BaseMovieModel trendingMovie : list){
            trendingMovie.setTrending(true); //This movie is trending....
        }
        insertOrUpdate(list);
        deleteStaleData();

        //updated DB
        LoggerUtil.logBug("updated DB for trending movies ");
    }

    @Query("DELETE FROM movies WHERE isTrending = 0 AND isBookMarked = 0 AND isNowPlaying = 0")
    public abstract void deleteStaleData();

    @Query("SELECT * FROM movies WHERE isNowPlaying = 1")
    public abstract LiveData<List<BaseMovieModel>> getAllNowMovies();

    @Transaction
    public void pushNewPlayingResults(List<BaseMovieModel> list){
        //unset all movies that are not playing anymore.
        //update still the playing movies and add the new ones
        //delete all non trending, non playing, non bookmarked movies
        List<Integer> newTrendingIdList = new ArrayList<>();
        for(BaseMovieModel movieModel : list){
            newTrendingIdList.add(movieModel.getId());
        }
        unflagOldPlayingMovies(newTrendingIdList);

        for(BaseMovieModel trendingMovie : list){
            trendingMovie.setNowPlaying(true); //This movie is trending....
        }
        insertOrUpdate(list);
        deleteStaleData();

        //updated DB
        LoggerUtil.logBug("updated DB for new playing movies ");
    }

    @Query("UPDATE movies SET isNowPlaying = 0 WHERE id IN (SELECT id FROM movies WHERE id NOT IN (:newTrendingIdList) AND isNowPlaying = 1)")
    public abstract void unflagOldPlayingMovies(List<Integer> newTrendingIdList);

    @Query("SELECT isBookMarked FROM movies WHERE id = :movieId")
    public abstract LiveData<Boolean> isBookMarked(int movieId);

    @Transaction
    public void markUnmarkBook(int movieId){
        updateTable(movieId);
    }

    @Query("UPDATE movies SET isBookMarked = (NOT isBookMarked) WHERE id = :movieId")
    public abstract void updateTable(int movieId);

    @Query("UPDATE movies SET isTrending = 0 WHERE id IN (SELECT id FROM movies WHERE id NOT IN (:list) AND isTrending = 1)")
    public abstract void unflagOldTrendingMovies(List<Integer> list);

}
