package com.digitalhouse.marsgaze.viewmodels.session

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.digitalhouse.marsgaze.controllers.user.Session

class SessionViewModelFactory(val session: Session) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(session::class.java).newInstance(session)
    }
}