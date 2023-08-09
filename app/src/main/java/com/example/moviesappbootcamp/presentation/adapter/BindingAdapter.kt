package com.example.moviesappbootcamp.presentation.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.moviesappbootcamp.utils.setImageWithGlide

@BindingAdapter("custom-loadImageWithGlide", "requirePlaceholder", requireAll = false)
fun ImageView.loadImageWithGlide(image : String?, requirePlaceholder : Boolean = true){
    setImageWithGlide(image?:"broken",requirePlaceholder)
}