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
import com.google.android.gms.dynamic.IFragmentWrapper
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ExploreFragment : BaseFragment<FragmentExploreBinding>(FragmentExploreBinding::inflate) {

    private val viewModel by viewModels<ExploreViewModel>()
    private val searchAdapter = MovieBigAdapter()
    private val loadingDialog by lazy {
        CustomLoadingDialog(requireContext(), layoutInflater, "Please wait...", true)
    }
    private lateinit var searchRv : RecyclerView

    private val pagingAdapter = SearchPagingAdapter()
    //
    private var savedQuery = ""

    override fun onViewCreatedLight() {
        searchRv =  binding.rvExplore
        observe()
        setRv()
        setSearch()
        goToFilter()
    }

    private fun observe() {
        with(viewModel) {
            pagingData.onEach {
                if (searchRv.adapter !is SearchPagingAdapter){
                    searchRv.adapter = pagingAdapter
                }
                it?.let {
                    pagingAdapter.submitData(it)
                }
            }.launchIn(lifecycleScope)

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
                        searchRv.adapter = searchAdapter
                        val movieModels = it.data!!
                        if (movieModels.isEmpty()){
                            //
                            binding.imageViewNotFound.visibility = View.VISIBLE
                            fancyToast(requireContext(),"There is no result matching your keywords",FancyToast.INFO)
                        }else{
                            //todo move to repo or somewhere else
                            binding.imageViewNotFound.visibility = View.GONE
                            searchAdapter.differ.submitList(movieModels)
                        }
                    }
                    else -> {/*No emission is observed*/
                    }
                }
            }.launchIn(lifecycleScope)
        }
    }

    private fun setSearch(){
        binding.editTextText.setOnEditorActionListener { _, i, _ ->
            if (i == EditorInfo.IME_ACTION_SEARCH){
                val query = binding.editTextText.text.toString().trim()
                savedQuery = query
                if (query.isNotEmpty()){
                    viewModel.searchMovies(query)
                }  else {
                    viewModel.defaultMovies()
                }
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

        pagingAdapter.addLoadStateListener {loadState->
            when(loadState.source.refresh){
                is LoadState.Error->{
                    fancyToast(requireContext(),"Something went wrong",FancyToast.ERROR)
                }else->{

                }
            }
        }
    }
}