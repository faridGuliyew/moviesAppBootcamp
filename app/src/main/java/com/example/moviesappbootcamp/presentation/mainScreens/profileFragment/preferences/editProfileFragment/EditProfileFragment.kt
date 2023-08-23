package com.example.moviesappbootcamp.presentation.mainScreens.profileFragment.preferences.editProfileFragment

import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.example.moviesappbootcamp.base.BaseFragment
import com.example.moviesappbootcamp.databinding.FragmentEditProfileBinding

class EditProfileFragment : BaseFragment<FragmentEditProfileBinding>(FragmentEditProfileBinding::inflate) {
    override fun onViewCreatedLight() {

        photoPicker()
    }

    private fun photoPicker(){
        val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            uri?.let{
                binding.imageView8.setImageURI(it)
            }
        }

        binding.button6.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    }

}