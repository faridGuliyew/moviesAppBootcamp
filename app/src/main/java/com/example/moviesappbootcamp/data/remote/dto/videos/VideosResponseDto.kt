package com.example.moviesappbootcamp.data.remote.dto.videos


import com.google.gson.annotations.SerializedName

data class VideosResponseDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("results")
    val videoResults: List<VideoResult>
)