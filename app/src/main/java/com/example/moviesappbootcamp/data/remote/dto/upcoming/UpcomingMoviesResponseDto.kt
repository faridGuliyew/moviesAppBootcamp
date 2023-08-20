package com.example.moviesappbootcamp.data.remote.dto.upcoming


import com.google.gson.annotations.SerializedName

data class UpcomingMoviesResponseDto(
    @SerializedName("dates")
    val datesDto: DatesDto,
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val upcomingResultDtos: List<UpcomingResultDto>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)