package com.example.moviesappbootcamp.base.diffUtil

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

class DiffCallbackBase<T: DiffItem> : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.areItemsTheSame(newItem)
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.areContentsTheSame(newItem)
    }
}