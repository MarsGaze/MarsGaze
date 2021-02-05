package com.digitalhouse.marsgaze.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digitalhouse.marsgaze.controllers.service.InsightController
import com.digitalhouse.marsgaze.models.insight.InsightInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class InsightViewModel(private val controller: InsightController) : ViewModel() {
    val insightResponse = MutableLiveData<ArrayList<InsightInfo>>()

    fun getInsightInfo() {
        viewModelScope.launch {

            try {
                val job = controller.jobInsight()
                if (job == null) {
                    Log.i("CALL", "Doing it")
                    val resp = controller.getInsight(true)
                    if (resp != null && resp.isSuccessful) {
                        insightResponse.value = resp.body()
                    }
                } else if (job.isCompleted && controller.getInsight(true)!!.isSuccessful) {
                    Log.i("CALL", "Cached")
                    insightResponse.value = controller.getInsight(true)!!.body()
                } else if (!job.isCompleted && controller.getInsight(true)!!.isSuccessful) {
                    GlobalScope.launch(Dispatchers.IO) {
                        controller.getInsight()
                        val job = controller.jobInsight()
                        if (job == null) {
                            Log.e("CALL", "No job was done")
                        } else {
                            job.invokeOnCompletion {
                                getInsightInfo()
                            }
                        }
                    }
                } else {
                    job.invokeOnCompletion {
                        Log.i("CALL", "Wait till done")
                        getInsightInfo()
                    }
                }
            } catch (e: Exception) {
                // TODO: Não devemos pegar exceções no gerais aqui...
                // Tentamos fazer novamente a conexão
                getInsightInfo()
            }
        }

    }

}
