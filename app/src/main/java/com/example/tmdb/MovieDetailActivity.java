package com.example.tmdb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.example.tmdb.databinding.ActivityMovieDetailBinding;
import com.example.tmdb.viewmodel.MovieDetailVM;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMovieDetailBinding binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_movie_detail, null, false);
        setContentView(binding.getRoot());

        MovieDetailVM movieDetailVM = new ViewModelProvider(this).get(MovieDetailVM.class);
        binding.setLifecycleOwner(this);
        binding.setModel(movieDetailVM);
        binding.setImageUrl(getIntent().getStringExtra("url"));
        binding.executePendingBindings();

        movieDetailVM.fetchMovieDetails(getIntent().getIntExtra("id", 0), binding.getImageUrl());
    }
}