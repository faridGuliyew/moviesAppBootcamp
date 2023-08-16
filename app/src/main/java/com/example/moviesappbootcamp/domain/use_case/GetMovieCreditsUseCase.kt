package com.example.moviesappbootcamp.domain.use_case

import com.example.moviesappbootcamp.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovieCreditsUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {

    suspend operator fun invoke(movieId : Int) = movieRepository.getMovieCredits(movieId)
}