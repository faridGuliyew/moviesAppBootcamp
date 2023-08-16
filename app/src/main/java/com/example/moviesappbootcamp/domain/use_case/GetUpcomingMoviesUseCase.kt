package com.example.moviesappbootcamp.domain.use_case

import com.example.moviesappbootcamp.common.MovieType
import com.example.moviesappbootcamp.domain.repository.MovieRepository
import javax.inject.Inject

class GetUpcomingMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {

    suspend operator fun invoke(movieType: MovieType) = movieRepository.getUpcomingMovies(movieType)
}