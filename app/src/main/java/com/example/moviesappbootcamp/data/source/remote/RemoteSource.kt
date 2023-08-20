package com.example.moviesappbootcamp.data.source.remote

import androidx.paging.PagingData
import com.example.moviesappbootcamp.common.MovieType
import com.example.moviesappbootcamp.common.model.data.NetworkState
import com.example.moviesappbootcamp.data.remote.dto.credits.CreditsResponseDto
import com.example.moviesappbootcamp.data.remote.dto.reviews.ReviewsResponseDto
import com.example.moviesappbootcamp.data.remote.dto.single.SingleMovieResponseDto
import com.example.moviesappbootcamp.data.remote.dto.top_rated.ResultDto
import com.example.moviesappbootcamp.data.remote.dto.top_rated.TopRatedResponseDto
import com.example.moviesappbootcamp.data.remote.dto.videos.VideosResponseDto
import com.example.moviesappbootcamp.domain.model.NetworkTopRatedMovieModelWithType
import com.example.moviesappbootcamp.domain.model.NetworkUpcomingMovieModelWithType
import kotlinx.coroutines.flow.Flow

interface RemoteSource {

    suspend fun searchMoviePagingResults(query : String) : Flow<PagingData<ResultDto>>

    suspend fun getSingleMovie(movieId : Int) : NetworkState<SingleMovieResponseDto>

    suspend fun getRecommendedMovies(movieId: Int) : NetworkState<TopRatedResponseDto>

    suspend fun getReviews(movieId: Int) : NetworkState<ReviewsResponseDto>

    suspend fun getVideos(movieId: Int) : NetworkState<VideosResponseDto>

    suspend fun getMovieCredits(movieId : Int) : NetworkState<CreditsResponseDto>
    suspend fun getTopRatedMovies(movieType: MovieType) : NetworkState<NetworkTopRatedMovieModelWithType>

    suspend fun getUpcomingMovies(movieType: MovieType): NetworkState<NetworkUpcomingMovieModelWithType>
}