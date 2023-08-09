package com.example.moviesappbootcamp.data.repository

import com.example.moviesappbootcamp.common.Resource
import com.example.moviesappbootcamp.domain.repository.FirebaseRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor (
    private val auth : FirebaseAuth
) : FirebaseRepository {

    override suspend fun mailSignUp(mail: String, password: String): Flow<Resource<String>> {
        return flow {
            emit(Resource.Loading())
            try {
                val request = auth.createUserWithEmailAndPassword(mail,password).await()
                emit(Resource.Success(data = request.user!!.uid))
            }catch (e:Exception){
                emit(Resource.Error(e.localizedMessage?:"Unexpected error occurred"))
            }
        }
    }

    override suspend fun mailLogin(mail: String, password: String): Flow<Resource<String>> {
        return flow {
            emit(Resource.Loading())
            try {
                val request = auth.signInWithEmailAndPassword(mail,password).await()
                emit(Resource.Success(data = request.user!!.uid))
            }catch (e:Exception){
                emit(Resource.Error(e.localizedMessage?:"Unexpected error occurred"))
            }
        }
    }
}