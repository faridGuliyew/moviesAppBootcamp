package com.example.moviesappbootcamp.domain.use_case

import android.util.Log
import com.example.moviesappbootcamp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

class GetRecommendedMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {

    suspend operator fun invoke(movieId : Int) = movieRepository.getRecommendedMovies(movieId).catch {
        Log.e("Get recommended movies use case", "error")
    }
}