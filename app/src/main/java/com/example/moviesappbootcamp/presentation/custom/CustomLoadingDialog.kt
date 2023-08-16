package com.example.moviesappbootcamp.presentation.custom

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import android.widget.TextView
import com.example.moviesappbootcamp.R
import com.example.moviesappbootcamp.databinding.CustomLoadingDialogBinding

class CustomLoadingDialog (context : Context, layoutInflater: LayoutInflater, loadingText : String, cancellable : Boolean = true) : Dialog(context) {
    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(CustomLoadingDialogBinding.inflate(layoutInflater).root)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        findViewById<TextView>(R.id.textViewLoading).text = loadingText
        setCancelable(cancellable)
    }

    fun setDialogText(newText : String){
        findViewById<TextView>(R.id.textViewLoading).text = newText
        show()
    }
}