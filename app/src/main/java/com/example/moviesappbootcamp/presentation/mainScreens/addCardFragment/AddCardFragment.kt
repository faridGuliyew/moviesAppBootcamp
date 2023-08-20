package com.example.moviesappbootcamp.presentation.mainScreens.addCardFragment

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moviesappbootcamp.R
import com.example.moviesappbootcamp.base.BaseFragment
import com.example.moviesappbootcamp.databinding.FragmentAddCardBinding
import java.util.Calendar


class AddCardFragment : BaseFragment<FragmentAddCardBinding>(FragmentAddCardBinding::inflate) {
    override fun onViewCreatedLight() {

        openCalendar()
    }

    private fun openCalendar(){
        binding.buttonCalendar.setOnClickListener {
            val datePicker = DatePickerDialog(requireContext())
            datePicker.setOnDateSetListener { datePicker, year, month, day ->
                binding.buttonCalendar.text = "$day/$month/$year"
            }
            datePicker.show()
        }
    }

}