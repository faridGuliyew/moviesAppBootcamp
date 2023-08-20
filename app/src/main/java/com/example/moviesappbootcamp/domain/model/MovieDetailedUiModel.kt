package com.example.moviesappbootcamp.domain.model

import android.os.Parcelable
import com.example.moviesappbootcamp.common.model.other.ChipFilter
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDetailedUiModel (
    val movieId : Int? = 0,
    val movieName : String? = "Unknown",
    val moviePoster : String? = "broken",
    val movieBackdrop : String? = "broken",
    val movieOverview : String? = "Overview is not available",
    val movieRating : Double? = 0.0,
    val voteCount : Int = 0,
    val movieReleaseYear : Int = 1990,
    val movieGenres : List<ChipFilter> = emptyList()
) : Parcelable