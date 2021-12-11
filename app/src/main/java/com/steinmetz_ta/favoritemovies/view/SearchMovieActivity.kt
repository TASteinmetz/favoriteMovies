/**
 * In this Activtiy you can enter a movie title into the EditText and by pressing
 * the SearchButton you will receive movies with a similiar name from imdb-api.com.
 * with the AddButton in the CardViews you can add a specific movie to your favorites
 *
 * @author: tobi.steinmetz@gmail.com
 * */
package com.steinmetz_ta.favoritemovies.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.steinmetz_ta.favoritemovies.adapter.SearchAdapter
import com.steinmetz_ta.favoritemovies.data.MovieApi
import com.steinmetz_ta.favoritemovies.databinding.ActivitySearchMovieBinding
import com.steinmetz_ta.favoritemovies.model.MovieResponse
import com.steinmetz_ta.favoritemovies.repo.SearchRepository
import com.steinmetz_ta.favoritemovies.viewmodel.MovieViewModel
import com.steinmetz_ta.favoritemovies.viewmodel.MovieViewModelFactory
import com.steinmetz_ta.favoritemovies.viewmodel.SearchViewModel
import com.steinmetz_ta.favoritemovies.viewmodel.SearchViewModelFactory


class SearchMovieActivity : AppCompatActivity() {

    private lateinit var bindings: ActivitySearchMovieBinding
    private lateinit var searchViewModel: SearchViewModel

    private var movieApi = MovieApi.getInstance()
    private lateinit var adapter: SearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindings = ActivitySearchMovieBinding.inflate(layoutInflater)
        setContentView(bindings.root)


        init()

    }

    private fun init(){

        /**
         * Init RecyclerView
         * */
        adapter = SearchAdapter(this)

        bindings.rvMovieSearch.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        bindings.rvMovieSearch.adapter = adapter
        bindings.rvMovieSearch.visibility = View.VISIBLE


        /**
         * Observe LiveData
         * */
        searchViewModel = ViewModelProvider(this, SearchViewModelFactory(SearchRepository(movieApi))).get(SearchViewModel::class.java)

        searchViewModel.movieList.observe(this, Observer { movieResponse: MovieResponse ->
            Log.d("Movies", "onCreate: $movieResponse")
            adapter.setMovieList(movieResponse.results)
        })

        searchViewModel.errorMsg.observe(this, Observer { errorMsg: String ->
            Log.d("Movies", "Error: $errorMsg")
        })


        /**
         * Set onClick for SearchButton
         * */
        bindings.ibSearchMovie.setOnClickListener {

            if(bindings.etSearchMovieTitle.text.toString().isNotEmpty()) {
                searchViewModel.getMovies(bindings.etSearchMovieTitle.text.toString()) //
            } else {
                Toast.makeText(this, "title is missing", Toast.LENGTH_LONG).show()
            }

        }
    }
}