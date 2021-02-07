package com.example.tmdb.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.tmdb.NetworkUtils;
import com.example.tmdb.contracts.IMovieHomeRepository;
import com.example.tmdb.datamodel.BaseMovieModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class HomeScreenVM extends AndroidViewModel {
    private final MutableLiveData<List<BaseMovieModel>> trendingMovieModelLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<BaseMovieModel>> nowPlayingMovieModelLiveData = new MutableLiveData<>();

    private final IMovieHomeRepository localRepository;

    @Inject public HomeScreenVM(@NonNull Application application, IMovieHomeRepository localRepository) {
        super(application);
        this.localRepository = localRepository;
    }

    public LiveData<List<BaseMovieModel>> getTrendingMovieModelLiveData() {
        if (trendingMovieModelLiveData.getValue() == null) {//this is done so that every attachment to the livedata does not trigger a repository-Hit
            return Transformations.switchMap(localRepository.fetchTrendingMovies(), input -> {
                trendingMovieModelLiveData.setValue(input);
                if (NetworkUtils.isNetworkAvailable(getApplication()))
                   localRepository.updateTrendingMovies();
                return trendingMovieModelLiveData;
            });
        }
        return trendingMovieModelLiveData;
    }

    public LiveData<List<BaseMovieModel>> getNowPlayingMovieModelLiveData() {
        if (nowPlayingMovieModelLiveData.getValue() == null) {//this is done so that every attachment to the livedata does not trigger a repository-Hit
            return Transformations.switchMap(localRepository.fetchNowPLayingMovies(), input -> {
                nowPlayingMovieModelLiveData.setValue(input);
                if (NetworkUtils.isNetworkAvailable(getApplication()))
                    localRepository.updateNowPlayingMovies();
                return nowPlayingMovieModelLiveData;
            });
        }
        return nowPlayingMovieModelLiveData;
    }
}
