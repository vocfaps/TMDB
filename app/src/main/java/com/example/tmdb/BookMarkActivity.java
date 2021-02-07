package com.example.tmdb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.example.tmdb.adapters.GenericRVAdapter;
import com.example.tmdb.databinding.ActivityBookMarkBinding;
import com.example.tmdb.datamodel.BaseMovieModel;
import com.example.tmdb.datamodel.BookMarkVM;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BookMarkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityBookMarkBinding binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_book_mark, null, false);
        setContentView(binding.getRoot());

        BookMarkVM vm = new ViewModelProvider(this).get(BookMarkVM.class);
        binding.setAdapter(new GenericRVAdapter<BaseMovieModel>(new ArrayList<>()));
        binding.setLifecycleOwner(this);
        binding.setViewModel(vm);
        binding.executePendingBindings();


        vm.getLiveData().observe(this,(data) -> binding.getAdapter().updateData(data));

    }
}