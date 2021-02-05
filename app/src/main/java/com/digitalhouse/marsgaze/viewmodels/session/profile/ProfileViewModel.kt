package com.digitalhouse.marsgaze.viewmodels.session.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digitalhouse.marsgaze.R
import com.digitalhouse.marsgaze.controllers.user.Session
import com.digitalhouse.marsgaze.models.data.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ProfileViewModel(private val session: Session) : ViewModel() {
    val userUpdateStatus = MutableLiveData<Pair<Boolean, Int>>()

    val userFavorites = MutableLiveData<Int>()

    fun getUser(): User {
        return try {
            session.user()
        } catch (e: Session.NoUserWasLoggedIn) {
            // Dados padrões caso não tenha um usuário logado
            User(
                "Not logged",
                "Not logged",
                "",
                "00/00/0000"
            )
        }
    }

    fun updateUser(user: User) {
        Dispatchers.IO.dispatch(viewModelScope.coroutineContext) {
            var result = Pair(true, R.string.updateUserOk)

            try {
                session.update(user)
            } catch (e: Session.ForbiddenAction) {
                result = Pair(false, R.string.updateUserDifferent)
            } catch (e: Session.NoUserWasLoggedIn) {
                result = Pair(false, R.string.updateUserNoUserLogged)
            }

            viewModelScope.launch {
                userUpdateStatus.value = result
            }
        }
    }

    fun getUserFavorites() {
        if (session.isLogged()) {
            viewModelScope.launch(Dispatchers.IO) {
                val size = session.getAllFavorites().size

                viewModelScope.launch {
                    userFavorites.value = size
                }
            }
        } else {
            userFavorites.value = Int.MAX_VALUE
        }
    }
}