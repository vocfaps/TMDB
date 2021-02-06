package com.example.tmdb.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.example.tmdb.contracts.ISearchRepository;
import com.example.tmdb.datamodel.BaseMovieModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class SearchVM extends AndroidViewModel {

    private final ISearchRepository searchRepository;
    private MediatorLiveData<List<BaseMovieModel>> searchResultsLiveData = new MediatorLiveData<>();

    @Inject public SearchVM(@NonNull Application application, ISearchRepository searchRepository) {
        super(application);
        this.searchRepository = searchRepository;
    }

    public void getSearchResults(String query){
        searchResultsLiveData.addSource(searchRepository.getSearchResults(query), baseMovieModels -> {
            searchResultsLiveData.setValue(baseMovieModels);
        });
    }

    public LiveData<List<BaseMovieModel>> getSearchResultsLiveData() {
        return searchResultsLiveData;
    }
}
