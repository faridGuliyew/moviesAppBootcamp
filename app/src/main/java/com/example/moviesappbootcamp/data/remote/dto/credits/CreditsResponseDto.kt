package com.example.moviesappbootcamp.data.remote.dto.credits


import com.google.gson.annotations.SerializedName

data class CreditsResponseDto(
    @SerializedName("cast")
    val cast: List<Cast>,
    @SerializedName("crew")
    val crew: List<Crew>,
    @SerializedName("id")
    val id: Int
)