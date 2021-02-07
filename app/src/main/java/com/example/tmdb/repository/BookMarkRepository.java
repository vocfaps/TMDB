package com.example.tmdb.repository;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.example.tmdb.contracts.IBookMarkMovieRepository;
import com.example.tmdb.datamodel.BaseMovieModel;
import com.example.tmdb.db.MoviesDao;

import java.util.List;

import javax.inject.Inject;

public class BookMarkRepository implements IBookMarkMovieRepository {
    MoviesDao moviesDao;

    public @Inject BookMarkRepository(MoviesDao moviesDao) {
        this.moviesDao = moviesDao;
    }

    @Override
    public LiveData<List<BaseMovieModel>> fetchMarkedMovies() {
        MediatorLiveData<List<BaseMovieModel>> model = new MediatorLiveData<>();
        AsyncTask.SERIAL_EXECUTOR.execute(() -> model.addSource(moviesDao.getMarkedMovies(), model::postValue));
        return model;
    }
}
