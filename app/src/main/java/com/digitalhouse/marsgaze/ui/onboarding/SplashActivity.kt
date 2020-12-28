package com.digitalhouse.marsgaze.ui.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.digitalhouse.marsgaze.R
import com.digitalhouse.marsgaze.controllers.service.InsightController
import com.digitalhouse.marsgaze.database.MarsGazeDB
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.Exception

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        GlobalScope.launch {
            delay(5000)

            startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
            // Limpamos o nosso planeta também, ok?
            finish()
        }

        // Roda o cache separado já que ele pode demorar mais do que o necessario além de possíveis
        // exceções.
        GlobalScope.launch {
            try {
                InsightController.getController().cacheInsight()
            } catch (e: Exception) {
                Log.e("Cache Insight", "Não foi possível realizar a chamada do insight. " +
                                                "Razão: ${e.message}")
            }
        }

        val test = MarsGazeDB.getDatabase(this)
        val testDao = test.favoriteDAO()
    }
}