package com.digitalhouse.marsgaze.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digitalhouse.marsgaze.models.rovers.RoverResponse
import com.digitalhouse.marsgaze.services.MarsRoversPhotosService
import com.digitalhouse.marsgaze.utils.Event
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
        viewModelScope.launch {
            photoList.value = repository.getLatestPhotos(rover).body()
        }
    }

    fun getRoverPhotos(rover: String, sol: String) {
        viewModelScope.launch {
            val response = repository.getPhotos(rover, sol)

            if (response.isSuccessful && response.body()?.photos.isNullOrEmpty()) {
                getLatestRoverPhotos(rover)
                statusMessage.value = Event(
                    """Não foram encontradas imagens de ${rover.capitalize(Locale.ROOT)} no Sol $sol.
                        |Alternativamente, buscamos as últimas imagens disponíveis.""".trimMargin()
                )
            } else photoList.value = response.body()
        }
    }

}
