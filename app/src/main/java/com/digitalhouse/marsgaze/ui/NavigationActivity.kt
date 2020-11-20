package com.digitalhouse.marsgaze.ui

import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import com.digitalhouse.marsgaze.R
import kotlinx.android.synthetic.main.navigation_drawer.*
import kotlinx.coroutines.delay
import java.lang.Thread.sleep
import kotlin.concurrent.thread

class NavigationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.navigation_drawer)
    }
}