package com.example.moviesappbootcamp.domain.model

import com.example.moviesappbootcamp.base.diffUtil.DiffItem


//Data class to be used in every movie item (common data will be stored)

data class MovieBriefUiModel(
    val movieId : Int = 0,
    val movieName : String? = "Unknown",
    val moviePoster : String? = "broken",
    val movieBackdrop : String? = "broken",
    val movieOverview : String? = "Overview is not available",
    val movieRating : Double?
) : DiffItem() {
    override fun areItemsTheSame(newItem: DiffItem): Boolean {
        newItem as MovieBriefUiModel
        return movieId == newItem.movieId
    }

    override fun areContentsTheSame(newItem: DiffItem): Boolean {
        newItem as MovieBriefUiModel
        return this == newItem
    }
}