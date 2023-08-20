package com.example.moviesappbootcamp.presentation.mainScreens.detailsFragment.giveRatingFragment


import android.graphics.Color
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.moviesappbootcamp.base.BottomSheetBaseFragment
import com.example.moviesappbootcamp.common.utils.fancyToast
import com.example.moviesappbootcamp.databinding.FragmentGiveRatingBinding
import com.example.moviesappbootcamp.presentation.custom.CustomLoadingDialog
import com.shashank.sony.fancytoastlib.FancyToast
import com.taufiqrahman.reviewratings.BarLabels
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Random


class GiveRatingFragment : BottomSheetBaseFragment<FragmentGiveRatingBinding>(FragmentGiveRatingBinding::inflate) {

    private val args by navArgs<GiveRatingFragmentArgs>()

    override fun onViewCreatedLight() {
        binding.movie = args.movie
        setReviews()
        setButtons()
    }

    private fun setReviews(){
        val ratingReviews = binding.ratingReviews


        val minValue = 5
        val rawRating = args.movie.movieRating?:0.0
        val movieRating = if (rawRating > 0.5) rawRating.toInt() * 10 - 1 else 5

        val raters = intArrayOf(
            (movieRating .. 100).random(),
            (minValue..100).random(),
            (minValue..100-movieRating).random(),
            (minValue..100-movieRating).random(),
            (minValue..100-movieRating).random()
        )


        ratingReviews.createRatingBars(100, BarLabels.STYPE3, Color.parseColor("#D50000"), raters)
    }

    private fun setButtons(){
        binding.buttonCancel.setOnClickListener {
            dismiss()
        }
        binding.buttonSubmit.setOnClickListener {
            if (binding.ratingBar2.rating != 0f){
                val loadingDialog = CustomLoadingDialog(requireContext(),layoutInflater,"Submitting your vote...")
                lifecycleScope.launch {
                    loadingDialog.show()
                    delay(2500)
                    loadingDialog.dismiss()
                    fancyToast(requireContext(),"Your vote is submitted successfully",FancyToast.SUCCESS)
                    dismiss()
                }
            }else{
                fancyToast(requireContext(),"Select a rating first",FancyToast.INFO)
            }
        }
    }
}