package com.example.moviesappbootcamp.domain.repository

import com.example.moviesappbootcamp.common.model.Resource
import kotlinx.coroutines.flow.Flow

interface FirebaseRepository {

    suspend fun mailSignUp(mail : String, password : String) : Flow<Resource<String>>

    suspend fun mailLogin(mail: String, password: String) : Flow<Resource<String>>
}