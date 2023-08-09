package com.example.moviesappbootcamp.presentation.mainScreens.exploreFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.moviesappbootcamp.R
import com.example.moviesappbootcamp.base.BaseFragment
import com.example.moviesappbootcamp.common.MovieType
import com.example.moviesappbootcamp.common.Resource
import com.example.moviesappbootcamp.databinding.FragmentExploreBinding
import com.example.moviesappbootcamp.presentation.adapter.rv.MovieBigAdapter
import com.example.moviesappbootcamp.presentation.custom.CustomLoadingDialog
import com.example.moviesappbootcamp.presentation.mainScreens.filterFragment.FilterFragment
import com.example.moviesappbootcamp.utils.fancyToast
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ExploreFragment : BaseFragment<FragmentExploreBinding>(FragmentExploreBinding::inflate) {

    private val viewModel by viewModels<ExploreViewModel>()
    private val searchAdapter = MovieBigAdapter()

    override fun onViewCreatedLight() {
        observe()
        setRv()
        setSearch()
        goToFilter()
    }

    private fun observe() {
        val loadingDialog =
            CustomLoadingDialog(requireContext(), layoutInflater, "Please wait...", true)
        with(viewModel) {
            result.onEach {
                when (it) {
                    is Resource.Loading -> {
                        loadingDialog.show()
                    }

                    is Resource.Error -> {
                        loadingDialog.dismiss()
                        fancyToast(requireContext(), it.message!!, FancyToast.ERROR)
                    }

                    is Resource.Success -> {
                        loadingDialog.dismiss()
                        val movieModels = it.data!!
                        if (movieModels.isEmpty()){
                            binding.imageViewNotFound.visibility = View.VISIBLE
                            fancyToast(requireContext(),"There is no result matching your keywords",FancyToast.INFO)
                        }else{
                            binding.imageViewNotFound.visibility = View.GONE
                        }
                        searchAdapter.updateAdapter(movieModels)
                    }
                    else -> {/*No emission is observed*/
                    }
                }
            }.launchIn(lifecycleScope)
        }
    }

    private fun setSearch(){
        binding.editTextText.setOnEditorActionListener { textView, i, keyEvent ->
            if (i == EditorInfo.IME_ACTION_SEARCH){
                val query = binding.editTextText.text.toString().trim()
                if (query.isNotEmpty()) viewModel.searchMovies(query) else viewModel.defaultMovies()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    private fun goToFilter(){
        binding.buttonFilter.setOnClickListener {
            FilterFragment().show(childFragmentManager,"FilterFragment")
        }
    }

    private fun setRv(){
        val searchRv = binding.rvExplore
        searchRv.adapter = searchAdapter
    }

}