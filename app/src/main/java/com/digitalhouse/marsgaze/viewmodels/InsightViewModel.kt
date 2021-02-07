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
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val job = controller.jobInsight()
                if (job == null) {
                    Log.i("CALL", "Doing it")
                    try {
                        val resp = controller.getInsight(true)

                        if (resp != null && resp.isSuccessful) {
                            viewModelScope.launch {
                                insightResponse.value = resp.body()
                            }
                        }
                    } catch (e: Exception) {
                        Log.e("INSIGHT", "Not possible to get insight result")
                    }
                } else if (job.isCompleted && controller.getInsight(true)!!.isSuccessful) {
                    Log.i("CALL", "Cached")
                    viewModelScope.launch {
                        insightResponse.value = controller.getInsight(true)!!.body()
                    }
                } else if (!job.isCompleted && controller.getInsight(true)!!.isSuccessful) {
                    controller.getInsight()
                    val jobT = controller.jobInsight()
                    if (jobT == null) {
                        Log.e("CALL", "No job was done")
                    } else {
                        jobT.invokeOnCompletion {
                            getInsightInfo()
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
