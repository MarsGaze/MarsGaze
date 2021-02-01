package com.digitalhouse.marsgaze.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digitalhouse.marsgaze.models.hubble.HubbleResponse
import com.digitalhouse.marsgaze.models.hubble.Item
import com.digitalhouse.marsgaze.models.hubble.PhotoCollection
import com.digitalhouse.marsgaze.services.HubbleService
import kotlinx.coroutines.launch

class HubbleViewModel(private val service: HubbleService) : ViewModel() {
    val hubbleImgList = MutableLiveData<HubbleResponse>()

    fun getHubbleImg() {
        viewModelScope.launch {
            val result = HubbleResponse()
            result.collection.items.apply {
                addAll(service.getHubbleImg("hubble mars").collection.items)
                addAll(service.getHubbleImg(title = "odyssey all stars").collection.items)
                addAll(service.getHubbleImg("mgs", "Mars at Ls").collection.items)
                addAll(service.getHubbleImg("viking", "Center is at Latitude").collection.items)
            }
            result.collection.items.shuffle()
            hubbleImgList.value = result
        }
    }
}