package com.example.moviesappbootcamp.common.utils

import android.content.Context
import com.shashank.sony.fancytoastlib.FancyToast

fun fancyToast(context : Context, message : String, type : Int, isDurationLong : Boolean = true){
    val duration = if(isDurationLong) FancyToast.LENGTH_LONG else FancyToast.LENGTH_SHORT
    FancyToast.makeText(context,message,duration, type,false).show()
}

fun releaseDateToYear(date : String?) : Int{
    val nullSafeDate = date ?: "1990-"
    return nullSafeDate.split("-")[0].toInt()
}