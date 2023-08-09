package com.example.moviesappbootcamp.common

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
sealed class MovieType (val query : String, val displayName : String) : Parcelable {
    object POPULAR : MovieType("popular", "Popular movies")
    object TOP_RATED : MovieType("top_rated", "Top rated movies")
    object RECENT : MovieType("upcoming","Newly released movies")
    object NOW_PLAYING : MovieType("now_playing","Movies in theaters")
}
