package com.steinmetz_ta.favoritemovies.repo

import androidx.annotation.WorkerThread
import com.steinmetz_ta.favoritemovies.model.Result
import com.steinmetz_ta.favoritemovies.util.MovieDao
import kotlinx.coroutines.flow.Flow

class MovieRepository(private val movieDao: MovieDao) {

    val allMovies: Flow<List<Result>> = movieDao.getAlphabetizedMovies()

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