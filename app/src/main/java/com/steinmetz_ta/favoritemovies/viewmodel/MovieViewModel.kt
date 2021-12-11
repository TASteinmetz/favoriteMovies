package com.steinmetz_ta.favoritemovies.viewmodel

import androidx.lifecycle.*
import com.steinmetz_ta.favoritemovies.model.MovieResponse
import com.steinmetz_ta.favoritemovies.repo.MovieRepository
import com.steinmetz_ta.favoritemovies.model.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MovieViewModel(private val repository: MovieRepository) : ViewModel() {

    val allMovies: LiveData<List<Result>>  = repository.allMovies.asLiveData()


    fun insertMovie(movie: Result) = viewModelScope.launch {
        repository.insert(movie)
    }

    fun deleteMovie(id: String) = viewModelScope.launch {
        repository.delete(id)
    }

    val movie = MutableStateFlow<Result?>(null)

    fun searchMovie(id: String) {
        viewModelScope.launch {
            movie.value = repository.search(id)
        }
    }
}