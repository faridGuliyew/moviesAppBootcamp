package com.example.moviesappbootcamp.presentation.adapter

import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.moviesappbootcamp.common.utils.setImageWithGlide
import com.google.android.material.button.MaterialButton

@BindingAdapter("custom-loadImageWithGlide", "requirePlaceholder", requireAll = false)
fun ImageView.loadImageWithGlide(image : String?, requirePlaceholder : Boolean = true){
    setImageWithGlide(image?:"broken",requirePlaceholder)
}

@BindingAdapter("custom-loadIconWithResource")
fun loadIconWithResource(materialButton: MaterialButton, resource : Drawable){
    materialButton.icon = resource
}

@BindingAdapter("custom-ratingToText")
fun ratingToText(textView: TextView, rating : Double?) {
    val textToDisplay = if (rating!=null) "$rating.0".slice(0..2) else "not rated"
    textView.text = textToDisplay
}

@BindingAdapter("custom-dateToReadableText")
fun dateToReadableText(textView: TextView, date : String){
    val sliced = date.split("-")
    if (sliced.isNotEmpty()){
        textView.text =  sliced[0]
    }else{
        textView.text = date
    }
}

@BindingAdapter("custom-loadTrailerThumbnail")
fun loadTrailerThumbnail(imageView : ImageView, key : String?){
    imageView.setImageWithGlide("https://img.youtube.com/vi/$key/hqdefault.jpg")
}