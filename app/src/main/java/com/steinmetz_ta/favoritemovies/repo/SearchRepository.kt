package com.steinmetz_ta.favoritemovies.repo

import com.steinmetz_ta.favoritemovies.BuildConfig
import com.steinmetz_ta.favoritemovies.data.MovieApi

class SearchRepository constructor(private val movieApi: MovieApi) {

    fun getMovies(title: String) = movieApi.getMovies(title) //title: String  title
}