package com.example.moviesappbootcamp.common.filter

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class RegionFilter (val regionCode : String, val displayName : String) : Parcelable{
    object US : RegionFilter("US", displayName = "United States")
    object GERMANY : RegionFilter("DE", displayName = "Germany")
}