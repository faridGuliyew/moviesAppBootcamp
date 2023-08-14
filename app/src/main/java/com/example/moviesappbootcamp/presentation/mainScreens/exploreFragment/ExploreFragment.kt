package com.example.moviesappbootcamp.presentation.mainScreens.exploreFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.AbsListView
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesappbootcamp.R
import com.example.moviesappbootcamp.base.BaseFragment
import com.example.moviesappbootcamp.common.MovieType
import com.example.moviesappbootcamp.common.Resource
import com.example.moviesappbootcamp.databinding.FragmentExploreBinding
import com.example.moviesappbootcamp.domain.model.MovieLayoutModel
import com.example.moviesappbootcamp.presentation.adapter.rv.MovieBigAdapter
import com.example.moviesappbootcamp.presentation.adapter.rv.SearchPagingAdapter
import com.example.moviesappbootcamp.presentation.custom.CustomLoadingDialog
import com.example.moviesappbootcamp.presentation.mainScreens.filterFragment.FilterFragment
import com.example.moviesappbootcamp.utils.fancyToast
import com.example.moviesappbootcamp.utils.gone
import com.example.moviesappbootcamp.utils.visible
import com.google.android.gms.dynamic.IFragmentWrapper
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ExploreFragment : BaseFragment<FragmentExploreBinding>(FragmentExploreBinding::inflate) {

    private val viewModel by viewModels<ExploreViewModel>()
    private val recommendedAdapter = MovieBigAdapter()
    private val loadingDialog by lazy {
        CustomLoadingDialog(requireContext(), layoutInflater, "Please wait...", true)
    }

    private val pagingAdapter = SearchPagingAdapter()

    //
    private var savedQuery = ""

    override fun onViewCreatedLight() {
        observe()
        setRv()
        setSearch()
        goToFilter()
    }

    private fun observe() {
        with(viewModel) {
            pagingData.observe(viewLifecycleOwner) {
                lifecycleScope.launch {
                    pagingAdapter.submitData(it)
                }
            }
            uiState.observe(viewLifecycleOwner){
                when(it){
                    is ExploreUiState.Loading->{
                        loadingDialog.show()
                    }
                    is ExploreUiState.Error->{
                        loadingDialog.dismiss()
                        fancyToast(requireContext(), "Something went wrong. Please check your internet connectivity", FancyToast.ERROR)
                    }
                    is ExploreUiState.Success->{
                        recommendedAdapter.differ.submitList(it.data)
                        loadingDialog.dismiss()
                    }
                }
            }
        }
    }

    private fun setSearch() {
        val searchLayout = binding.searchLayout
        val recommendedLayout = binding.recommendedLayout
        val notFoundImageView = binding.imageViewNotFound

        binding.editTextText.setOnEditorActionListener { _, i, _ ->
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                savedQuery = binding.editTextText.text.toString().trim()

                if (savedQuery.isNotEmpty()) {
                    viewModel.searchMovies(savedQuery)
                    searchLayout.visible()
                    recommendedLayout.gone()
                } else {
                    viewModel.defaultMovies()
                    searchLayout.gone()
                    notFoundImageView.gone()
                    recommendedLayout.visible()
                }
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    private fun goToFilter() {
        binding.buttonFilter.setOnClickListener {
            FilterFragment().show(childFragmentManager, "FilterFragment")
        }
    }

    private fun setRv() {
        val recommendedRv = binding.recommendedRv
        recommendedRv.adapter = recommendedAdapter

        val searchRv = binding.searchRv

        val notFoundImageView = binding.imageViewNotFound

        searchRv.adapter = pagingAdapter

        pagingAdapter.addLoadStateListener { loadState ->
            if (loadState.append.endOfPaginationReached) {
                if (pagingAdapter.itemCount < 1) {
                    notFoundImageView.visible()
                } else {
                    notFoundImageView.gone()
                }
            } else {
                notFoundImageView.gone()
            }
            when (loadState.source.refresh) {
                is LoadState.Error -> {
                    fancyToast(requireContext(), "Something went wrong", FancyToast.ERROR)
                }

                is LoadState.Loading -> {
                    loadingDialog.show()
                }

                else -> {
                    loadingDialog.dismiss()
                }
            }
        }
    }
}