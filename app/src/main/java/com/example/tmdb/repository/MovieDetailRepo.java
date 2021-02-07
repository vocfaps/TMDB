package com.example.tmdb.repository;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tmdb.contracts.IMovieDetailRepository;
import com.example.tmdb.datamodel.MovieDetailModel;
import com.example.tmdb.db.MoviesDao;

import javax.inject.Inject;

import network.IMovieDetails;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailRepo implements IMovieDetailRepository {

    private IMovieDetails movieDetails;
    private MoviesDao moviesDao;
    @Inject
    public MovieDetailRepo(IMovieDetails movieDetails, MoviesDao moviesDao){
        this.movieDetails = movieDetails;
        this.moviesDao = moviesDao;
    }

    @Override
    public LiveData<MovieDetailModel> getMovieDetails(int movieId) {
        MutableLiveData<MovieDetailModel> liveData = new MutableLiveData<>();
        Call<MovieDetailModel> call = movieDetails.getMovieDetails(movieId);
        call.enqueue(new Callback<MovieDetailModel>() {
            @Override
            public void onResponse(Call<MovieDetailModel> call, Response<MovieDetailModel> response) {
                if (response.isSuccessful()){
                    liveData.setValue(response.body());
                }else{
                    liveData.setValue(null); //we can also create a good wrapper for handling edge cases and error cases.
                }
            }

            @Override
            public void onFailure(Call<MovieDetailModel> call, Throwable t) {
                liveData.setValue(null);
            }
        });
        return liveData;
    }

    @Override
    public void bookMarkMovie(int movieId) {
        AsyncTask.SERIAL_EXECUTOR.execute(() ->  moviesDao.markUnmarkBook(movieId));
    }
}
