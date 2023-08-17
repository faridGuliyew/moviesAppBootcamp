package com.example.moviesappbootcamp.data.source.remote

import androidx.paging.PagingData
import com.example.moviesappbootcamp.common.MovieType
import com.example.moviesappbootcamp.common.model.NetworkState
import com.example.moviesappbootcamp.common.model.Resource
import com.example.moviesappbootcamp.data.remote.dto.credits.CreditsResponseDto
import com.example.moviesappbootcamp.data.remote.dto.single.SingleMovieResponseDto
import com.example.moviesappbootcamp.data.remote.dto.top_rated.ResultDto
import com.example.moviesappbootcamp.data.remote.dto.top_rated.TopRatedResponseDto
import com.example.moviesappbootcamp.data.remote.dto.upcoming.UpcomingMoviesResponseDto
import com.example.moviesappbootcamp.domain.model.MovieModelWithType
import com.example.moviesappbootcamp.domain.model.NetworkTopRatedMovieModelWithType
import com.example.moviesappbootcamp.domain.model.NetworkUpcomingMovieModelWithType
import kotlinx.coroutines.flow.Flow

interface RemoteSource {

    suspend fun searchMoviePagingResults(query : String) : Flow<PagingData<ResultDto>>

    suspend fun getSingleMovie(movieId : Int) : Flow<NetworkState<SingleMovieResponseDto>>

    suspend fun getRecommendedMovies(movieId: Int) : Flow<NetworkState<TopRatedResponseDto>>

    suspend fun getMovieCredits(movieId : Int) : Flow<NetworkState<CreditsResponseDto>>
    suspend fun getTopRatedMovies(movieType: MovieType) : Flow<NetworkState<NetworkTopRatedMovieModelWithType>>

    suspend fun getUpcomingMovies(movieType: MovieType): Flow<NetworkState<NetworkUpcomingMovieModelWithType>>
}