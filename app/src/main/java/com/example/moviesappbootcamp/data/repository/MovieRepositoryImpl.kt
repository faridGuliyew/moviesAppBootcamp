package com.example.moviesappbootcamp.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.map
import com.example.moviesappbootcamp.common.MovieType
import com.example.moviesappbootcamp.common.Resource
import com.example.moviesappbootcamp.data.remote.MovieApi
import com.example.moviesappbootcamp.data.remote.MoviePagingSource
import com.example.moviesappbootcamp.domain.model.MovieLayoutModel
import com.example.moviesappbootcamp.domain.model.MovieModelWithType
import com.example.moviesappbootcamp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi
) : MovieRepository {

    override suspend fun getMovies(movieType: MovieType): Flow<Resource<MovieModelWithType>> {
        return flow {
            emit(Resource.Loading())
            try {
                var result: MovieModelWithType
                if (movieType == MovieType.POPULAR || movieType == MovieType.TOP_RATED){
                    val request = movieApi.getTopRatedMovies(movieType.query)
                    if (request.isSuccessful){
                        result = MovieModelWithType(movieType,request.body()?.resultDtos.orEmpty().map { it.toMovieLayoutModel() })
                        emit(Resource.Success(result))
                    }else{
                        emit(Resource.Error("Internal error occurred"))
                    }
                }else{
                    val request = movieApi.getRecentMovies(movieType.query)
                    if (request.isSuccessful){
                        result = MovieModelWithType(movieType,request.body()?.resultDtos.orEmpty().map { it.toMovieLayoutModel() })
                        emit(Resource.Success(result))
                    }else{
                        emit(Resource.Error("Internal error occurred"))
                    }
                }
            }catch (e:Exception){
                emit(Resource.Error(e.localizedMessage?:"Unexpected error occurred"))
            }
        }
    }

    override suspend fun searchMovies(query: String): Flow<PagingData<MovieLayoutModel>> {
        return flow {
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
            ).flow.collect {
                emit(it.map { dto-> dto.toMovieLayoutModel() })
            }
        }
    }
}