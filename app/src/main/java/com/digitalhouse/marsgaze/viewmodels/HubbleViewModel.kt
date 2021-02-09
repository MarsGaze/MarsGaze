package com.digitalhouse.marsgaze.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digitalhouse.marsgaze.models.hubble.HubbleResponse
import com.digitalhouse.marsgaze.services.HubbleService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class HubbleViewModel(private val service: HubbleService) : ViewModel() {
    val hubbleImgList = MutableLiveData<HubbleResponse>()

    fun getHubbleImg() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = HubbleResponse()
            result.collection.items.apply {
                try {
                    addAll(service.getHubbleImg("hubble mars").collection.items)
                    addAll(service.getHubbleImg(title = "odyssey all stars").collection.items)
                    addAll(service.getHubbleImg("mgs", "Mars at Ls").collection.items)
                    addAll(service.getHubbleImg("viking", "Center is at Latitude").collection.items)
                } catch (e: Exception) {
                    Log.e("HUBBLE", "Not possible to load images")
                }
            }
            result.collection.items.shuffle()

            viewModelScope.launch {
                hubbleImgList.value = result
            }
        }
    }
}