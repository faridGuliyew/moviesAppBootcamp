package com.example.moviesappbootcamp.presentation.mainScreens.profileFragment.logOutFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.moviesappbootcamp.R
import com.example.moviesappbootcamp.base.BottomSheetBaseFragment
import com.example.moviesappbootcamp.common.PrefManager
import com.example.moviesappbootcamp.common.utils.fancyToast
import com.example.moviesappbootcamp.databinding.FragmentLogOutBinding
import com.example.moviesappbootcamp.presentation.UiState
import com.example.moviesappbootcamp.presentation.custom.CustomLoadingDialog
import com.shashank.sony.fancytoastlib.FancyToast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LogOutFragment : BottomSheetBaseFragment<FragmentLogOutBinding>(FragmentLogOutBinding::inflate) {

    private val viewModel by viewModels<LogOutViewModel>()
    @Inject
    lateinit var prefManager: PrefManager
    override fun onViewCreatedLight() {

        cancel()
        logOut()
        observe()
    }

    private fun observe(){
        val loadingDialog = CustomLoadingDialog(requireContext(),layoutInflater,"Logging out...")
        with(viewModel){
            state.observe(viewLifecycleOwner){
                when(it){
                    is UiState.Loading-> loadingDialog.show()
                    is UiState.Success-> {
                        fancyToast(requireContext(),"Logged out successfully", FancyToast.SUCCESS)
                        prefManager.uid = null
                        requireActivity().finish()
                    }
                    else->{}
                }
            }
        }
    }

    private fun cancel(){
        binding.buttonCancel.setOnClickListener {
            dismiss()
        }
    }
    private fun logOut(){
        binding.buttonLogOut.setOnClickListener {
            viewModel.logout()
        }
    }
}