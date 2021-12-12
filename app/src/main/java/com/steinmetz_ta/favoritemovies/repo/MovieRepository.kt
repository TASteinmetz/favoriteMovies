package com.steinmetz_ta.favoritemovies.repo

import androidx.annotation.WorkerThread
import com.steinmetz_ta.favoritemovies.model.Result
import com.steinmetz_ta.favoritemovies.util.MovieDao
import kotlinx.coroutines.flow.Flow

class MovieRepository(private val movieDao: MovieDao) {

    /**
     * Room executes all queries on a separate thread.
     * Observed Flow will notify the observer when the data has changed
     * */

    val allMovies: Flow<List<Result>> = movieDao.getAlphabetizedMovies()

    /**
     * By default Room runs suspend queries off the main thread, therefore, there is no need to
     * implement anything else to ensure there is no long running database work
     * off the main thread.
     * */

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(movie: Result) {
        movieDao.insert(movie)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(id: String){
        movieDao.deleteById(id)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun search(id: String) = movieDao.findById(id)

}