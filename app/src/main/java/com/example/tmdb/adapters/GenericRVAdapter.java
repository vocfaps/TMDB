package com.example.tmdb.adapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tmdb.ViewHolderFactory;
import com.example.tmdb.logger.LoggerUtil;

import java.util.List;

public class GenericRVAdapter<E> extends RecyclerView.Adapter<ViewHolderFactory.BaseVH<E>> {
    List<E> listOfItems;

    public GenericRVAdapter(List<E> listOfItems){
        this.listOfItems = listOfItems;
    }

    @NonNull
    @Override
    public ViewHolderFactory.BaseVH<E> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ViewHolderFactory.getViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderFactory.BaseVH holder, int position) {
        holder.bind(listOfItems.get(position));
    }

    @Override
    public int getItemCount() {
        return listOfItems.size();
    }

    public void updateData(List<E> list) {
        if(list != null){
            this.listOfItems = list;
        }else{
            LoggerUtil.log("Empty list encountered for " + this.getClass().getTypeParameters());
        }
        notifyDataSetChanged();
        //DIFFUtils maybe
    }
}
