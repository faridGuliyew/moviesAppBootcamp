package com.example.moviesappbootcamp.presentation.mainScreens.detailsFragment

import com.example.moviesappbootcamp.presentation.UiState


enum class DetailsDataType{
    MOVIE_DETAILS, CREDITS
}

sealed class DetailsUiState <out T>{

    data class Loading(val message : String) : DetailsUiState<Nothing>()
    data class Unavailable (val message : String) : DetailsUiState<Nothing>()
    data class Error (val message : String) : DetailsUiState<Nothing>()
    //todo
    data class Success <out T> (val data : T, val type : DetailsDataType) : DetailsUiState<T>()

}