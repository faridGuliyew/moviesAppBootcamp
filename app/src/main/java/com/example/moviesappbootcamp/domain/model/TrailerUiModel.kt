package com.example.moviesappbootcamp.domain.model

import com.example.moviesappbootcamp.base.diffUtil.DiffItem
import com.google.gson.annotations.SerializedName

data class TrailerUiModel(
    @SerializedName("id")
    val id: String,
    @SerializedName("key")
    val key: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("site")
    val site: String
) : DiffItem() {
    override fun areItemsTheSame(newItem: DiffItem): Boolean {
        newItem as TrailerUiModel
        return id == newItem.id
    }

    override fun areContentsTheSame(newItem: DiffItem): Boolean {
        return this == newItem
    }
}
