package com.digitalhouse.marsgaze.viewmodels.session.rover_image

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.digitalhouse.marsgaze.controllers.user.Session
import com.digitalhouse.marsgaze.models.data.FavoriteTest
import com.digitalhouse.marsgaze.models.data.FavoriteType
import com.digitalhouse.marsgaze.models.rovers.RoverPhoto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RoverImageViewModel(val session: Session) : ViewModel() {
    val favoriteStatus = MutableLiveData<Int>()
    private val roverType = FavoriteType.ROVERS_IMAGE

    fun favoriteAction(rover: RoverPhoto, favoritedAlready: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            if (!session.isLogged()) {
           favoriteStatus.value = 0
                return@launch
            }

            if (favoritedAlready) {
                removeFavoriteRoverImage(rover)
            } else {
                favoriteRoverImage(rover)
            }
        }
    }


    private fun favoriteRoverImage(rover: RoverPhoto) {
         session.addFavorite(
             FavoriteTest(
                 imageType = roverType.ordinal,
                 imageId = rover.id.toString(),
                 user = session.user().email,
             )
         )
    }

    private fun removeFavoriteRoverImage(rover: RoverPhoto) {
        session.removeFavorite(
            FavoriteTest(
                imageType = roverType.ordinal,
                imageId = rover.id.toString(),
                user = session.user().email,
            )
        )
    }
}