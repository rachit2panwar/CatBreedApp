package com.example.catapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catapp.data.models.BreedDetailModel
import com.example.catapp.data.models.CatBreedDataModel
import com.example.catapp.data.network.CatApiService
import com.example.catapp.domain.CatBreedDataUseCase
import com.example.catapp.domain.CatDetailsUseCase
import com.example.catapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatViewModel @Inject constructor(
    private val catBreedDataUseCase: CatBreedDataUseCase,
    private val catDetailsUseCase: CatDetailsUseCase
): ViewModel() {

    private val _catBreedData = MutableLiveData< Resource<List<CatBreedDataModel>>>()
    val catBreedData : LiveData<Resource<List<CatBreedDataModel>>> = _catBreedData

    private val _breedDetailsData = MutableLiveData<Resource<CatBreedDataModel>>()
    val breedDetailsData : LiveData<Resource<CatBreedDataModel>> = _breedDetailsData

    private val _sharedCatBreed = MutableLiveData<CatBreedDataModel>()
    val sharedCatBreed : LiveData<CatBreedDataModel> = _sharedCatBreed


     fun getCatBreedData() {
         viewModelScope.launch(Dispatchers.IO)  {
             val response = catBreedDataUseCase.getCatBreedData()
             _catBreedData.postValue(response)
         }
    }

     fun getBreedDetailsData(id: String) {
         viewModelScope.launch(Dispatchers.IO) {
             val response = catDetailsUseCase.getBreedDetailsData(id)
             _breedDetailsData.postValue(response)
         }
    }

    fun setCatBread(breedDetailModel: CatBreedDataModel) {
        _sharedCatBreed.postValue(breedDetailModel)
    }
}