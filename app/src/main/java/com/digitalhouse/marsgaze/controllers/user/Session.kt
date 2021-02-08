package com.digitalhouse.marsgaze.controllers.user

import com.digitalhouse.marsgaze.database.AfterFavoriteAction
import com.digitalhouse.marsgaze.database.FavoriteHelperAfter
import com.digitalhouse.marsgaze.database.MarsGazeDB
import com.digitalhouse.marsgaze.helper.MessageHash
import com.digitalhouse.marsgaze.models.data.Favorite
import com.digitalhouse.marsgaze.models.data.FavoriteType
import com.digitalhouse.marsgaze.models.data.User
import com.digitalhouse.marsgaze.models.favorite.ImageDetailAdapter
import com.digitalhouse.marsgaze.models.hubble.Item
import com.digitalhouse.marsgaze.models.rovers.RoverPhoto
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * PT-BR
 * Sessão geral do aplicativo em relação ao usuário logado. Disponibiliza diversas funções que
 * devem ser usadas quando há necessidade de pegar algo do banco que é relacionado a algum usuário.
 *
 * EN-US
 * Geral session of the app in relation to the logged user. Enables many functions which needs
 * to be used whe  there's a necessity to retrieve something from the database which is related
 * to an user.
 *
 * @property marsGazeDB Banco de dados do MarsGaze
 *                   Marsgaze database
 *
 * @property favoriteHelperAfter Ações após alguma mudança de um favorito
 *                            Actions after a modification of a favorite
 */
class Session private constructor(
    private val marsGazeDB: MarsGazeDB,
    private val favoriteHelperAfter: FavoriteHelperAfter
    ) {
    private var loggedUser: User? = null

    init {
        GlobalScope.launch(Dispatchers.IO) {
            val user = marsGazeDB.userDAO().userInSession()
            if (user != null) {
                if (user.inSessionUntil > Date().time) {
                    loggedUser = user
                    // Não precisamos da senha hash aqui
                    loggedUser!!.password = user.purePassword
                } else {
                    user.inSessionUntil = 0
                    user.purePassword = ""
                    marsGazeDB.userDAO().update(user)
                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: Session? = null

        fun getInstance(marsGazeDB: MarsGazeDB, afterFavoriteAction: FavoriteHelperAfter): Session {
            return INSTANCE ?: synchronized(this) {
                val instance = Session(marsGazeDB, afterFavoriteAction)
                INSTANCE = instance
                instance
            }
        }
    }

    fun isLogged(): Boolean = loggedUser != null

    /**
     * PT-BR
     * Desloga o usuário
     *
     * EN-US
     * Logoff the user
     */
    fun logoff() {
        if (loggedUser != null) {
            // Retira a sessão do usuário após a saída
            loggedUser!!.inSessionUntil = 0
            loggedUser!!.purePassword = ""
            update(loggedUser!!)
            loggedUser = null
        }
    }

    /**
     * PT-BR
     * Loga um usuário caso ele exista no banco de dados passados
     *
     * EN-US
     * Logs an user if it exists on the given database
     *
     * @param user Usuário com a senha pura
     *             User with the original password
     *
     * @return True if the login was successful, false otherwise
     */
    fun login(user: User, keepLoggedIn: Boolean = true): Boolean {
        // Hashs the password
        val tmpPass = user.password
        if (user.password != null) {
            user.password = MessageHash(user.password!!).hashAndSalt()
        }

        val userExists = marsGazeDB.userDAO().loginMatch(user.email, user.password)

        // Evita alterar o objeto passado
        user.password = tmpPass
        if (userExists != null) {
            logoff()
            // O usuário logado fica com a senha pura na sessão mas não no banco de dados
            userExists.password = tmpPass
            loggedUser = userExists
            if (keepLoggedIn) {
                val date = Date()
                date.time += TimeUnit.DAYS.toSeconds(3)
                loggedUser!!.inSessionUntil = date.time
                loggedUser!!.purePassword = user.password!!
                update(loggedUser!!)
            }

            return true
        }

        return false
    }

    /**
     * PT-BR
     * Registra o usuário no banco de dados.
     *
     * EN-US
     * Register the user to the database.
     *
     * @param user Usuário com a senha pura
     *             User with the original password
     */
    fun register(user: User, date: Date = Date()) {
        val tmpPass = user.password

        if (user.password != null) {
            user.password = MessageHash(user.password!!).hashAndSalt()
        }

        val parser = SimpleDateFormat("dd/MM/yyyy", Locale.UK)
        user.createdOn = parser.format(date)
        val userDao = marsGazeDB.userDAO()
        userDao.insert(user)

        user.password = tmpPass
    }

    /**
     * PT-BR
     * Atualiza os dados do usuário no banco de dados.
     *
     * EN-US
     * Updates the user data at the database
     *
     * @param user Usuário com a senha pura
     *             User with the original password
     */
    @Throws(exceptionClasses = [NoUserWasLoggedIn::class, ForbiddenAction::class])
    fun update(user: User) {
        val userSession = loggedUser ?: throw NoUserWasLoggedIn(
            "Can't update user if not logged in"
        )

        val userDAO = marsGazeDB.userDAO()

        // FIXME ou TODO devemos aceitar mudanças de email?

        if (user.email != userSession.email) {
            throw ForbiddenAction("Update will not affect logged user")
        }

        val actualPassword = user.password

        if (user.password != null) {
            user.password = MessageHash(user.password!!).hashAndSalt()
        }

        userDAO.update(user)
        user.password = actualPassword
        loggedUser = user
    }

    /**
     * PT-BR
     * Adiciona um favorito ao usuário.
     *
     * EN-US
     * Adds a favorite to the user.
     *
     * @throws NoUserWasLoggedIn Se nenhum usuário estiver logado
     *                           If no user was logged
     */
    @Throws(exceptionClasses = [NoUserWasLoggedIn::class])
    fun addFavorite(fav: ImageDetailAdapter): Long {
        val user = loggedUser ?: throw NoUserWasLoggedIn(
            "Can't add favorites if no user is logged"
        )

        val favDAO = marsGazeDB.favoriteDAO()

        lateinit var data: String
        when (
            fav.getType()
        ) {
            FavoriteType.ROVERS_IMAGE.ordinal -> {
                data = Gson().toJson(fav as RoverPhoto)
            }

            FavoriteType.HUBBLE_IMAGE.ordinal -> {
                data = Gson().toJson(fav as Item)
            }
        }

        val id = favDAO.insert(fav.toFavorite(user))
        favoriteHelperAfter.afterInsert(
            FavoriteType.values()[fav.getType()],
            fav.getId(),
            data
        )

        return id
    }

    /**
     * PT-BR
     * Remove um favorito do usuário
     *
     * EN-US
     * Removes a favorite from the user
     *
     * @throws NoUserWasLoggedIn Se nenhum usuário estiver logado
     *                           If no user was logged
     */
    @Throws(exceptionClasses = [NoUserWasLoggedIn::class])
    fun removeFavorite(fav: Favorite) {
        val user = loggedUser ?: throw NoUserWasLoggedIn(
            "Can't remove favorites if no user is logged"
        )

        if (user.email != fav.user) {
            throw ForbiddenAction("User is not related to the given favorite")
        }

        val favDAO = marsGazeDB.favoriteDAO()


        favDAO.delete(fav)
        favoriteHelperAfter.afterDelete(FavoriteType.values()[fav.imageType], fav.imageId)
    }


    /**
     * PT-BR
     * Retorna o favorito com o id a partir do nome da image, tipo e o usuário que favoritou ela.
     *
     * EN-US
     * Returns the favorite with its id from the image name, type and user which owns it.
     *
     * @param fav Favorito
     *            Favorite
     */
    fun isFavorited(fav: Favorite): Favorite? =
        marsGazeDB.favoriteDAO().favoritedImage(fav.user, fav.imageType, fav.imageId)


    /**
     * PT-BR
     * Pega todos os favoritos do usuário
     *
     * EN-US
     * Returns all the user favorites
     *
     * @throws NoUserWasLoggedIn Se nenhum usuário estiver logado
     *                           If no user was logged
     *
     * @return Favoritos do usuário
     *         User favorites
     */
    @Throws(NoUserWasLoggedIn::class)
    fun getAllFavorites(): List<Favorite> {
        val user = loggedUser ?: throw NoUserWasLoggedIn(
            "No user is logged to get all its favorites"
        )

        val favoriteDAO = marsGazeDB.favoriteDAO()

        return favoriteDAO.getUserFavorites(user.email)
    }

    /**
     * PT-BR
     * Pega o usuário logado
     *
     * EN-US
     * Picks the logged user
     *
     * @throws NoUserWasLoggedIn Se nenhum usuário estiver logado
     *                           If no user was logged
     */
    fun user(): User = loggedUser ?: throw NoUserWasLoggedIn("No user was logged in")


    fun getFavoriteFile(id: String, type: FavoriteType): File {
        val favAction = favoriteHelperAfter as AfterFavoriteAction
        return favAction.getFavFile(type, id)
    }

    class NoUserWasLoggedIn(message: String?) : Exception(message)

    class ForbiddenAction(message: String?) : Exception(message)
}