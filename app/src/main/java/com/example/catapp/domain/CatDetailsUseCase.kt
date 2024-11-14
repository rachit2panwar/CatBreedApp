package com.example.catapp.domain

import com.example.catapp.data.models.CatBreedDataModel
import com.example.catapp.data.repository.ICatBreedRepository
import com.example.catapp.utils.Resource
import javax.inject.Inject

class CatDetailsUseCase @Inject constructor(
    private val repository: ICatBreedRepository
) {

    suspend fun getBreedDetailsData(id: String): Resource<CatBreedDataModel> {
        return repository.getBreedDetails(id)
    }

}