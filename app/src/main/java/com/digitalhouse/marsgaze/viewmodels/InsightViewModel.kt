package com.digitalhouse.marsgaze.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digitalhouse.marsgaze.controllers.service.InsightController
import com.digitalhouse.marsgaze.models.insight.InsightInfo
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception

class InsightViewModel(private val controller: InsightController) : ViewModel() {
    val insightResponse = MutableLiveData<ArrayList<InsightInfo>>()

    fun getInsightInfo() {
        viewModelScope.launch {
            try {
                val resp = controller.getInsight(true)
                if (resp != null && resp.isSuccessful) {
                    insightResponse.value = resp.body()
                }
            } catch (e: Exception) {
                // TODO: Não devemos pegar exceções no gerais aqui...

            }
        }

    }

}
