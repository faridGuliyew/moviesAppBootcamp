package com.example.moviesappbootcamp.presentation.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.moviesappbootcamp.common.utils.setImageWithGlide

@BindingAdapter("custom-loadImageWithGlide", "requirePlaceholder", requireAll = false)
fun ImageView.loadImageWithGlide(image : String?, requirePlaceholder : Boolean = true){
    setImageWithGlide(image?:"broken",requirePlaceholder)
}

@BindingAdapter("custom-ratingToText")
fun ratingToText(textView: TextView, rating : Double?) {
    val textToDisplay = if (rating!=null) "$rating.0".slice(0..2) else "not rated"
    textView.text = textToDisplay
}