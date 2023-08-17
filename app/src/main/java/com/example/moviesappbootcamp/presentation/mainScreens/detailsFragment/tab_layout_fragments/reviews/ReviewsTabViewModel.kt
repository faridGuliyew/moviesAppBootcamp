package com.example.moviesappbootcamp.presentation.mainScreens.detailsFragment.tab_layout_fragments.reviews

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesappbootcamp.common.model.Resource
import com.example.moviesappbootcamp.domain.model.MovieBriefUiModel
import com.example.moviesappbootcamp.domain.model.ReviewUiModel
import com.example.moviesappbootcamp.domain.use_case.GetReviewsUseCase
import com.example.moviesappbootcamp.presentation.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewsTabViewModel @Inject constructor(
    private val getReviewsUseCase: GetReviewsUseCase
)  : ViewModel(){

    private val _result = MutableLiveData<UiState<List<ReviewUiModel>>>()
    val result : LiveData<UiState<List<ReviewUiModel>>>
        get() = _result


    fun getReviews(movieId : Int){
        viewModelScope.launch {
            getReviewsUseCase(movieId).collect {
                when(it){
                    is Resource.Loading -> _result.value = UiState.Loading
                    is Resource.Error -> _result.value = UiState.Error(it.message)
                    is Resource.Success -> _result.value = UiState.Success(it.data)
                }
            }
        }
    }
}