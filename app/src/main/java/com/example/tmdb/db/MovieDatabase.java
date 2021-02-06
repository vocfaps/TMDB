package com.example.tmdb.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.tmdb.datamodel.NowPlayingMovieModel;
import com.example.tmdb.datamodel.NowPlayingMoviewResponseModel;
import com.example.tmdb.datamodel.TrendingMovieModel;
import com.example.tmdb.datamodel.TrendingMovieResponseModel;
import com.example.tmdb.logger.LoggerUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@androidx.room.Database(entities = {TrendingMovieModel.class, NowPlayingMovieModel.class}, version = 1, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "movie-db";
    private static final String TRENDING_MOVIE_FILENAME = "trending_movie.json";
    private static final String NOW_PLAYING_FILENAME = "now_playing_movie.json";

    public abstract TrendingMoviesDao getMovieDao();
    public abstract NowPlayingMoviesDao getPlayingMovieDao();

    private static MovieDatabase instance;

    public static MovieDatabase getInstance(Context context){
        if(instance == null) {
            instance = getNewInstance(context);
        }
        return instance;
    }

    private static MovieDatabase getNewInstance(Context context) {
        LoggerUtil.log("New instance being created");
        return Room.databaseBuilder(context, MovieDatabase.class, DATABASE_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        LoggerUtil.log("Fresh DB creation");
                        AsyncTask.SERIAL_EXECUTOR.execute(() -> preloadData(context));
                    }
                })
                .build();
    }

    public static void preloadData(Context context) {
        TrendingMovieResponseModel movieResponseModel= readLocalFile(context, TRENDING_MOVIE_FILENAME, TrendingMovieResponseModel.class);
        LoggerUtil.log("DB created with trending movie list size -> "+ movieResponseModel.getResults().size());
        getInstance(context).getMovieDao().pushNewResults(movieResponseModel.getResults());

        NowPlayingMoviewResponseModel playingMoviewResponseModel = readLocalFile(context, NOW_PLAYING_FILENAME, NowPlayingMoviewResponseModel.class);
        LoggerUtil.log("DB created with now playing movie list size -> "+ playingMoviewResponseModel.getResults().size());
        getInstance(context).getPlayingMovieDao().pushNewResults(playingMoviewResponseModel.getResults());
    }

    private static <T> T readLocalFile(Context context, String fileName, Class<T> clazz) {
        InputStream inputStream = null;
        try {
            inputStream = context.getAssets().open(fileName);
            InputStreamReader reader = new InputStreamReader(inputStream);
            Gson gson = new Gson();
            return gson.fromJson(reader, clazz);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}

