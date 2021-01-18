package com.digitalhouse.marsgaze.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digitalhouse.marsgaze.controllers.service.InsightController
import com.digitalhouse.marsgaze.models.insight.InsightInfo
import kotlinx.coroutines.launch

class InsightViewModel(private val controller: InsightController) : ViewModel() {
    val insightResponse = MutableLiveData<ArrayList<InsightInfo>>()

    fun getInsightInfo() {
        viewModelScope.launch {

            try {
                val job = controller.jobInsight()
                if (job == null) {
                    val resp = controller.getInsight(true)
                    if (resp != null && resp.isSuccessful) {
                        insightResponse.value = resp.body()
                    }
                } else if (job.isCompleted && controller.getInsight(true)!!.isSuccessful) {
                    insightResponse.value = controller.getInsight(true)!!.body()
                } else {
                    job.invokeOnCompletion {
                        getInsightInfo()
                    }
                }
            } catch (e: Exception) {
                // TODO: Não devemos pegar exceções no gerais aqui...

            }
        }

    }

}
