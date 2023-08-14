package com.example.moviesappbootcamp.common.filter

sealed class RegionFilter (val regionCode : String, val displayName : String){
    object US : RegionFilter("US", displayName = "United States")
    object GERMANY : RegionFilter("DE", displayName = "Germany")
}