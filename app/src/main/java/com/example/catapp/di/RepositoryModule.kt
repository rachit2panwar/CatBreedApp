package com.example.catapp.di

import com.example.catapp.data.repository.CatBreedRepositoryImpl
import com.example.catapp.data.repository.ICatBreedRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindCatBreedRepository(
        impl: CatBreedRepositoryImpl
    ): ICatBreedRepository
}
