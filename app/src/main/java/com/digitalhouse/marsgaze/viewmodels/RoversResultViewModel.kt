package com.digitalhouse.marsgaze.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digitalhouse.marsgaze.models.rovers.RoverResponse
import com.digitalhouse.marsgaze.services.MarsRoversPhotosService
import com.digitalhouse.marsgaze.utils.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class RoversResultViewModel(private val repository: MarsRoversPhotosService) : ViewModel() {
    val photoList = MutableLiveData<RoverResponse>()

    /**
     * Sends a message to be shown as a toast by the fragment
     */
    private val statusMessage = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = statusMessage

    fun getLatestRoverPhotos(rover: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val body = repository.getLatestPhotos(rover).body()

                viewModelScope.launch {
                    photoList.value = body
                }
            } catch (e: Exception) {
                viewModelScope.launch {
                    statusMessage.value = Event(
                        """Não foi possível carregar as imagens escolhidas"""
                    )
                }
            }
        }
    }

    fun getRoverPhotos(rover: String, sol: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getPhotos(rover, sol)

                if (response.isSuccessful && response.body()?.photos.isNullOrEmpty()) {
                    getLatestRoverPhotos(rover)
                    viewModelScope.launch {
                        statusMessage.value = Event(
                            """Não foram encontradas imagens de ${rover.capitalize(Locale.ROOT)} no Sol $sol.
                        |Alternativamente, buscamos as últimas imagens disponíveis.""".trimMargin()
                        )
                    }
                } else {
                    viewModelScope.launch {
                        photoList.value = response.body()
                    }
                }
            } catch (e: Exception) {
                viewModelScope.launch {
                    statusMessage.value = Event(
                        """Não foi possível carregar as imagens escolhidas"""
                    )
                }
            }
        }
    }

}
