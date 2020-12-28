package com.digitalhouse.marsgaze.viewmodels.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.digitalhouse.marsgaze.controllers.user.Session

class ProfileViewModelFactory(private val session: Session) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(Session::class.java).newInstance(session)
    }
}