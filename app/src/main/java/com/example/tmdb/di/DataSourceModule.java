package com.example.tmdb.di;

import android.content.Context;

import com.example.tmdb.db.MovieDatabase;
import com.example.tmdb.db.NowPlayingMoviesDao;
import com.example.tmdb.db.TrendingMoviesDao;
import com.example.tmdb.logger.LoggerUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import network.IHomeResponse;
import network.ISearchResult;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@InstallIn(SingletonComponent.class)
@Module
public class DataSourceModule {

    @Provides
    TrendingMoviesDao getDao(MovieDatabase database){
        return database.getMovieDao();
    }

    @Provides
    NowPlayingMoviesDao getPlayingDao(MovieDatabase database){
        return database.getPlayingMovieDao();
    }

    @Provides
    @Singleton
    MovieDatabase provideDatabase(@ApplicationContext Context context){
        LoggerUtil.log("Trying to get DB instance");
        return MovieDatabase.getInstance(context);
    }

    @Provides
    Gson getGson(){
        return new GsonBuilder().create();
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit(Gson gson) {
        return new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }


    @Provides
    ISearchResult provideSearchDataSource(Retrofit retrofit) {
       return retrofit.create(ISearchResult.class);
    }

    @Provides
    IHomeResponse provideHomeDataSource(Retrofit retrofit){
        return retrofit.create(IHomeResponse.class);
    }

}
