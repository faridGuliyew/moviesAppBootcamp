package com.example.moviesappbootcamp.domain.use_case

import android.util.Log
import com.example.moviesappbootcamp.domain.repository.MovieRepository
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

class GetSingleMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId : Int) = movieRepository.getSingleMovie(movieId).catch {
        Log.e("get single use case", it.localizedMessage?:"error in flow")
    }
}