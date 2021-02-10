package com.digitalhouse.marsgaze.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.digitalhouse.marsgaze.controllers.service.InsightController
import com.digitalhouse.marsgaze.controllers.user.Session
import com.digitalhouse.marsgaze.database.AfterFavoriteAction
import com.digitalhouse.marsgaze.database.MarsGazeDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.SocketException
import java.net.SocketOptions
import java.net.UnknownHostException

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
     * @param insightController insight controller para realizar a chamada do cache
     *                          insight controller to do the cache call
     */
    fun cacheInsight(insightController: InsightController) {
        // Mantém o cache mesmo após esse view model ser destruido
        GlobalScope.launch(Dispatchers.IO) {
            try {
                insightController.cacheInsight()
            } catch (e: Exception) {
                Log.e(
                    "Cache Insight", "Não foi possível realizar a chamada do insight. " +
                            "Razão: ${e.message}"
                )
            }
        }
    }

    /**
     * PT-BR
     * Inicia diversas classes na splash
     *
     * EN-US
     * Initializes lots of classes in the splash
     *
     * @param insightController insight controller para realizar a chamada do cache
     *                          insight controller to do the cache call
     */
    fun startMemoryClasses(context: Context) {
        // Mantém o cache mesmo após esse view model ser destruido
        GlobalScope.launch(Dispatchers.Default) {
            Session.getInstance(
                MarsGazeDB.getDatabase(
                    context
                ),
                AfterFavoriteAction(
                    context
                )
            )
        }
    }
}