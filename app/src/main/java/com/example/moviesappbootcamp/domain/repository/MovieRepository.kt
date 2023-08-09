package com.example.moviesappbootcamp.domain.repository

import com.example.moviesappbootcamp.common.MovieType
import com.example.moviesappbootcamp.common.Resource
import com.example.moviesappbootcamp.domain.model.MovieLayoutModel
import com.example.moviesappbootcamp.domain.model.MovieModelWithType
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun getMovies(movieType: MovieType) : Flow<Resource<MovieModelWithType>>

    suspend fun searchMovies(query : String) : Flow<Resource<List<MovieLayoutModel>>>
}