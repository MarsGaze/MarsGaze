package com.digitalhouse.marsgaze.ui.onboarding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.digitalhouse.marsgaze.R
import com.digitalhouse.marsgaze.viewmodels.LoginActivityViewModel

class LoginActivity : AppCompatActivity() {
    private val viewModel: LoginActivityViewModel by viewModels()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val navigator = findNavController(R.id.navHostfragment)
        navController = navigator

        if (!viewModel.firstTimeUse(this)) {
            navController.popBackStack()
            navController.navigate(R.id.loginFragment)
        }
    }
}