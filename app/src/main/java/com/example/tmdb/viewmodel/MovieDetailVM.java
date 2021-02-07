package com.example.tmdb.viewmodel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.tmdb.contracts.IMovieDetailRepository;
import com.example.tmdb.datamodel.MovieDetailModel;
import com.example.tmdb.db.MoviesDao;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MovieDetailVM extends AndroidViewModel {

    private final IMovieDetailRepository movieRepository;
    private final MutableLiveData<MovieDetailModel> movieDetailModel = new MutableLiveData<>();
    private final MoviesDao bookMarkDao;
    private final MediatorLiveData<Boolean> bookMarkStatusLiveData = new MediatorLiveData<>();

    @Inject
    public MovieDetailVM(@NonNull Application application, IMovieDetailRepository movieRepository, MoviesDao bookMarkDao) {
        super(application);
        this.movieRepository = movieRepository;
        this.bookMarkDao = bookMarkDao;

        //mark unmark livedata
        bookMarkStatusLiveData.addSource(Transformations.switchMap(movieDetailModel, input -> {
            MediatorLiveData<Boolean> isBookMarked = new MediatorLiveData<>();
            AsyncTask.SERIAL_EXECUTOR.execute(() -> isBookMarked.addSource(bookMarkDao.isBookMarked(input.getId()), isBookMarked::setValue));
            return isBookMarked;
        }), bookMarkStatusLiveData::setValue);
    }

    public void fetchMovieDetails(int movieId) {
        movieRepository.getMovieDetails(movieId);
    }

    public MutableLiveData<MovieDetailModel> getMovieDetailModel() {
        return movieDetailModel;
    }

    public void markModel() {
        if (movieDetailModel.getValue() != null) {
            AsyncTask.SERIAL_EXECUTOR.execute(() ->
                    bookMarkDao.markUnmarkBook(movieDetailModel.getValue().getId())
            );
        }
    }

    public LiveData<Boolean> getBookMarkStatusLiveData() {
        return bookMarkStatusLiveData;
    }
}
