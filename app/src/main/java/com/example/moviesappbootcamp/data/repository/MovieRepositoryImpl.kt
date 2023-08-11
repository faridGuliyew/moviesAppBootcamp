package com.example.moviesappbootcamp.data.repository

import android.util.Log
import com.example.moviesappbootcamp.common.MovieType
import com.example.moviesappbootcamp.common.Resource
import com.example.moviesappbootcamp.data.remote.MovieApi
import com.example.moviesappbootcamp.domain.model.MovieLayoutModelsWithTotalPage
import com.example.moviesappbootcamp.domain.model.MovieModelWithType
import com.example.moviesappbootcamp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi
) : MovieRepository {

    override suspend fun getMovies(movieType: MovieType): Flow<Resource<MovieModelWithType>> {
        return flow {
            emit(Resource.Loading())
            Log.e("repo input",movieType.toString())
            try {
                val result = if (movieType == MovieType.POPULAR || movieType == MovieType.TOP_RATED){
                    MovieModelWithType(movieType,movieApi.getTopRatedMovies(movieType.query).body()!!.resultDtos.map { it.toMovieLayoutModel() })
                }else{
                    MovieModelWithType(movieType,movieApi.getRecentMovies(movieType.query).body()!!.resultDtos.map { it.toMovieLayoutModel() })
                }
                Log.e("repo output",result.movieType.toString())
                emit(Resource.Success(result))
            }catch (e:Exception){
                emit(Resource.Error(e.localizedMessage?:"Unexpected error occurred"))
            }
        }
    }

    override suspend fun searchMovies(query: String, page : Int): Flow<Resource<MovieLayoutModelsWithTotalPage>> {
        return flow {
            emit(Resource.Loading())
            try {
                val result = movieApi.searchMovie(query,page).body()!!
                emit(Resource.Success(MovieLayoutModelsWithTotalPage(result.resultDtos.map { it.toMovieLayoutModel() },result.totalPages)))
            }catch (e:Exception){
                emit(Resource.Error(message = e.localizedMessage?:"Unexpected error occurred"))
            }
        }
    }
}