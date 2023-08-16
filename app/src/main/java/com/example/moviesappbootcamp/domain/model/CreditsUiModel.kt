package com.example.moviesappbootcamp.domain.model

import com.example.moviesappbootcamp.base.diffUtil.DiffItem

data class CreditsUiModel(
    val id : Int = 0,
    val name : String = "Unknown",
    val role : String = "Not defined",
    val profilePath : String? = "no photo"
) : DiffItem() {
    override fun areItemsTheSame(newItem: DiffItem): Boolean {
        newItem as CreditsUiModel
        return id == newItem.id
    }

    override fun areContentsTheSame(newItem: DiffItem): Boolean {
        return this == newItem
    }
}
