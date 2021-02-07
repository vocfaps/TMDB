package network;

import com.example.tmdb.datamodel.MovieDetailModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IMovieDetails {

    @GET("movie/{movieId}?api_key=e5f9a608972c1a374a278d723c8c3fdf&language=en-US")
    Call<MovieDetailModel> getMovieDetails(@Path("movieId") int movieId);

}
