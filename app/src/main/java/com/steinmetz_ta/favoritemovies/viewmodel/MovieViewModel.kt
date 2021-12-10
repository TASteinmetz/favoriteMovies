package com.steinmetz_ta.favoritemovies.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.steinmetz_ta.favoritemovies.repo.MovieRepository
import com.steinmetz_ta.favoritemovies.model.Result
import kotlinx.coroutines.launch

class MovieViewModel(private val repository: MovieRepository) : ViewModel() {

    val allMovies: LiveData<List<Result>>  = repository.allMovies.asLiveData()

    fun insertMovie(movie: Result) = viewModelScope.launch {
        repository.insert(movie)
    }

    fun deleteMovie(id: String) = viewModelScope.launch {
        repository.delete(id)
    }
}