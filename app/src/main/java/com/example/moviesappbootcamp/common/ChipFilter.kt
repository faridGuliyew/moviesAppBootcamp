package com.example.moviesappbootcamp.common

import com.example.moviesappbootcamp.common.filter.CategoryFilter
import com.example.moviesappbootcamp.common.filter.GenreFilter
import com.example.moviesappbootcamp.common.filter.RegionFilter

sealed class ChipFilter (val rootPath : String? = null, val secondaryPath : String? = null, val query : String? = null, val value : String? = null, val displayName : String? = null){
    data class Category(val filter : CategoryFilter) : ChipFilter(displayName = filter.displayName, rootPath = filter.path, secondaryPath = "popular")
    data class Region(val filter : RegionFilter) : ChipFilter(displayName = filter.displayName, query = "region", value = filter.regionCode)
    data class Genre(val filter : GenreFilter) : ChipFilter(displayName = filter.displayName, query = "genre", value = filter.code.toString())
}
