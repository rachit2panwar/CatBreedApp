package com.example.catapp.data.repository

import com.example.catapp.data.models.CatBreedDataModel
import com.example.catapp.utils.Resource

interface ICatBreedRepository {
    suspend fun getCatBreedData() : Resource<List<CatBreedDataModel>>

    suspend fun getBreedDetails(id: String) : Resource<CatBreedDataModel>
}