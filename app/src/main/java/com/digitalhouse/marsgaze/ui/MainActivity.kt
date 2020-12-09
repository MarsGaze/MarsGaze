package com.digitalhouse.marsgaze.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.digitalhouse.marsgaze.R
import com.digitalhouse.marsgaze.services.serviceWeather
import com.digitalhouse.marsgaze.viewmodels.InsightViewModel

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModels<InsightViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return InsightViewModel(serviceWeather) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.getWeather()
        viewModel.weather.observe(this, Observer {
            it.forEach {
                Log.i("Sol number", "${it.key}")
                Log.i("Sol info", "${it.value.First_UTC}")

            }
        })
    }
}