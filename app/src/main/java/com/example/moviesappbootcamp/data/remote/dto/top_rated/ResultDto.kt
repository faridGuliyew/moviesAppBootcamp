package com.example.moviesappbootcamp.data.remote.dto.top_rated


import com.example.moviesappbootcamp.domain.model.MovieLayoutModel
import com.google.gson.annotations.SerializedName


data class ResultDto(
    @SerializedName("adult")
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @SerializedName("genre_ids")
    val genreIds: List<Int>,
    @SerializedName("id")
    val id: Int,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_title")
    val originalTitle: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("popularity")
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("video")
    val video: Boolean,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int,
) {
    fun toMovieLayoutModel() : MovieLayoutModel {
        return MovieLayoutModel(
            movieId = id,
            movieName = originalTitle,
            moviePoster = posterPath,
            movieBackdrop = backdropPath,
            movieOverview = overview,
            movieRating = voteAverage
        )
    }
}