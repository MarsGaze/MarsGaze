package com.digitalhouse.marsgaze.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digitalhouse.marsgaze.controllers.service.InsightController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {
    /**
     * PT-BR
     * Roda o cache separado já que ele pode demorar mais do que o necessario além de possíveis
     * exceções. Falhas são ignoradas aqui já que seriam usadas somente no cache.
     *
     * EN-US
     * Runs the cache separately from the main thread as it may take more time than necessary and
     * cause exceptions, which, are ignored here.
     *
     * @param InsightController insight controller para realizar a chamada do cache
     *                          insight controller to do the cache call
     */
    fun cacheInsight(insightController: InsightController) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                insightController.cacheInsight()
            } catch (e: Exception) {
                Log.e("Cache Insight", "Não foi possível realizar a chamada do insight. " +
                        "Razão: ${e.message}")
            }
        }
    }
}