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

    var binding : CustomLoadingDialogBinding
    init {
        binding = CustomLoadingDialogBinding.inflate(layoutInflater)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        binding.textViewLoading.text = loadingText
        setCancelable(cancellable)
    }

    fun setDialogText(newText : String){
        binding.textViewLoading.text = newText
        show()
    }
}