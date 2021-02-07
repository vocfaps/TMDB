package com.example.tmdb.repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.example.tmdb.contracts.IMovieHomeRepository;
import com.example.tmdb.datamodel.BaseMovieModel;
import com.example.tmdb.datamodel.BaseResponseModel;
import com.example.tmdb.db.MovieDatabase;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.android.scopes.ViewModelScoped;
import network.IHomeResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@ViewModelScoped
public class HomeScreenLocalRepository implements IMovieHomeRepository {

    private final MovieDatabase database;
    private final IHomeResponse homeResponseSource;
    private final MediatorLiveData<List<BaseMovieModel>> trendingliveData =  new MediatorLiveData<>();
    private final MediatorLiveData<List<BaseMovieModel>> newMovieLiveData = new MediatorLiveData<>();

    private void getNewMovieDBData(MediatorLiveData<List<BaseMovieModel>> newMovieLiveData) {
        newMovieLiveData.addSource(database.getMovieDao().getAllNowMovies(), newMovieLiveData::postValue);
    }

    @Inject public HomeScreenLocalRepository(@ApplicationContext Context context, MovieDatabase database, IHomeResponse homeResponse){
        this.database = database;
        this.homeResponseSource = homeResponse;
    }

    @Override
    public LiveData<List<BaseMovieModel>> fetchTrendingMovies() {
        //Start observing a livedata from DB.
        AsyncTask.SERIAL_EXECUTOR.execute(() -> getTrendingDBData(trendingliveData));
        return trendingliveData;
    }

    private void getTrendingDBData(MediatorLiveData<List<BaseMovieModel>> liveData) {
        liveData.addSource(database.getMovieDao().getAllTrendingMovies(), liveData::postValue);
    }

    @Override
    public LiveData<List<BaseMovieModel>> fetchNowPLayingMovies() {
        AsyncTask.SERIAL_EXECUTOR.execute(() -> getNewMovieDBData(newMovieLiveData));
        return newMovieLiveData;
    }

    @Override
    public void updateTrendingMovies() {
        Call<BaseResponseModel> call = homeResponseSource.getTrendingMovies();
        call.enqueue(new Callback<BaseResponseModel>() {
            @Override
            public void onResponse(Call<BaseResponseModel> call, Response<BaseResponseModel> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getResults() != null){
                    AsyncTask.SERIAL_EXECUTOR.execute(() -> database.getMovieDao().pushNewTrendingResults(response.body().getResults()));
                }
            }

            @Override
            public void onFailure(Call<BaseResponseModel> call, Throwable t) {

            }
        });
    }

    @Override
    public void updateNowPlayingMovies() {
        Call<BaseResponseModel> call =  homeResponseSource.getNowPlayingMovies();
        call.enqueue(new Callback<BaseResponseModel>() {
            @Override
            public void onResponse(Call<BaseResponseModel> call, Response<BaseResponseModel> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getResults() != null){
                    AsyncTask.SERIAL_EXECUTOR.execute(() -> database.getMovieDao().pushNewPlayingResults(response.body().getResults()));
                }

            }

            @Override
            public void onFailure(Call<BaseResponseModel> call, Throwable t) {

            }
        });
    }
}
