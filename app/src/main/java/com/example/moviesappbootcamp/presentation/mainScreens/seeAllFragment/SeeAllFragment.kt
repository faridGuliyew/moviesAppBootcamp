package com.example.moviesappbootcamp.presentation.mainScreens.seeAllFragment

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.moviesappbootcamp.base.BaseFragment
import com.example.moviesappbootcamp.common.model.Resource
import com.example.moviesappbootcamp.databinding.FragmentSeeAllBinding
import com.example.moviesappbootcamp.presentation.adapter.rv.MovieBigAdapter
import com.example.moviesappbootcamp.presentation.custom.CustomLoadingDialog
import com.example.moviesappbootcamp.common.utils.fancyToast
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SeeAllFragment : BaseFragment<FragmentSeeAllBinding>(FragmentSeeAllBinding::inflate) {

    private val viewModel by viewModels<SeeAllViewModel>()
    private val args by navArgs<SeeAllFragmentArgs>()
    private val seeAllAdapter = MovieBigAdapter()

    override fun onViewCreatedLight() {
        observe()
        setRv()
        goBack()
    }

    private fun observe() {
        binding.textViewTitle.text = args.movieType.displayName
        viewModel.getMovies(args.movieType)
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
                        val movieModels = it.data!!.movieBriefUiModels
                        seeAllAdapter.differ.submitList(movieModels)
                    }
                    else -> {/*No emission is observed*/
                    }
                }
            }.launchIn(lifecycleScope)
        }
    }

    private fun goBack(){
        binding.buttonBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setRv(){
        val rv = binding.rvResult
        rv.adapter = seeAllAdapter
    }

}