package com.example.catapp.data.repository

import com.example.catapp.data.models.CatBreedDataModel
import com.example.catapp.data.network.CatApiService
import com.example.catapp.utils.Resource
import javax.inject.Inject

class CatBreedRepositoryImpl @Inject constructor(
    private val apiService: CatApiService
): ICatBreedRepository{

    override suspend fun getCatBreedData(): Resource<List<CatBreedDataModel>> {
        val data = apiService.getCatBreed()

        return if(data.isSuccessful){
            data.body()?.let { Resource.Success(data = it) } ?: Resource.Error("Response body is null")
        } else {
            Resource.Error(data.message(), null)
        }
    }

    override suspend fun getBreedDetails(id: String): Resource<CatBreedDataModel> {
        val data = apiService.getBreedDetails(id)

        return if(data.isSuccessful){
            data.body()?.let { Resource.Success(data = it) } ?: Resource.Error("Response body is null")
        } else {
            Resource.Error(data.message(), null)
        }
    }

}