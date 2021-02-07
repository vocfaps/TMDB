package com.example.tmdb;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tmdb.databinding.LytMovieItemBinding;
import com.example.tmdb.datamodel.BaseMovieModel;

public class ViewHolderFactory {
    public static <E> BaseVH<E> getViewHolder(ViewGroup parent, int viewType) {
       return new CarouselMoviesVH(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.lyt_movie_item, parent, false));
    }

    public static class CarouselMoviesVH<E> extends BaseVH<BaseMovieModel> {

        private final LytMovieItemBinding binding;

        public CarouselMoviesVH(@NonNull LytMovieItemBinding binding) {
            super(binding);
            this.binding = binding;
        }

        @Override
        public void bind(BaseMovieModel movieModel) {
            binding.setModel(movieModel);
            binding.setListener(this);
            binding.executePendingBindings();
        }

        public void onClick(BaseMovieModel movieModel){
            Intent intent = new Intent(binding.getRoot().getContext(), MovieDetailActivity.class);
            intent.putExtra("id", movieModel.getId());
            intent.putExtra("url", movieModel.getUrl());
            binding.getRoot().getContext().startActivity(intent);
        }
    }

    public static abstract class BaseVH<E> extends RecyclerView.ViewHolder{
        public BaseVH(ViewDataBinding binding){
            super(binding.getRoot());
        }

        public abstract void bind(E e);
    }
}
