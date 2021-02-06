package com.example.tmdb.repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.example.tmdb.contracts.IMovieHomeRepository;
import com.example.tmdb.datamodel.NowPlayingMovieModel;
import com.example.tmdb.datamodel.NowPlayingMoviewResponseModel;
import com.example.tmdb.datamodel.TrendingMovieModel;
import com.example.tmdb.datamodel.TrendingMovieResponseModel;
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
    private final MediatorLiveData<List<TrendingMovieModel>> trendingliveData =  new MediatorLiveData<>();
    private final MediatorLiveData<List<NowPlayingMovieModel>> newMovieLiveData = new MediatorLiveData<>();

    private void getNewMovieDBData(MediatorLiveData<List<NowPlayingMovieModel>> newMovieLiveData) {
        newMovieLiveData.addSource(database.getPlayingMovieDao().getAllNewMovies(), newMovieLiveData::postValue);
    }

    @Inject public HomeScreenLocalRepository(@ApplicationContext Context context, MovieDatabase database, IHomeResponse homeResponse){
        this.database = database;
        this.homeResponseSource = homeResponse;
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

    @Override
    public void updateTrendingMovies() {
        Call<TrendingMovieResponseModel> call = homeResponseSource.getTrendingMovies();
        call.enqueue(new Callback<TrendingMovieResponseModel>() {
            @Override
            public void onResponse(Call<TrendingMovieResponseModel> call, Response<TrendingMovieResponseModel> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getResults() != null){
                    AsyncTask.SERIAL_EXECUTOR.execute(() -> database.getMovieDao().pushNewResults(response.body().getResults()));
                }
            }

            @Override
            public void onFailure(Call<TrendingMovieResponseModel> call, Throwable t) {

            }
        });
    }

    @Override
    public void updateNowPlayingMovies() {
        Call<NowPlayingMoviewResponseModel> call =  homeResponseSource.getNowPlayingMovies();
        call.enqueue(new Callback<NowPlayingMoviewResponseModel>() {
            @Override
            public void onResponse(Call<NowPlayingMoviewResponseModel> call, Response<NowPlayingMoviewResponseModel> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getResults() != null){
                    AsyncTask.SERIAL_EXECUTOR.execute(() -> database.getPlayingMovieDao().pushNewResults(response.body().getResults()));
                }

            }

            @Override
            public void onFailure(Call<NowPlayingMoviewResponseModel> call, Throwable t) {

            }
        });
    }
}
