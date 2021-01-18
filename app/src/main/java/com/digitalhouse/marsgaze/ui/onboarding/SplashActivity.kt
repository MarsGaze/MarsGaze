package com.digitalhouse.marsgaze.ui.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.digitalhouse.marsgaze.R
import com.digitalhouse.marsgaze.controllers.service.InsightController
import com.digitalhouse.marsgaze.viewmodels.SplashViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.Exception

class SplashActivity : AppCompatActivity() {
    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        GlobalScope.launch {
            delay(5000)

            startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
            // Limpamos o nosso planeta também, ok?
            finish()
        }

        viewModel.cacheInsight(InsightController.getController())
    }
}