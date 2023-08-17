package com.example.moviesappbootcamp.data.source.remote

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.moviesappbootcamp.common.MovieType
import com.example.moviesappbootcamp.common.model.NetworkState
import com.example.moviesappbootcamp.common.model.Resource
import com.example.moviesappbootcamp.data.remote.MovieApi
import com.example.moviesappbootcamp.data.MoviePagingSource
import com.example.moviesappbootcamp.data.mapper.toBriefUiModel
import com.example.moviesappbootcamp.data.remote.dto.credits.CreditsResponseDto
import com.example.moviesappbootcamp.data.remote.dto.reviews.ReviewsResponseDto
import com.example.moviesappbootcamp.data.remote.dto.top_rated.TopRatedResponseDto
import com.example.moviesappbootcamp.domain.model.MovieModelWithType
import com.example.moviesappbootcamp.domain.model.NetworkTopRatedMovieModelWithType
import com.example.moviesappbootcamp.domain.model.NetworkUpcomingMovieModelWithType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
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

    //todo
    override suspend fun getSingleMovie(movieId: Int) = flow {
        try {
            val response = movieApi.getSingleMovie(movieId)
            emit(NetworkState.Success(response.body()))
        } catch (e: Exception) {
            emit(NetworkState.Error(e.localizedMessage?:"Unexpected error occurred."))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getRecommendedMovies(movieId: Int) = flow {
        try {
            val response = movieApi.getRecommendedMovies(movieId).body()
            emit(NetworkState.Success(response))
        }catch (e:Exception){
            emit(NetworkState.Error(e.localizedMessage?:"Unexpected error occurred."))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getReviews(movieId: Int) = flow {
        try {
            val response = movieApi.getReviews(movieId).body()
            emit(NetworkState.Success(response))
        }catch (e:Exception){
            emit(NetworkState.Error(e.localizedMessage?:"Unexpected error occurred"))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getMovieCredits(movieId: Int) = flow {
        try {
            val response = movieApi.getMovieCredits(movieId)
            emit(NetworkState.Success(response.body()))
        }catch (e:Exception){
            emit(NetworkState.Error(e.localizedMessage?:"Unexpected error occurred."))
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getTopRatedMovies(movieType: MovieType) =
        flow {
            try {
                val request = movieApi.getTopRatedMovies(movieType.query)
                emit(
                    NetworkState.Success(
                        NetworkTopRatedMovieModelWithType(
                            movieType,
                            request.body()
                        )
                    )
                )
            } catch (e: Exception) {
                emit(NetworkState.Error(e.localizedMessage?:"Unexpected error occurred."))
            }
        }.flowOn(Dispatchers.IO)

    override suspend fun getUpcomingMovies(movieType: MovieType) =
        flow {
            try {
                val response = movieApi.getRecentMovies(movieType.query)
                emit(
                    NetworkState.Success(
                        NetworkUpcomingMovieModelWithType(
                            movieType,
                            response.body()
                        )
                    )
                )
            } catch (e: Exception) {
                emit(NetworkState.Error(e.localizedMessage?:"Unexpected error occurred."))
            }
        }.flowOn(Dispatchers.IO)
}