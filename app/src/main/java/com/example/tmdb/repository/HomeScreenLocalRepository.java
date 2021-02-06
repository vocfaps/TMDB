package com.example.tmdb.repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.example.tmdb.contracts.IMovieHomeRepository;
import com.example.tmdb.datamodel.NowPlayingMovieModel;
import com.example.tmdb.datamodel.TrendingMovieModel;
import com.example.tmdb.db.MovieDatabase;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.android.scopes.ViewModelScoped;

@ViewModelScoped
public class HomeScreenLocalRepository implements IMovieHomeRepository {

    private final MovieDatabase database;
    private final MediatorLiveData<List<TrendingMovieModel>> trendingliveData =  new MediatorLiveData<>();
    private final MediatorLiveData<List<NowPlayingMovieModel>> newMovieLiveData = new MediatorLiveData<>();

    private void getNewMovieDBData(MediatorLiveData<List<NowPlayingMovieModel>> newMovieLiveData) {
        newMovieLiveData.addSource(database.getPlayingMovieDao().getAllNewMovies(), newMovieLiveData::postValue);
    }

    @Inject public HomeScreenLocalRepository(@ApplicationContext Context context, MovieDatabase database){
        this.database = database;
    }

    @Override
    public LiveData<List<TrendingMovieModel>> fetchTrendingMovies() {
        //Start observing a livedata from DB.
        AsyncTask.SERIAL_EXECUTOR.execute(() -> getTrendingDBData(trendingliveData));
        return trendingliveData;
    }

    private void getTrendingDBData(MediatorLiveData<List<TrendingMovieModel>> liveData) {
        liveData.addSource(database.getMovieDao().getAllTrendingMovies(), liveData::postValue);
    }

    @Override
    public LiveData<List<NowPlayingMovieModel>> fetchNowPLayingMovies() {
        AsyncTask.SERIAL_EXECUTOR.execute(() -> getNewMovieDBData(newMovieLiveData));
        return newMovieLiveData;
    }
}
