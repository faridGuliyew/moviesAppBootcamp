package com.example.moviesappbootcamp.data.remote

import com.example.moviesappbootcamp.common.Constants.API_KEY
import com.example.moviesappbootcamp.data.remote.dto.top_rated.TopRatedResponseDto
import com.example.moviesappbootcamp.data.remote.dto.upcoming.UpcomingMoviesResponseDto
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    //TopRated response is the same for both top rated and popular
    @GET("movie/{path}")
    suspend fun getTopRatedMovies(@Path("path") path : String, @Query("api_key") apiKey : String = API_KEY) : Response<TopRatedResponseDto>

    //Upcoming and now played response objects are the same
    @GET("movie/{path}")
    suspend fun getRecentMovies(@Path("path") path : String, @Query("api_key") apiKey : String = API_KEY) : Response<UpcomingMoviesResponseDto>

    //returns the same object as in top rated response
    @GET("search/movie")
    suspend fun searchMovie(@Query("query") query : String, @Query("page") page : Int = 1, @Query("api_key") apiKey : String = API_KEY) : Response<TopRatedResponseDto>
}