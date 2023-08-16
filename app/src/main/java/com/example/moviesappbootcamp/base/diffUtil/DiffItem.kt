package com.example.moviesappbootcamp.base.diffUtil

abstract class DiffItem  {
    abstract fun areItemsTheSame(newItem : DiffItem) : Boolean

    abstract fun areContentsTheSame(newItem : DiffItem) : Boolean
    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }
}