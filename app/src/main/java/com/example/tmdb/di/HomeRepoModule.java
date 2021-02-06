package com.example.tmdb.di;

import com.example.tmdb.contracts.IMovieHomeRepository;
import com.example.tmdb.repository.HomeScreenLocalRepository;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;
import dagger.hilt.android.scopes.ViewModelScoped;

@InstallIn(ViewModelComponent.class)
@Module
abstract class HomeRepoModule {

@Binds
@ViewModelScoped
abstract IMovieHomeRepository getLocalRepo(HomeScreenLocalRepository impl);
}


