package com.example.tmdb.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tmdb.contracts.ISearchRepository;
import com.example.tmdb.datamodel.BaseMovieModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class SearchVM extends AndroidViewModel {

    private final ISearchRepository searchRepository;
    private final MediatorLiveData<List<BaseMovieModel>> searchResultsLiveData = new MediatorLiveData<>();
    private final MediatorLiveData<String> errorQuery = new MediatorLiveData<>();
    private final MutableLiveData<String> userInputLiveData = new MutableLiveData<>();

    public MutableLiveData<String> getUserInputLiveData() {
        return userInputLiveData;
    }

    public MutableLiveData<String> getErrorQuery() {
        return errorQuery;
    }

    @Inject public SearchVM(@NonNull Application application, ISearchRepository searchRepository) {
        super(application);
        this.searchRepository = searchRepository;
        errorQuery.addSource(userInputLiveData, s -> errorQuery.setValue(""));
    }

    public void getSearchResults(String query) {
        searchResultsLiveData.addSource(searchRepository.getSearchResults(query), baseMovieModels -> {
            errorQuery.setValue(baseMovieModels.size() > 0 ? "" : query);
            searchResultsLiveData.setValue(baseMovieModels);

        });
    }

    public LiveData<List<BaseMovieModel>> getSearchResultsLiveData() {
        return searchResultsLiveData;
    }
}
