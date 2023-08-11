package com.example.moviesappbootcamp.domain.use_case

import com.example.moviesappbootcamp.domain.repository.MovieRepository
import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {

    suspend operator fun invoke(query : String, page : Int) = movieRepository.searchMovies(query, page)
}