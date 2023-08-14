package com.example.moviesappbootcamp.utils

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.TransitionOptions
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.moviesappbootcamp.R
import com.example.moviesappbootcamp.common.Constants.IMAGE_BASE_URL

fun ImageView.setImageWithGlide(image : String, requirePlaceholder : Boolean = true){
    if (requirePlaceholder){
        Glide.with(this)
            .load(IMAGE_BASE_URL + image)
            .into(this)
    }else{
        Glide.with(this)
            .load(IMAGE_BASE_URL + image)
            .placeholder(R.drawable.img_placeholder)
            .error(R.drawable.img_placeholder)
            .into(this)
    }
}

fun View.visible(){
    visibility = View.VISIBLE
}
fun View.gone(){
    visibility = View.GONE
}