package com.example.tmdb.di;


import com.example.tmdb.contracts.IMovieDetailRepository;
import com.example.tmdb.repository.MovieDetailRepo;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;
import dagger.hilt.android.scopes.ViewModelScoped;

@InstallIn(ViewModelComponent.class)
@Module
abstract class MovieDetailModule {

    @Binds
    @ViewModelScoped
    abstract IMovieDetailRepository getMovieDetailsRepo(MovieDetailRepo impl);

}
