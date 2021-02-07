package com.example.tmdb.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.tmdb.contracts.IMovieDetailRepository;
import com.example.tmdb.datamodel.MovieDetailModel;
import com.example.tmdb.db.MoviesDao;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MovieDetailVM extends AndroidViewModel {

    private final IMovieDetailRepository movieRepository;
    private final MediatorLiveData<MovieDetailModel> movieDetailModelLiveData = new MediatorLiveData<>();
    private final MediatorLiveData<Boolean> bookMarkStatusLiveData = new MediatorLiveData<>();
    private String imageUrl = "";

    @Inject
    public MovieDetailVM(@NonNull Application application, IMovieDetailRepository movieRepository, MoviesDao bookMarkDao) {
        super(application);
        this.movieRepository = movieRepository;
    }

    public void fetchMovieDetails(int movieId, String imageUrl) {
        movieDetailModelLiveData.addSource(movieRepository.getMovieDetails(movieId), movieDetailModelLiveData::setValue);
        //mark unmark livedata
        bookMarkStatusLiveData.addSource(movieRepository.isMovieBookMarked(movieId), bookMarkStatusLiveData::postValue);
        this.imageUrl = imageUrl;
    }

    public MutableLiveData<MovieDetailModel> getMovieDetailModelLiveData() {
        return movieDetailModelLiveData;
    }

    public void markModel() {
        if (movieDetailModelLiveData.getValue() != null) {
            movieRepository.bookMarkMovie(movieDetailModelLiveData.getValue().getId(), imageUrl);
        }
    }

    public LiveData<Boolean> getBookMarkStatusLiveData() {
        return bookMarkStatusLiveData;
    }
}
