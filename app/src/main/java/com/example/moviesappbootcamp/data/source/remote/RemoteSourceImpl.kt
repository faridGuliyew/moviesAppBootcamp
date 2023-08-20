package com.example.moviesappbootcamp.data.source.remote

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.moviesappbootcamp.common.MovieType
import com.example.moviesappbootcamp.common.model.data.NetworkState
import com.example.moviesappbootcamp.data.remote.MovieApi
import com.example.moviesappbootcamp.data.MoviePagingSource
import com.example.moviesappbootcamp.data.remote.dto.videos.VideosResponseDto
import com.example.moviesappbootcamp.domain.model.NetworkTopRatedMovieModelWithType
import com.example.moviesappbootcamp.domain.model.NetworkUpcomingMovieModelWithType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RemoteSourceImpl @Inject constructor(
    private val movieApi: MovieApi,
) : RemoteSource {

    override suspend fun searchMoviePagingResults(query: String) = flow {
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = PagingConfig.MAX_SIZE_UNBOUNDED,
                jumpThreshold = Int.MIN_VALUE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = {
                MoviePagingSource(movieApi, query)
            }
        ).flow.catch {
            Log.e("paging", "Remote source: error")
        }.collect {
            emit(it)
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getSingleMovie(movieId: Int) =
        try {
            val response = movieApi.getSingleMovie(movieId)
            NetworkState.Success(response.body())
        } catch (e: Exception) {
            NetworkState.Error(e.localizedMessage ?: "Unexpected error occurred.")
        }


    override suspend fun getRecommendedMovies(movieId: Int) =
        try {
            val response = movieApi.getRecommendedMovies(movieId).body()
            NetworkState.Success(response)
        } catch (e: Exception) {
            NetworkState.Error(e.localizedMessage ?: "Unexpected error occurred.")
        }


    override suspend fun getReviews(movieId: Int) =
        try {
            val response = movieApi.getReviews(movieId).body()
            NetworkState.Success(response)
        } catch (e: Exception) {
            NetworkState.Error(e.localizedMessage ?: "Unexpected error occurred")
        }

    override suspend fun getVideos(movieId: Int): NetworkState<VideosResponseDto> {
        return try {
            val response = movieApi.getMovieVideos(movieId).body()
            NetworkState.Success(response)
        }catch (e:Exception){
            NetworkState.Error(e.localizedMessage?:"Unexpected error occurred")
        }
    }


    override suspend fun getMovieCredits(movieId: Int) =
        try {
            val response = movieApi.getMovieCredits(movieId)
            NetworkState.Success(response.body())
        } catch (e: Exception) {
            NetworkState.Error(e.localizedMessage ?: "Unexpected error occurred.")
        }


    override suspend fun getTopRatedMovies(movieType: MovieType) =

        try {
            val request = movieApi.getTopRatedMovies(movieType.query)

            NetworkState.Success(
                NetworkTopRatedMovieModelWithType(
                    movieType,
                    request.body()
                )

            )
        } catch (e: Exception) {
            NetworkState.Error(e.localizedMessage ?: "Unexpected error occurred.")
        }


    override suspend fun getUpcomingMovies(movieType: MovieType) =

        try {
            val response = movieApi.getRecentMovies(movieType.query)

            NetworkState.Success(
                NetworkUpcomingMovieModelWithType(
                    movieType,
                    response.body()
                )
            )

        } catch (e: Exception) {
            NetworkState.Error(e.localizedMessage ?: "Unexpected error occurred.")
        }

}

