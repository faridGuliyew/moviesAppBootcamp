package com.example.moviesappbootcamp.data.remote.dto.top_rated


import com.google.gson.annotations.SerializedName

data class TopRatedResponseDto(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val resultDtos: List<ResultDto>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)