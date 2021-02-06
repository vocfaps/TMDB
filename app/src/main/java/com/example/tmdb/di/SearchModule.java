package com.example.tmdb.di;

import com.example.tmdb.contracts.ISearchRepository;
import com.example.tmdb.repository.SearchRepository;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;
import dagger.hilt.android.scopes.ViewModelScoped;

@InstallIn(ViewModelComponent.class)
@Module
abstract class SearchModule {

    @Binds
    @ViewModelScoped
    abstract ISearchRepository getSeachRepo(SearchRepository impl);
}