package com.digitalhouse.marsgaze.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.digitalhouse.marsgaze.databinding.ActivityExceptionBinding

class ExceptionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExceptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExceptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBackArrowException.setOnClickListener {
            finish()
        }
    }
}