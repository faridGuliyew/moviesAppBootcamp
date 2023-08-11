package com.example.moviesappbootcamp.di

import android.content.Context
import com.example.moviesappbootcamp.common.Constants.BASE_URL
import com.example.moviesappbootcamp.common.PrefManager
import com.example.moviesappbootcamp.data.remote.MovieApi
import com.example.moviesappbootcamp.data.repository.FirebaseRepositoryImpl
import com.example.moviesappbootcamp.data.repository.MovieRepositoryImpl
import com.example.moviesappbootcamp.domain.repository.FirebaseRepository
import com.example.moviesappbootcamp.domain.repository.MovieRepository
import com.example.moviesappbootcamp.domain.use_case.MailLoginUseCase
import com.example.moviesappbootcamp.domain.use_case.MailSignUpUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.checkerframework.checker.fenum.qual.PolyFenum
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {


    @Singleton
    @Provides
    fun provideFirebaseRepository(auth : FirebaseAuth) : FirebaseRepository{
        return FirebaseRepositoryImpl(auth)
    }

    @Singleton
    @Provides
    fun provideMovieRepository(movieApi: MovieApi) : MovieRepository{
        return MovieRepositoryImpl(movieApi)
    }

    @Singleton
    @Provides
    fun providePrefManager(@ApplicationContext context: Context) : PrefManager{
        return PrefManager(context)
    }
}