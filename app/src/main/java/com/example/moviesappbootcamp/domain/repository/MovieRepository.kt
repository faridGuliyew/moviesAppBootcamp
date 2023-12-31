package com.example.moviesappbootcamp.domain.repository

import androidx.paging.PagingData
import com.example.moviesappbootcamp.common.MovieType
import com.example.moviesappbootcamp.common.model.data.Resource
import com.example.moviesappbootcamp.domain.model.CreditsUiModel
import com.example.moviesappbootcamp.domain.model.MovieBriefUiModel
import com.example.moviesappbootcamp.domain.model.MovieDetailedUiModel
import com.example.moviesappbootcamp.domain.model.MovieModelWithType
import com.example.moviesappbootcamp.domain.model.ReviewUiModel
import com.example.moviesappbootcamp.domain.model.TrailerUiModel
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun getTopRatedMovies(movieType: MovieType) : Flow<Resource<MovieModelWithType>>

    suspend fun getUpcomingMovies(movieType: MovieType) : Flow<Resource<MovieModelWithType>>

    suspend fun getSingleMovie(movieId : Int) : Flow<Resource<MovieDetailedUiModel?>>

    suspend fun getVideos(movieId: Int) : Flow<Resource<List<TrailerUiModel>>>
    suspend fun getRecommendedMovies(movieId: Int) : Flow<Resource<List<MovieBriefUiModel>>>

    suspend fun getMovieCredits(movieId : Int) : Flow<Resource<List<CreditsUiModel>?>>

    suspend fun getReviews(movieId: Int) : Flow<Resource<List<ReviewUiModel>>>

    suspend fun searchMovies(query : String) : Flow<PagingData<MovieBriefUiModel>>
}