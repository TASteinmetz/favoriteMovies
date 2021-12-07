package com.steinmetz_ta.favoritemovies.view

import android.graphics.Movie
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.steinmetz_ta.favoritemovies.adapter.SearchAdapter
import com.steinmetz_ta.favoritemovies.data.MovieApi
import com.steinmetz_ta.favoritemovies.databinding.ActivitySearchMovieBinding
import com.steinmetz_ta.favoritemovies.model.MovieResponse
import com.steinmetz_ta.favoritemovies.repo.SearchRepository
import com.steinmetz_ta.favoritemovies.viewmodel.SearchViewModel
import com.steinmetz_ta.favoritemovies.viewmodel.SearchViewModelFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchMovieActivity : AppCompatActivity() {

    private lateinit var bindings: ActivitySearchMovieBinding
    private lateinit var viewModel: SearchViewModel

    //private lateinit var searchRepository: SearchRepository

    private var movieApi = MovieApi.getInstance()
    private val adapter = SearchAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindings = ActivitySearchMovieBinding.inflate(layoutInflater)
        setContentView(bindings.root)


        //Init RecyclerView
        bindings.rvMovieSearch.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        bindings.rvMovieSearch.adapter = adapter
        bindings.rvMovieSearch.visibility = View.VISIBLE

        //Init LiveData

        //old: ViewModel gets deleted
        //searchRepository = SearchRepository(movieApi)
        //viewModel = SearchViewModel(searchRepository)

        //new: ViewModelProvider retains them
        viewModel = ViewModelProvider(this, SearchViewModelFactory(SearchRepository(movieApi))).get(SearchViewModel::class.java)

        viewModel.movieList.observe(this, Observer { movieResponse: MovieResponse ->
            Log.d("Movies", "onCreate: $movieResponse")
            adapter.setMovieList(movieResponse.results)
        })

        viewModel.errorMsg.observe(this, Observer { errorMsg: String ->
            Log.d("Movies", "Error: $errorMsg")
        })


        bindings.ibSearchMovie.setOnClickListener {

            if(bindings.etSearchMovieTitle.text.toString().isNotEmpty()) {
                viewModel.getMovies(bindings.etSearchMovieTitle.text.toString()) //
            } else {
                Toast.makeText(this, "title is missing", Toast.LENGTH_LONG).show()
            }

        }

    }
}