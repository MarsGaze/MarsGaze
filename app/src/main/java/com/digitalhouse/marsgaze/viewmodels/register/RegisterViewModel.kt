package com.digitalhouse.marsgaze.viewmodels.register

import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteException
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digitalhouse.marsgaze.R
import com.digitalhouse.marsgaze.controllers.user.Session
import com.digitalhouse.marsgaze.database.MarsGazeDB
import com.digitalhouse.marsgaze.models.data.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel(val session: Session) : ViewModel() {
    val registerStatus = MutableLiveData<Pair<Boolean, Int>>()

    fun registerUser(user: User) {
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
}