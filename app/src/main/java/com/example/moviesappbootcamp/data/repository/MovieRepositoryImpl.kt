package com.example.moviesappbootcamp.data.repository

import android.util.Log
import androidx.paging.map
import com.example.moviesappbootcamp.common.MovieType
import com.example.moviesappbootcamp.common.model.data.NetworkState
import com.example.moviesappbootcamp.common.model.data.Resource
import com.example.moviesappbootcamp.data.mapper.toBriefUiModel
import com.example.moviesappbootcamp.data.mapper.toBriefUiModels
import com.example.moviesappbootcamp.data.mapper.toBrieffUiModels
import com.example.moviesappbootcamp.data.mapper.toCreditsUiModel
import com.example.moviesappbootcamp.data.mapper.toDetailedUiModel
import com.example.moviesappbootcamp.data.mapper.toReviewUiModels
import com.example.moviesappbootcamp.data.mapper.toTrailerUiModels
import com.example.moviesappbootcamp.data.source.remote.RemoteSource
import com.example.moviesappbootcamp.domain.model.MovieModelWithType
import com.example.moviesappbootcamp.domain.model.TrailerUiModel
import com.example.moviesappbootcamp.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val remoteSource: RemoteSource,
) : MovieRepository {

    private val TAG = "MovieRepository"
    override suspend fun getTopRatedMovies(movieType: MovieType) =
        flow {
            emit(Resource.Loading)

            when (val response = remoteSource.getTopRatedMovies(movieType)) {
                is NetworkState.Success -> {
                    emit(
                        Resource.Success(
                            MovieModelWithType(
                                movieType,
                                response.data?.response?.resultDtos?.toBriefUiModels()
                                    .orEmpty()
                            )
                        )
                    )
                }

                is NetworkState.Error -> {
                    emit(Resource.Error(response.errorMessage))
                }
            }
        }.flowOn(Dispatchers.IO).catch {
            Log.e(TAG, "getTopRatedMovies: error")
        }

    override suspend fun getUpcomingMovies(movieType: MovieType) =
        flow {
            emit(Resource.Loading)

            when (val response = remoteSource.getUpcomingMovies(movieType)) {
                is NetworkState.Success -> {
                    emit(
                        Resource.Success(
                            MovieModelWithType(
                                movieType,
                                response.data?.response?.upcomingResultDtos?.toBrieffUiModels()
                                    .orEmpty()
                            )
                        )
                    )
                }

                is NetworkState.Error -> {
                    emit(Resource.Error(response.errorMessage))
                }
            }
        }.flowOn(Dispatchers.IO).catch {
            Log.e(TAG, "getUpcomingMovies: error")
        }

    override suspend fun getSingleMovie(movieId: Int) = flow {

        emit(Resource.Loading)
        when (val response = remoteSource.getSingleMovie(movieId)) {
            is NetworkState.Success -> {
                val data = response.data
                emit(Resource.Success(data?.toDetailedUiModel()))
            }

            is NetworkState.Error -> {
                emit(Resource.Error(response.errorMessage))
            }
        }
    }

    override suspend fun getVideos(movieId: Int) = flow {
        emit(Resource.Loading)

        when(val networkState = remoteSource.getVideos(movieId)){
            is NetworkState.Error-> emit(Resource.Error(networkState.errorMessage))
            is NetworkState.Success -> {
                emit(Resource.Success(networkState.data?.toTrailerUiModels().orEmpty()))
            }
        }
    }

    override suspend fun getRecommendedMovies(movieId: Int) = flow {

        emit(Resource.Loading)
        when (val response = remoteSource.getRecommendedMovies(movieId)) {
            is NetworkState.Error -> emit(Resource.Error(response.errorMessage))
            is NetworkState.Success -> {
                emit(Resource.Success(response.data?.resultDtos?.toBriefUiModels().orEmpty()))
            }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getMovieCredits(movieId: Int) = flow {
        emit(Resource.Loading)

        when (val response = remoteSource.getMovieCredits(movieId)) {
            is NetworkState.Success -> {
                val data = response.data
                emit(Resource.Success(data?.cast?.toCreditsUiModel()))
            }

            is NetworkState.Error -> {
                emit(Resource.Error(response.errorMessage))
            }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getReviews(movieId: Int) = flow {
        emit(Resource.Loading)
        when (val response = remoteSource.getReviews(movieId)) {
            is NetworkState.Error -> emit(Resource.Error(response.errorMessage))
            is NetworkState.Success -> {
                emit(Resource.Success(response.data?.toReviewUiModels().orEmpty()))
            }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun searchMovies(query: String) = flow {
        remoteSource.searchMoviePagingResults(query)
            .map { pagingData ->
                pagingData.map {
                    it.toBriefUiModel()
                }
            }
            .catch {
                Log.e(TAG, "search paging: error")
            }
            .collect {
                emit(it)
            }
    }.flowOn(Dispatchers.IO)
}