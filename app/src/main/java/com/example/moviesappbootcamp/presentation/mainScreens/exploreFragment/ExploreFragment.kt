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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesappbootcamp.R
import com.example.moviesappbootcamp.base.BaseFragment
import com.example.moviesappbootcamp.common.MovieType
import com.example.moviesappbootcamp.common.Resource
import com.example.moviesappbootcamp.databinding.FragmentExploreBinding
import com.example.moviesappbootcamp.domain.model.MovieLayoutModel
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
    //pagination
    private var isLoading = false
    private var isLastPage = false
    private var savedQuery = ""

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
                        isLoading = true
                        loadingDialog.show()
                    }

                    is Resource.Error -> {
                        isLoading = false
                        loadingDialog.dismiss()
                        isLastPage= true
                        fancyToast(requireContext(), it.message!!, FancyToast.ERROR)
                    }

                    is Resource.Success -> {
                        isLoading = false
                        isLastPage = false
                        loadingDialog.dismiss()
                        val movieModels = it.data!!
                        if (movieModels.isEmpty()){
                            binding.imageViewNotFound.visibility = View.VISIBLE
                            fancyToast(requireContext(),"There is no result matching your keywords",FancyToast.INFO)
                            searchAdapter.resetAdapter()
                        }else{
                            //todo move to repo or somewhere else
                            binding.imageViewNotFound.visibility = View.GONE
                            val list = ArrayList(searchAdapter.getCurrentList())
                            list.addAll(movieModels)
                            Log.e("explore", list.size.toString())
                            searchAdapter.updateAdapter(list)
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
                searchAdapter.resetAdapter()
                viewModel.resetPager()
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
        val searchRv = binding.rvExplore
        searchRv.adapter = searchAdapter

        var isScrolling = false
        searchRv.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isScrolling = true
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                if (layoutManager.findFirstVisibleItemPosition() + layoutManager.childCount >= layoutManager.itemCount && isScrolling && !isLoading && !isLastPage && savedQuery!=""){
                    viewModel.searchMovies(savedQuery)
                    isScrolling = false
                }
            }
        })
    }

}