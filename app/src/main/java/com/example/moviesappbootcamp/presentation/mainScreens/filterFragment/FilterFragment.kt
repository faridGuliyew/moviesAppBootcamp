package com.example.moviesappbootcamp.presentation.mainScreens.filterFragment

import com.example.moviesappbootcamp.base.BottomSheetBaseFragment
import com.example.moviesappbootcamp.common.model.other.ChipFilter
import com.example.moviesappbootcamp.common.filter.CategoryFilter
import com.example.moviesappbootcamp.common.filter.GenreFilter
import com.example.moviesappbootcamp.common.filter.RegionFilter
import com.example.moviesappbootcamp.databinding.FragmentFilterBinding
import com.example.moviesappbootcamp.presentation.adapter.rv.FilterChipAdapter

class FilterFragment : BottomSheetBaseFragment<FragmentFilterBinding>(FragmentFilterBinding::inflate) {

    private val categoryAdapter = FilterChipAdapter()
    private val regionsAdapter = FilterChipAdapter()
    private val genresAdapter = FilterChipAdapter()
    override fun onViewCreatedLight() {
        setRvs()
        apply()
        reset()
    }


    private fun setRvs(){
        val categoryRv = binding.rvCategories
        categoryRv.adapter = categoryAdapter

        categoryAdapter.updateAdapter(listOf(
            ChipFilter.Category(CategoryFilter.Movie),
            ChipFilter.Category(CategoryFilter.People),
            ChipFilter.Category(CategoryFilter.Tv),
            ChipFilter.Category(CategoryFilter.Collection),
            ChipFilter.Category(CategoryFilter.Company)))

        val regionsRv = binding.rvRegions
        regionsRv.adapter = regionsAdapter

        regionsAdapter.updateAdapter(listOf(
            ChipFilter.Region(RegionFilter.US),
            ChipFilter.Region(RegionFilter.GERMANY)))

        val genresRv = binding.rvGenres
        genresRv.adapter = genresAdapter

        //
        genresAdapter.updateAdapter(listOf(
            ChipFilter.Genre(GenreFilter.Action),
            ChipFilter.Genre(GenreFilter.Adventure),
            ChipFilter.Genre(GenreFilter.Animation),
            ChipFilter.Genre(GenreFilter.Documentary),
            ChipFilter.Genre(GenreFilter.Drama),
            ChipFilter.Genre(GenreFilter.Comedy),
            ChipFilter.Genre(GenreFilter.Science_Fiction),
            ChipFilter.Genre(GenreFilter.Family),
            ChipFilter.Genre(GenreFilter.Fantasy),
            ChipFilter.Genre(GenreFilter.Western),
            ChipFilter.Genre(GenreFilter.War),
            ChipFilter.Genre(GenreFilter.Thriller),
            ChipFilter.Genre(GenreFilter.Horror),
            ChipFilter.Genre(GenreFilter.Romance),
            ChipFilter.Genre(GenreFilter.Mystery),
            ChipFilter.Genre(GenreFilter.Crime)))
    }

    private fun apply(){
        binding.buttonApply.setOnClickListener {
            this.dismiss()
        }
    }
    private fun reset(){
        binding.buttonBack.setOnClickListener {
            categoryAdapter.reset()
            regionsAdapter.reset()
            genresAdapter.reset()
        }
    }
}