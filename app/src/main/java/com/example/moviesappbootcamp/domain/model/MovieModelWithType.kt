package com.example.moviesappbootcamp.domain.model

import com.example.moviesappbootcamp.common.MovieType

data class MovieModelWithType(
    val movieType: MovieType,
    val movieLayoutModels: List<MovieLayoutModel>
)