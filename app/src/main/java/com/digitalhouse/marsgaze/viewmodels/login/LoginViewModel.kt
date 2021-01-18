package com.digitalhouse.marsgaze.viewmodels.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digitalhouse.marsgaze.R
import com.digitalhouse.marsgaze.controllers.user.Session
import com.digitalhouse.marsgaze.models.data.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class LoginViewModel(val session: Session) : ViewModel() {
    val loginStatus = MutableLiveData<Pair<Boolean, Int>>()

    fun loginUser(user: User) {
        Dispatchers.IO.dispatch(viewModelScope.coroutineContext) {
            val result = session.login(user)

            var message = 0
            if (!result) {
                message = R.string.userLoginInvalid
            }

            viewModelScope.launch {
                loginStatus.value = Pair(result, message)
            }
        }
    }
}