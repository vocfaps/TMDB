package com.example.tmdb.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.tmdb.datamodel.BaseMovieModel;
import com.example.tmdb.datamodel.BaseResponseModel;
import com.example.tmdb.logger.LoggerUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@androidx.room.Database(entities = {BaseMovieModel.class}, version = 2, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "movie-db";
    private static final String TRENDING_MOVIE_FILENAME = "trending_movie.json";
    private static final String NOW_PLAYING_FILENAME = "now_playing_movie.json";

    public abstract MoviesDao getMovieDao();

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
                .fallbackToDestructiveMigration()
                .build();
    }

    public static void preloadData(Context context) {
        BaseResponseModel movieResponseModel= readLocalFile(context, TRENDING_MOVIE_FILENAME, BaseResponseModel.class);
        LoggerUtil.log("DB created with trending movie list size -> "+ movieResponseModel.getResults().size());
        getInstance(context).getMovieDao().pushNewTrendingResults(movieResponseModel.getResults());

        BaseResponseModel playingMoviewResponseModel = readLocalFile(context, NOW_PLAYING_FILENAME, BaseResponseModel.class);
        LoggerUtil.log("DB created with now playing movie list size -> "+ playingMoviewResponseModel.getResults().size());
        getInstance(context).getMovieDao().pushNewPlayingResults(playingMoviewResponseModel.getResults());
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

