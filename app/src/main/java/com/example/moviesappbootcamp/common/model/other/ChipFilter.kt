package com.example.moviesappbootcamp.common.model.other

import android.os.Parcel
import android.os.Parcelable
import com.example.moviesappbootcamp.base.diffUtil.DiffItem
import com.example.moviesappbootcamp.common.filter.CategoryFilter
import com.example.moviesappbootcamp.common.filter.GenreFilter
import com.example.moviesappbootcamp.common.filter.RegionFilter
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class ChipFilter (val rootPath : String? = null, val secondaryPath : String? = null, val query : String? = null, val value : String? = null, val displayName : String? = null) : DiffItem(), Parcelable{
    data class Category(val filter : CategoryFilter) : ChipFilter(displayName = filter.displayName, rootPath = filter.path, secondaryPath = "popular") {
        override fun areItemsTheSame(newItem: DiffItem): Boolean {
            return true
        }

        override fun areContentsTheSame(newItem: DiffItem): Boolean {
            return false
        }
    }

    data class Region(val filter : RegionFilter) : ChipFilter(displayName = filter.displayName, query = "region", value = filter.regionCode) {
        override fun areItemsTheSame(newItem: DiffItem): Boolean {
            return true
        }

        override fun areContentsTheSame(newItem: DiffItem): Boolean {
            return false
        }
    }

    data class Genre(val filter : GenreFilter) : ChipFilter(displayName = filter.displayName, query = "genre", value = filter.code.toString()) {
        override fun areItemsTheSame(newItem: DiffItem): Boolean {
            return true
        }

        override fun areContentsTheSame(newItem: DiffItem): Boolean {
            return false
        }
    }
}
