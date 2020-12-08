package com.digitalhouse.marsgaze.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digitalhouse.marsgaze.models.hubble.HubbleResponse
import com.digitalhouse.marsgaze.services.HubbleService
import kotlinx.coroutines.launch

class HubbleViewModel(private val service: HubbleService) : ViewModel() {
    val hubbleImgList = MutableLiveData<HubbleResponse>()

    fun getHubbleImg() {
        viewModelScope.launch {
            hubbleImgList.value = service.getHubbleImg()
        }
    }
}