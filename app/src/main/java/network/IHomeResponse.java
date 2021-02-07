package network;

import com.example.tmdb.datamodel.BaseResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IHomeResponse {

    @GET("trending/movie/week?api_key=e5f9a608972c1a374a278d723c8c3fdf")
    Call<BaseResponseModel> getTrendingMovies();

    @GET("movie/now_playing?api_key=e5f9a608972c1a374a278d723c8c3fdf&language=en-US&page=1")
    Call<BaseResponseModel> getNowPlayingMovies();

}
