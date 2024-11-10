package com.example.catapp.data.network

import com.example.catapp.BuildConfig
import com.example.catapp.common.Constants
import com.example.catapp.data.models.CatBreedDataModel
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface CatApiService {

    @Headers("x-api-key: ${BuildConfig.API_KEY}")
    @GET(Constants.BREEDS)
    suspend fun getCatBreed(): List<CatBreedDataModel>

    @Headers("x-api-key: ${BuildConfig.API_KEY}")
    @GET(Constants.BREEDS_DETAILS)
    suspend fun getBreedDetails(
        @Path("breed_id") breedId: String
    ): CatBreedDataModel
}