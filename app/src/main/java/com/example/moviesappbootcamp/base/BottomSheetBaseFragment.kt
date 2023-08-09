package com.example.moviesappbootcamp.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BottomSheetBaseFragment <VB : ViewBinding> (private val layoutInflater : (LayoutInflater)->VB): BottomSheetDialogFragment() {

    private var _binding : VB? = null
    lateinit var binding : VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = layoutInflater(inflater)
        binding = _binding!!
        return binding.root
    }

    abstract fun onViewCreatedLight()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewCreatedLight()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}