package com.steinmetz_ta.favoritemovies.data

import com.steinmetz_ta.favoritemovies.model.MovieResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("en/API/SearchTitle/k_wqjoq51m/{movie_title}")
    fun getMovies(
        @Path("movie_title") movie_title: String
    ) : Call<MovieResponse>


    companion object{

        var movieApi: MovieApi? = null

        fun getInstance() : MovieApi {

            if (movieApi == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://imdb-api.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                movieApi = retrofit.create(MovieApi::class.java)
            }

            return movieApi!!
        }

    }
}