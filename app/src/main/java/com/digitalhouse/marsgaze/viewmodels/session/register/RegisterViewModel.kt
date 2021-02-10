package com.digitalhouse.marsgaze.viewmodels.session.register

import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteException
import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digitalhouse.marsgaze.R
import com.digitalhouse.marsgaze.controllers.user.Session
import com.digitalhouse.marsgaze.models.data.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel(val session: Session) : ViewModel() {
    val registerStatus = MutableLiveData<Pair<Boolean, Int>>()

    fun registerUser(user: User, confirmPswd: String) {
        val result = validateUser(user, confirmPswd)
        if (!result.first) {
            registerStatus.value = result
            return
        }

        Dispatchers.IO.dispatch(viewModelScope.coroutineContext) {
            var returnValue: Pair<Boolean, Int> = Pair(true, R.string.userRegistered)
            try {
                session.register(user)
            } catch (e: SQLiteConstraintException) {
                // Caso o erro de constraint aconteça deve somente ser relacionado ao email
                returnValue = Pair(false, R.string.emailAlreadyExists)
            } catch(e: SQLiteException) {
                // Algo estranho que não sabemos o que aconteceu
                returnValue = Pair(false, R.string.unknownDBError)
            }

            viewModelScope.launch {
                registerStatus.value = returnValue
            }
        }

    }

    private fun validateUser(user: User, passwordRpt: String): Pair<Boolean, Int> {
        return if (user.name.isEmpty() || user.name.isBlank()) {
            Pair(false, R.string.emptyName)
        } else if (user.email.isEmpty() || user.email.isBlank()) {
            Pair(false, R.string.emptyEmail)
        } else if (user.password.isNullOrBlank() || user.password.isNullOrEmpty()) {
            Pair(false, R.string.emptyPassword)
        } else if (passwordRpt.isEmpty() || passwordRpt.isBlank()) {
            Pair(false, R.string.emptyPasswordRepeat)
        }else if (!user.email.isEmailValid()) {
            Pair(false, R.string.invalidEmail)
        } else if (user.name.length < 2 || user.name.length > 25) {
            Pair(false, R.string.invalidName)
        } else if (user.password!!.length < 8 || user.password!!.length > 25) {
            Pair(false, R.string.invalidPassword)
        } else if (user.password != passwordRpt) {
            Pair(false, R.string.invalidPasswordRepeat)
        }else {
            Pair(true, 0)
        }
    }

    private fun String.isEmailValid(): Boolean {
        return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }
}