package com.example.moviesappbootcamp.domain.use_case

import com.example.moviesappbootcamp.common.MovieType
import com.example.moviesappbootcamp.common.Resource
import com.example.moviesappbootcamp.domain.model.MovieLayoutModel
import com.example.moviesappbootcamp.domain.model.MovieModelWithType
import com.example.moviesappbootcamp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {

    suspend operator fun invoke (movieType: MovieType) : Flow<Resource<MovieModelWithType>> = movieRepository.getMovies(movieType)
}