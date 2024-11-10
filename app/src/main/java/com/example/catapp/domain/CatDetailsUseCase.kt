package com.example.catapp.domain

import com.example.catapp.data.models.CatBreedDataModel
import com.example.catapp.data.network.CatApiService
import javax.inject.Inject

class CatDetailsUseCase @Inject constructor(
    private val client: CatApiService
) {

    suspend fun getBreedDetailsData(id: String): CatBreedDataModel {
        return client.getBreedDetails(id)
    }

}