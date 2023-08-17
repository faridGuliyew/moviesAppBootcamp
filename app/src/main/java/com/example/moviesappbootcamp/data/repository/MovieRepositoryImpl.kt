package com.example.moviesappbootcamp.data.repository

import android.util.Log
import androidx.paging.PagingData
import androidx.paging.map
import com.example.moviesappbootcamp.common.MovieType
import com.example.moviesappbootcamp.common.model.NetworkState
import com.example.moviesappbootcamp.common.model.Resource
import com.example.moviesappbootcamp.data.mapper.toBriefUiModel
import com.example.moviesappbootcamp.data.mapper.toBriefUiModels
import com.example.moviesappbootcamp.data.mapper.toBrieffUiModels
import com.example.moviesappbootcamp.data.mapper.toCreditsUiModel
import com.example.moviesappbootcamp.data.mapper.toDetailedUiModel
import com.example.moviesappbootcamp.data.remote.MovieApi
import com.example.moviesappbootcamp.data.source.remote.RemoteSource
import com.example.moviesappbootcamp.domain.model.CreditsUiModel
import com.example.moviesappbootcamp.domain.model.MovieBriefUiModel
import com.example.moviesappbootcamp.domain.model.MovieDetailedUiModel
import com.example.moviesappbootcamp.domain.model.MovieModelWithType
import com.example.moviesappbootcamp.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
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
            remoteSource.getTopRatedMovies(movieType).collect {
                when (it) {
                    is NetworkState.Success -> {
                        emit(
                            Resource.Success(
                                MovieModelWithType(
                                    movieType,
                                    it.data?.response?.resultDtos?.toBriefUiModels()
                                        .orEmpty()
                                )
                            )
                        )
                    }

                    is NetworkState.Error -> {
                        emit(Resource.Error(it.errorMessage))
                    }
                }
            }
        }.flowOn(Dispatchers.IO).catch {
            Log.e(TAG, "getTopRatedMovies: error")
        }

    override suspend fun getUpcomingMovies(movieType: MovieType) =
        flow {
            emit(Resource.Loading)
            remoteSource.getUpcomingMovies(movieType).collect {
                when (it) {
                    is NetworkState.Success -> {
                        emit(
                            Resource.Success(
                                MovieModelWithType(
                                    movieType,
                                    it.data?.response?.resultDtos?.toBrieffUiModels()
                                        .orEmpty()
                                )
                            )
                        )
                    }

                    is NetworkState.Error -> {
                        emit(Resource.Error(it.errorMessage))
                    }
                }
            }
        }.flowOn(Dispatchers.IO).catch {
            Log.e(TAG, "getUpcomingMovies: error")
        }

    override suspend fun getSingleMovie(movieId: Int) = flow {

        emit(Resource.Loading)
        remoteSource.getSingleMovie(movieId).collect{
            when(it){
                is NetworkState.Success->{
                    val data = it.data
                    emit(Resource.Success(data?.toDetailedUiModel()))
                }
                is NetworkState.Error->{
                    emit(Resource.Error(it.errorMessage))
                }
            }
        }
    }

    override suspend fun getRecommendedMovies(movieId: Int) = flow {

        emit(Resource.Loading)

        remoteSource.getRecommendedMovies(movieId)
            .catch {
                Log.e(TAG, "getRecommendedMovies: error")
            }.collect{
                when(it){
                    is NetworkState.Error-> emit(Resource.Error(it.errorMessage))
                    is NetworkState.Success->{
                        emit(Resource.Success(it.data?.resultDtos?.toBriefUiModels().orEmpty()))
                    }
                }
            }
    }

    override suspend fun getMovieCredits(movieId: Int) = flow {
        emit(Resource.Loading)
        remoteSource.getMovieCredits(movieId).collect{
            when(it){
                is NetworkState.Success->{
                    val data = it.data
                    emit(Resource.Success(data?.cast?.toCreditsUiModel()))
                }
                is NetworkState.Error->{
                    emit(Resource.Error(it.errorMessage))
                }
            }
        }

    }

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