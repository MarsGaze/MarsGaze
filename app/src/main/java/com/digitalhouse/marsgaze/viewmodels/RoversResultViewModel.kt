package com.digitalhouse.marsgaze.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digitalhouse.marsgaze.models.rovers.RoverResponse
import com.digitalhouse.marsgaze.services.MarsRoversPhotosService
import kotlinx.coroutines.launch

class RoversResultViewModel(private val repository: MarsRoversPhotosService) : ViewModel() {
    val photoList = MutableLiveData<RoverResponse>()

    fun getLatestRoverPhotos(rover: String) {
        viewModelScope.launch {
            photoList.value = repository.getLatestPhotos(rover)
        }
    }

    fun getRoverPhotos(rover: String, sol: Int) {
        viewModelScope.launch {
            photoList.value = repository.getPhotos(rover, sol)
        }
    }
}
