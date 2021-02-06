package network;

import com.example.tmdb.datamodel.BaseResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ISearchResult {

    @GET("search/movie?api_key=e5f9a608972c1a374a278d723c8c3fdf&language=en-US&page=1&include_adult=false")
    Call<BaseResponseModel> getSearchResult(@Query("query") String query);
}
