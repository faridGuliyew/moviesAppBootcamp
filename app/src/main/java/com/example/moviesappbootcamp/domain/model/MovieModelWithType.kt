package com.example.moviesappbootcamp.domain.model

import com.example.moviesappbootcamp.common.MovieType
import com.example.moviesappbootcamp.data.remote.dto.top_rated.TopRatedResponseDto
import com.example.moviesappbootcamp.data.remote.dto.upcoming.UpcomingMoviesResponseDto

data class MovieModelWithType(
    val movieType: MovieType,
    val movieBriefUiModels: List<MovieBriefUiModel>
)

data class NetworkTopRatedMovieModelWithType(
    val movieType: MovieType,
    val response: TopRatedResponseDto?
)

data class NetworkUpcomingMovieModelWithType(
    val movieType: MovieType,
    val response: UpcomingMoviesResponseDto?
)