package com.digitalhouse.marsgaze.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.digitalhouse.marsgaze.R
import kotlinx.android.synthetic.main.activity_exception.*

class ExceptionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exception)

        iv_backArrow_exception.setOnClickListener {
            finish()
        }
    }
}