package com.example.tmdb;

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

    static class CarouselMoviesVH<E> extends BaseVH<BaseMovieModel> {

        private final LytMovieItemBinding binding;

        public CarouselMoviesVH(@NonNull LytMovieItemBinding binding) {
            super(binding);
            this.binding = binding;
        }

        @Override
        public void bind(BaseMovieModel movieModel) {
            binding.setModel(movieModel);
            binding.executePendingBindings();
        }
    }

    public static abstract class BaseVH<E> extends RecyclerView.ViewHolder{
        public BaseVH(ViewDataBinding binding){
            super(binding.getRoot());
        }

        public abstract void bind(E e);
    }
}
