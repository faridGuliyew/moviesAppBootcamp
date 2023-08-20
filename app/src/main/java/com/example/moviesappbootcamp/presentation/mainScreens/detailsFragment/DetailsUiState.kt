package com.example.moviesappbootcamp.presentation.mainScreens.detailsFragment

import com.example.moviesappbootcamp.presentation.UiState

sealed class DetailsUiState <out T>{

    data class Loading(val message : String) : DetailsUiState<Nothing>()
    data class Unavailable (val message : String) : DetailsUiState<Nothing>()
    data class Error (val message : String) : DetailsUiState<Nothing>()
    data class SuccessCredits <out T> (val data : T) : DetailsUiState<T>()
    data class SuccessDetails <out T> (val data : T) : DetailsUiState<T>()

}