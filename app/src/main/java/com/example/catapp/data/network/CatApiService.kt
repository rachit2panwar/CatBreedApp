package com.example.catapp.data.network

import com.example.catapp.data.models.CatBreedDataModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CatApiService {
    @GET("v1/breeds")
    suspend fun getCatBreed(): Response<List<CatBreedDataModel>>

    @GET("v1/breeds/{breed_id}")
    suspend fun getBreedDetails(
        @Path("breed_id") breedId: String
    ): Response<CatBreedDataModel>
}