package com.steinmetz_ta.favoritemovies.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.steinmetz_ta.favoritemovies.BuildConfig
import com.steinmetz_ta.favoritemovies.model.MovieResponse
import com.steinmetz_ta.favoritemovies.repo.SearchRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchViewModel constructor(private val searchRepository: SearchRepository) : ViewModel() {

    val movieList = MutableLiveData<MovieResponse>()
    val errorMsg = MutableLiveData<String>()

    fun getMovies(title: String) { //

        val response = searchRepository.getMovies(title) //title
        response.enqueue(object: Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {

                if(response.isSuccessful) {
                    Log.i("Movies", "${response.body()}")
                    movieList.postValue(response.body())
                } else {
                    Log.e("Movies", "Response: ${response.code()}")
                    errorMsg.postValue(response.message())
                }

            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                errorMsg.postValue(t.message)
                Log.e("Movies", "${t.message}")
            }

        })
    }

}