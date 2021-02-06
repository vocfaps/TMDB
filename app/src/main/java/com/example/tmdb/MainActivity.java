package com.example.tmdb;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.example.tmdb.adapters.GenericRVAdapter;
import com.example.tmdb.databinding.ActivityMainBinding;
import com.example.tmdb.datamodel.NowPlayingMovieModel;
import com.example.tmdb.datamodel.TrendingMovieModel;
import com.example.tmdb.viewmodel.HomeScreenVM;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;
import search.SearchActivity;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Inflate UI
        ActivityMainBinding binding =  DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_main, null, false);
        setContentView(binding.getRoot());

        //set binding variables
        binding.setNowAdapter(new GenericRVAdapter<NowPlayingMovieModel>(new ArrayList<>()));
        binding.setTrendingAdapter(new GenericRVAdapter<TrendingMovieModel>(new ArrayList<>()));
        binding.setHandler(this);

        HomeScreenVM viewModel = new ViewModelProvider(this).get(HomeScreenVM.class);

        //observe Livedata for latest data.
        viewModel.getNowPlayingMovieModelLiveData().observe(this, nowPlayingMovieModels -> binding.getNowAdapter().updateData(nowPlayingMovieModels));
        viewModel.getTrendingMovieModelLiveData().observe(this, trendingMovieModels -> binding.getTrendingAdapter().updateData(trendingMovieModels));
    }

    public void onClick(View view){
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

}