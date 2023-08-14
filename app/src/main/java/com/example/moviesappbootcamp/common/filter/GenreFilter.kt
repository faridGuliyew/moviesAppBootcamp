package com.example.moviesappbootcamp.common.filter

sealed class GenreFilter (val displayName : String, val code : Int){
    object Action : GenreFilter("Action", 28)
    object Adventure : GenreFilter("Adventure", 12)
    object Animation : GenreFilter("Animation", 16)
    object Comedy : GenreFilter("Comedy", 35)
    object Crime : GenreFilter("Crime", 80)
    object Documentary : GenreFilter("Documentary", 99)
    object Mystery : GenreFilter("Mystery", 9648)
    object Romance : GenreFilter("Romance", 10749)
    object Science_Fiction : GenreFilter("Science Fiction", 878)
    object Thriller : GenreFilter("Thriller", 53)
    object Drama : GenreFilter("Drama", 18)
    object Family : GenreFilter("Family", 10751)
    object War : GenreFilter("War", 10752)
    object Western : GenreFilter("Western", 37)
    object Fantasy : GenreFilter("Fantasy", 14)
    object Horror : GenreFilter("Horror", 27)
}