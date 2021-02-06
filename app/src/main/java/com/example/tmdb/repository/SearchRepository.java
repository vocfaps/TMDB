package com.example.tmdb.repository;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tmdb.contracts.ISearchRepository;
import com.example.tmdb.datamodel.BaseMovieModel;
import com.example.tmdb.datamodel.BaseResponseModel;
import com.example.tmdb.logger.LoggerUtil;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.android.scopes.ViewModelScoped;
import network.ISearchResult;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@ViewModelScoped
public class SearchRepository implements ISearchRepository {

    private final ISearchResult searchDataSource;
    public @Inject SearchRepository(@ApplicationContext Context context, ISearchResult searchResultDataSource){
        this.searchDataSource = searchResultDataSource;
    }


    @Override
    public LiveData<List<BaseMovieModel>> getSearchResults(String query) {
        //call retrofit stuff
        MutableLiveData<List<BaseMovieModel>> responseLiveData = new MutableLiveData<>();
        Call<BaseResponseModel> call = searchDataSource.getSearchResult(query);
        call.enqueue(new Callback<BaseResponseModel>() {
            @Override
            public void onResponse(Call<BaseResponseModel> call, Response<BaseResponseModel> response) {
                BaseResponseModel model = response.body();
                responseLiveData.setValue(model.getResults());
            }

            @Override
            public void onFailure(Call<BaseResponseModel> call, Throwable t) {
                LoggerUtil.log("Error from API");
            }
        });
        return responseLiveData;
    }
}
