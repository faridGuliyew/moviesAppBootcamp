package com.example.moviesappbootcamp.domain.model

import com.example.moviesappbootcamp.base.diffUtil.DiffItem

data class ReviewUiModel(
    val username : String = "unknown user",
    val profilePath : String = "broken",
    val comment : String = "Great work!",
    val rating : Double = 0.0,
    val date : String = "Long time ago"
) : DiffItem() {
    override fun areItemsTheSame(newItem: DiffItem): Boolean {
        newItem as ReviewUiModel
        return newItem.profilePath == profilePath
    }

    override fun areContentsTheSame(newItem: DiffItem): Boolean {
        return this == newItem
    }
}
