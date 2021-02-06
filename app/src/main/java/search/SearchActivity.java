package search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.example.tmdb.R;
import com.example.tmdb.adapters.GenericRVAdapter;
import com.example.tmdb.databinding.ActivitySearchBinding;
import com.example.tmdb.datamodel.BaseMovieModel;
import com.example.tmdb.viewmodel.HomeScreenVM;
import com.example.tmdb.viewmodel.SearchVM;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySearchBinding binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.activity_search, null, false);
        setContentView(binding.getRoot());

        SearchVM viewModel = new ViewModelProvider(this).get(SearchVM.class);
        binding.setViewModel(viewModel);
        binding.setAdapter(new GenericRVAdapter<BaseMovieModel>(new ArrayList<>()));
        binding.setSpanCount(3);
        binding.setLifecycleOwner(this);
        binding.executePendingBindings();

        viewModel.getSearchResultsLiveData().observe(this, (models) -> binding.getAdapter().updateData(models));
    }
}