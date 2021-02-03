package com.digitalhouse.marsgaze.viewmodels.session.favorite

import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digitalhouse.marsgaze.R
import com.digitalhouse.marsgaze.controllers.user.Session
import com.digitalhouse.marsgaze.database.AfterFavoriteAction
import com.digitalhouse.marsgaze.models.data.FavoriteTest
import com.digitalhouse.marsgaze.models.data.FavoriteType
import com.digitalhouse.marsgaze.models.favorite.ImageDetailAdapter
import com.digitalhouse.marsgaze.models.hubble.Item
import com.digitalhouse.marsgaze.models.rovers.RoverPhoto
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.SQLException

class FavoriteViewModel(private val session: Session) : ViewModel() {
    val status = MutableLiveData<Pair<Boolean, Int>>()

    val favorites = MutableLiveData<MutableList<ImageDetailAdapter>>()


    fun favoriteAction(adapter: ImageDetailAdapter, afterSuccessAction: (Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val inFavorite = session.isFavorited(adapter.toFavorite(session.user()))
            val isFavorited = inFavorite != null

            if (!session.isLogged()) {
                status.value = Pair(false, R.string.updateUserNoUserLogged)
                return@launch
            }

            if (inFavorite != null && isFavorited) {
                removeFavoriteRoverImage(inFavorite, afterSuccessAction)
            } else {
                favoriteRoverImage(adapter, afterSuccessAction)
            }
        }
    }

    fun isFavorited(adapter: ImageDetailAdapter, afterSuccessAction: (Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            val inFavorite = session.isFavorited(adapter.toFavorite(session.user()))
            val isFavorited = inFavorite != null

            afterSuccessAction(isFavorited)
        }
    }

    fun getAllFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            // TODO: Define good message
            var message = Pair(true, 0)

            try {
                val data = session.getAllFavorites()

                val convertedData = mutableListOf<ImageDetailAdapter>()

                for (fav in data) {
                    val type = FavoriteType.values()[fav.imageType]


                    val file = session.getFavoriteFile(fav.imageId, type)

                     if (file.exists()) {
                         val text = file.readText()

                         lateinit var adapter: ImageDetailAdapter
                         when (fav.imageType) {
                             FavoriteType.ROVERS_IMAGE.ordinal -> {
                                 adapter = Gson().fromJson(text, RoverPhoto::class.java)
                             }

                             FavoriteType.HUBBLE_IMAGE.ordinal -> {
                                 adapter = Gson().fromJson(text, Item::class.java)
                             }
                         }
                         convertedData.add(adapter)
                     }
                }

                viewModelScope.launch {
                    favorites.value = convertedData
                }
            } catch (ex: SQLiteConstraintException) {
                message = Pair(false, R.string.favoriteException)
            } catch (ex: SQLException) {
                message = Pair(false, R.string.favoriteException)
            }

            viewModelScope.launch {
                status.value = message
            }
        }
    }


    private fun favoriteRoverImage(favorite: ImageDetailAdapter,
                                   afterSuccessAction: (Boolean) -> Unit) {
        var message = Pair(true, R.string.favoriteAdded)

        try {
            val id = session.addFavorite(
                favorite
            )

            afterSuccessAction(true)
        } catch (ex: SQLiteConstraintException) {
            message = Pair(false, R.string.favoriteException)
        } catch (ex: SQLException) {
            message = Pair(false, R.string.favoriteException)
        }

        viewModelScope.launch {
            status.value = message
        }
    }

    private fun removeFavoriteRoverImage(favorite: FavoriteTest,
                                         afterSuccessAction: (Boolean) -> Unit) {
        var message = Pair(true, R.string.favoriteRemoved)
        try {
            session.removeFavorite(
                favorite
            )

            afterSuccessAction(false)
        } catch (ex: SQLiteConstraintException) {
            message = Pair(false, R.string.favoriteException)
        } catch (ex: SQLException) {
            message = Pair(false, R.string.favoriteException)
        }

        viewModelScope.launch {
            status.value = message
        }
    }
}