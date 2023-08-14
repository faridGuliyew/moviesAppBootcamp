package com.example.moviesappbootcamp.domain.model

import android.os.Parcelable
import com.example.moviesappbootcamp.base.diffUtil.DiffItem
import kotlinx.parcelize.Parcelize


//Data class to be used in every movie item (common data will be stored)

data class MovieLayoutModel(
    val movieId : Int? = 0,
    val movieName : String? = "Unknown",
    val moviePoster : String? = "broken",
    val movieBackdrop : String? = "broken",
    val movieOverview : String? = "Overview is not available",
    val movieRating : Double? = 0.0
) : DiffItem() {
    override fun areItemsTheSame(newItem: DiffItem): Boolean {
        newItem as MovieLayoutModel
        return movieId == newItem.movieId
    }

    override fun areContentsTheSame(newItem: DiffItem): Boolean {
        newItem as MovieLayoutModel
        return this == newItem
    }
}