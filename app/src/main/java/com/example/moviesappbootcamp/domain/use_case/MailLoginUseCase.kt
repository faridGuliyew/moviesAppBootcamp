package com.example.moviesappbootcamp.domain.use_case

import com.example.moviesappbootcamp.domain.repository.FirebaseRepository
import javax.inject.Inject

class MailLoginUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) {

    suspend operator fun invoke(mail : String ,password : String) = firebaseRepository.mailLogin(mail, password)
}