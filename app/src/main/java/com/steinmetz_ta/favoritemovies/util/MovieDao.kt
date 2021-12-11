package com.steinmetz_ta.favoritemovies.util

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import com.steinmetz_ta.favoritemovies.model.Result

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie_table ORDER BY title ASC")
    fun getAlphabetizedMovies(): Flow<List<Result>>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(movie: Result): Long

    @Transaction
    @Query("DELETE FROM movie_table WHERE id = :id")
    suspend fun deleteById(id: String)


    @Query("SELECT * FROM movie_table WHERE id = :id limit 1")
    suspend fun findById(id: String): Result

    @Transaction
    @Query("DELETE FROM movie_table")
    suspend fun deleteAll()
}