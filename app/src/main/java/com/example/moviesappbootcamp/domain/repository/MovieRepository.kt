package com.example.moviesappbootcamp.domain.repository

import androidx.paging.PagingData
import com.example.moviesappbootcamp.common.MovieType
import com.example.moviesappbootcamp.common.model.Resource
import com.example.moviesappbootcamp.domain.model.CreditsUiModel
import com.example.moviesappbootcamp.domain.model.MovieBriefUiModel
import com.example.moviesappbootcamp.domain.model.MovieDetailedUiModel
import com.example.moviesappbootcamp.domain.model.MovieModelWithType
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun getTopRatedMovies(movieType: MovieType) : Flow<Resource<MovieModelWithType>>

    suspend fun getUpcomingMovies(movieType: MovieType) : Flow<Resource<MovieModelWithType>>

    suspend fun getSingleMovie(movieId : Int) : Flow<Resource<MovieDetailedUiModel?>>

    suspend fun getRecommendedMovies(movieId: Int) : Flow<Resource<List<MovieBriefUiModel>>>

    suspend fun getMovieCredits(movieId : Int) : Flow<Resource<List<CreditsUiModel>?>>

    suspend fun searchMovies(query : String) : Flow<PagingData<MovieBriefUiModel>>
}