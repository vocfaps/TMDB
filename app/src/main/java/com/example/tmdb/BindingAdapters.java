package com.example.tmdb;

import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tmdb.adapters.GenericRVAdapter;
import com.example.tmdb.viewmodel.SearchVM;

public class BindingAdapters {

    @BindingAdapter(value = "setCarousel")
    public static  void setCarousel(RecyclerView recyclerView, GenericRVAdapter adapter){
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    @BindingAdapter(value = "loadImage")
    public static void loadImage(ImageView imageView, String url){
        if(!TextUtils.isEmpty(url)){

            Glide.with(imageView).load("https://image.tmdb.org/t/p/original"+url).into(imageView);
        }
    }

    @BindingAdapter(value = "setEditTextSearch")
    public static void setEditTextSearch(AppCompatEditText editTextSearch, SearchVM viewModel) {
        editTextSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (NetworkUtils.isNetworkAvailable(editTextSearch.getContext())) {
                    viewModel.getSearchResults(editTextSearch.getText().toString());
                    return true;
                }else{
                    Toast.makeText(editTextSearch.getContext(), editTextSearch.getContext().getString(R.string.no_internet),Toast.LENGTH_SHORT).show();
                }
            }
            return false;
        });
    }

    @BindingAdapter(value = {"setGridAdapter", "setSpanCount"})
    public static void setGridAdapter(RecyclerView recyclerView, GenericRVAdapter adapter, int spanCount){
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), spanCount));
    }
}
