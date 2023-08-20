package com.example.moviesappbootcamp.common.model.other

import android.graphics.drawable.Drawable
import com.example.moviesappbootcamp.base.diffUtil.DiffItem

data class PreferencesModel(
    val icon : Drawable?,
    val label : String,
    val additionalText : String = ""
) : DiffItem() {
    override fun areItemsTheSame(newItem: DiffItem): Boolean {
        newItem as PreferencesModel
        return label == newItem.label
    }

    override fun areContentsTheSame(newItem: DiffItem): Boolean {
        return newItem == this
    }
}
