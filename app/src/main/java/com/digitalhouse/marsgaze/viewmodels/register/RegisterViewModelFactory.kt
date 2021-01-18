package com.digitalhouse.marsgaze.viewmodels.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.digitalhouse.marsgaze.controllers.user.Session

class RegisterViewModelFactory(val session: Session) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
         modelClass.getConstructor(Session::class.java).newInstance(session)
}