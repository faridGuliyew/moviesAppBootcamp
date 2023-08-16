package com.example.moviesappbootcamp.domain.use_case

import com.example.moviesappbootcamp.common.MovieType
import com.example.moviesappbootcamp.common.model.Resource
import com.example.moviesappbootcamp.domain.model.MovieModelWithType
import com.example.moviesappbootcamp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTopRatedMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {

    suspend operator fun invoke (movieType: MovieType) = movieRepository.getTopRatedMovies(movieType)
}