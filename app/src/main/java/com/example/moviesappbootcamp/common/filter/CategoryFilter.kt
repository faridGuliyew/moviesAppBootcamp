package com.example.moviesappbootcamp.common.filter

sealed class CategoryFilter (val path : String, val displayName : String){
    object People : CategoryFilter("person", displayName = "People")
    object Movie : CategoryFilter("movie", displayName = "Movies")
    object Tv : CategoryFilter("tv", displayName = "TV Shows")
    object Collection : CategoryFilter("collection" , displayName = "Collections")
    object Company : CategoryFilter("company", displayName = "Companies")
}
