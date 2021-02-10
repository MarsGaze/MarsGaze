package com.digitalhouse.marsgaze.ui.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.digitalhouse.marsgaze.R
import com.digitalhouse.marsgaze.controllers.service.InsightController
import com.digitalhouse.marsgaze.viewmodels.SplashViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
        viewModel.startMemoryClasses(this)
    }
}