package com.example.tmdb.repository;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tmdb.contracts.IMovieDetailRepository;
import com.example.tmdb.datamodel.BaseMovieModel;
import com.example.tmdb.datamodel.MovieDetailModel;
import com.example.tmdb.db.MoviesDao;

import javax.inject.Inject;

import network.IMovieDetails;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailRepo implements IMovieDetailRepository {

    private final IMovieDetails movieDetails;
    private final MoviesDao moviesDao;
    MutableLiveData<MovieDetailModel> liveData = new MutableLiveData<>();
    @Inject
    public MovieDetailRepo(IMovieDetails movieDetails, MoviesDao moviesDao){
        this.movieDetails = movieDetails;
        this.moviesDao = moviesDao;
    }

    @Override
    public LiveData<MovieDetailModel> getMovieDetails(int movieId) {
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
    public void bookMarkMovie(int movieId, String imageUrl) {
        AsyncTask.SERIAL_EXECUTOR.execute(() ->  {
            if(!moviesDao.markUnmarkBook(movieId))
                moviesDao.insert(constructMovieModel(liveData.getValue(), imageUrl));
        });

    }

    private BaseMovieModel constructMovieModel(MovieDetailModel value, String imageUrl) {
       BaseMovieModel movieModel = new BaseMovieModel();
       movieModel.setMarked(true);
       movieModel.setId(value.getId());
       movieModel.setName(value.getTitle());
       movieModel.setUrl(imageUrl);
       return movieModel;
    }

    @Override
    public LiveData<Boolean> isMovieBookMarked(int movieId) {
        MediatorLiveData<Boolean> liveData = new MediatorLiveData<>();
        AsyncTask.SERIAL_EXECUTOR.execute(() -> liveData.addSource(moviesDao.isBookMarked(movieId), liveData::postValue));
        return liveData;
    }
}
