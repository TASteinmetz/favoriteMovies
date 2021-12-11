package com.steinmetz_ta.favoritemovies.view

import android.app.Application
import com.steinmetz_ta.favoritemovies.data.MovieRoomDatabase
import com.steinmetz_ta.favoritemovies.repo.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MovieApplication : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { MovieRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { MovieRepository(database.MovieDao()) }
}