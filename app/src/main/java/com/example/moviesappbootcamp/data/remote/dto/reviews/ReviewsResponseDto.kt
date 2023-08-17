package com.example.moviesappbootcamp.data.remote.dto.reviews


import com.google.gson.annotations.SerializedName

data class ReviewsResponseDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<Result>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)