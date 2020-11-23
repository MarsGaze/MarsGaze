package com.digitalhouse.marsgaze.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.digitalhouse.marsgaze.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btncallException.setOnClickListener {
            callException()
        }

        btncallCurio.setOnClickListener {
            callCuriosidades()
        }
    }

    fun callException() {
        var intent = Intent(this, ExceptionActivity::class.java)

        startActivity(intent)
    }

    fun callCuriosidades() {
        var intent = Intent(this, CuriosidadesFragment::class.java)

        startActivity(intent)
    }
}