package com.steinmetz_ta.favoritemovies.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.steinmetz_ta.favoritemovies.model.Result
import com.steinmetz_ta.favoritemovies.util.MovieDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Result::class], version = 1, exportSchema = false)
abstract class MovieRoomDatabase : RoomDatabase() {

    abstract fun MovieDao(): MovieDao

    //singleton
    companion object{

        @Volatile
        private var INSTANCE: MovieRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): MovieRoomDatabase {

            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MovieRoomDatabase::class.java,
                    "movie_database"
                ).addCallback(MovieDatabaseCallback(scope)).build()

                INSTANCE = instance

                instance
            }
        }

    }

    private class MovieDatabaseCallback(
        private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.MovieDao())
                }
            }
        }

        suspend fun populateDatabase(movieDao: MovieDao) {

            movieDao.deleteAll()

            var movie = Result("desc", "0x1",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/8/82/Milky_Way_Galaxy.jpg/2048px-Milky_Way_Galaxy.jpg",
                "a movie",
                "Milky Way"
            )

            movieDao.insert(movie)
        }
    }

}