package com.digitalhouse.marsgaze.viewmodels.session.image

import com.digitalhouse.marsgaze.models.favorite.ImageDetailAdapter
import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digitalhouse.marsgaze.R
import com.digitalhouse.marsgaze.controllers.user.Session
import com.digitalhouse.marsgaze.models.data.FavoriteTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.SQLException

class ImageDetailViewModel(val session: Session) : ViewModel() {
    val favoriteStatus = MutableLiveData<Pair<Boolean, Int>>()

    val isFavorite = MutableLiveData<Boolean>()

    private var inFavorite: FavoriteTest? = null

    /**
     * PT-BR
     * Favorita a imagem
     *
     * @see Session.isFavorited para mais informações
     *
     * EN-US
     * Favorites the image
     *
     * @see Session.isFavorited for more info
     *
     * @param Rover Entidade do rover
     *              Rover entity
     */
    fun isFavorited(adapter: ImageDetailAdapter) {
        viewModelScope.launch(Dispatchers.IO) {
            val favoriteTmp = session.isFavorited(
                adapter.toFavorite(session.user())
            )

            inFavorite = favoriteTmp

            viewModelScope.launch {
                isFavorite.value = favoriteTmp != null
            }
        }
    }

    fun favoriteAction(adapter: ImageDetailAdapter) {
        viewModelScope.launch(Dispatchers.IO) {
            if (!session.isLogged()) {
                favoriteStatus.value = Pair(false, R.string.updateUserNoUserLogged)
                return@launch
            }

            if (inFavorite != null && isFavorite.value != null && isFavorite.value!!) {
                removeFavoriteRoverImage(inFavorite!!)
            } else {
                favoriteRoverImage(adapter)
            }
        }
    }


    private fun favoriteRoverImage(favorite: ImageDetailAdapter) {
        var message = Pair(true, R.string.favoriteAdded)

        try {
            val id = session.addFavorite(
                favorite
            )

            viewModelScope.launch {
                isFavorite.value = true
            }

            inFavorite = FavoriteTest(
                id,
                imageType = favorite.getType(),
                imageId = favorite.getId(),
                user = session.user().email,
                favorite.getImg()
            )
        } catch (ex: SQLiteConstraintException) {
            message = Pair(false, R.string.favoriteException)
        } catch (ex: SQLException) {
            message = Pair(false, R.string.favoriteException)
        }

        viewModelScope.launch {
            favoriteStatus.value = message
        }
    }

    private fun removeFavoriteRoverImage(favorite: FavoriteTest) {
        var message = Pair(true, R.string.favoriteRemoved)
        try {
            session.removeFavorite(
                favorite
            )

            viewModelScope.launch {
                isFavorite.value = false
            }
        } catch (ex: SQLiteConstraintException) {
            message = Pair(false, R.string.favoriteException)
        } catch (ex: SQLException) {
            message = Pair(false, R.string.favoriteException)
        }

        viewModelScope.launch {
            favoriteStatus.value = message
        }
    }


}