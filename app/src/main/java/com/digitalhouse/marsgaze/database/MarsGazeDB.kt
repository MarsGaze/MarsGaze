package com.digitalhouse.marsgaze.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.digitalhouse.marsgaze.database.dao.FavoriteDAO
import com.digitalhouse.marsgaze.database.dao.UserDAO
import com.digitalhouse.marsgaze.models.data.FavoriteTest
import com.digitalhouse.marsgaze.models.data.User

/**
 * PT-BR
 * Acesso ao banco de dados da nossa aplicação.
 *
 * EN-US
 * Access of our database from our application.
 *
 * @author Jomar Júnior
 */
@Database(entities = [User::class, FavoriteTest::class], version = 1, exportSchema = false)
abstract class MarsGazeDB : RoomDatabase() {
    /**
     * PT-BR
     * Pega o DAO para acessar os métodos relacionado à tabela do usuário
     *
     * EN-US
     * Get the DAO to access the methods related to the user table
     *
     * @return O DAO da tabela do usuário
     *         The user DAO table.
     */
    abstract fun userDAO(): UserDAO

    /**
     * PT-BR
     * Pega o DAO para acessar os métodos relacionado à tabela dos favoritos
     *
     * EN-US
     * Get the DAO to access the methods related to the favorites table
     *
     * @return O DAO da tabela dos favoritos
     *         The favorites DAO table.
     */
    abstract fun favoriteDAO(): FavoriteDAO

    // Cherry picked from
    // https://developer.android.com/training/data-storage/room/accessing-data.html
    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: MarsGazeDB? = null

        fun getDatabase(context: Context): MarsGazeDB {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MarsGazeDB::class.java,
                    "marsgaze_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

}