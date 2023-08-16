package com.example.moviesappbootcamp.domain.model

import com.example.moviesappbootcamp.data.remote.dto.single.Genre

data class MovieDetailedUiModel (
    val movieId : Int? = 0,
    val movieName : String? = "Unknown",
    val moviePoster : String? = "broken",
    val movieBackdrop : String? = "broken",
    val movieOverview : String? = "Overview is not available",
    val movieRating : Double? = 0.0,
    val voteCount : Int = 0,
    val movieReleaseYear : Int = 1990,
    val movieGenres : List<Genre> = emptyList()
)