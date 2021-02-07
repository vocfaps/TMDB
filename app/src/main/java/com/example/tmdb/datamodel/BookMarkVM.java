package com.example.tmdb.datamodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.example.tmdb.contracts.IBookMarkMovieRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class BookMarkVM extends AndroidViewModel {

    private final MediatorLiveData<List<BaseMovieModel>> liveData = new MediatorLiveData<>();

    @Inject public BookMarkVM(@NonNull Application application, IBookMarkMovieRepository repository) {
        super(application);
        liveData.addSource(repository.fetchMarkedMovies(), liveData::postValue);
    }

    public LiveData<List<BaseMovieModel>> getLiveData() {
        return liveData;
    }
}
